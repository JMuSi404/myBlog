package com.heshijia.myblog.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heshijia.myblog.pojo.*;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import com.heshijia.myblog.service.impl.CommentServiceImpl;
import com.heshijia.myblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogcommentController {

    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    BlogServiceImpl blogServiceimpl;
    @Autowired
    private JavaMailSender mailSender;



    //查询评论
    @GetMapping ("/comment")
    @ResponseBody
    public  Object  getComment(String blogId) {
        List<Comment> comments = commentService.queryByBlogIdAndExtendsCommentidIsNull(Long.parseLong(blogId));
        querySonComment(comments);
        return  comments;
    }

  //递归查询子评论
    public  void  querySonComment(List<Comment> list){
        if (list.size()>0){
            for (Comment comment : list) {
                List<Comment> sonCommenList = commentService.queryByExtendsCommentid(comment.getId());
                if (sonCommenList.size()>0){
                    List<Comment> Commentlist = comment.getList( );
                    for (Comment soncomment : sonCommenList){
                        Commentlist.add(soncomment);
                    }
                    querySonComment(Commentlist);
                }
            }
        }
    }


    //新增评论
@PostMapping("/comment")
@ResponseBody
public  Object  addComment(Comment comment, HttpSession session){
    Msg msg = new Msg( );
    try{
     User user = (User)session.getAttribute(SessionInfo.LOGIN_INFO);
      if (user!=null){
          comment.setNickname(user.getNickname());
          comment.setAvatar(user.getAvatar());
         comment.setAdministrator(true);
      }else{
          comment.setAdministrator(false);
      }
      comment.setCreatetime(DateTimeFormatUtils.getDateTime(new Date(  )));
    int i = commentService.saveComment(comment);
    if (i>0){
        msg.setCode(MsgCode.SUCCESS_CODE);
    }else {
        msg.setMessage(MsgCode.FAIL_CODE);
        msg.setMessage("添加评论失败");
    }
    return msg;
    }catch (Exception ex){
        ex.printStackTrace();
        msg.setMessage(MsgCode.FAIL_CODE);
        msg.setMessage("系统繁忙,请稍后再试");
        return msg;
    }
}

    //评论邮件提醒
    @PostMapping("/emailAlert")
    @ResponseBody
   public  Object  emailAlert(Comment comment){
       Msg msg = new Msg( );
        try {
            SimpleMailMessage message = new SimpleMailMessage( );
            Blog blog = blogServiceimpl.queryBlogById(comment.getBlogId( ));
            String url=MailDataUtils.DOMAIN_NAME+"/toBlog/"+ blog.getId( );
            if (blog.getType().equals("2")){
                url=MailDataUtils.DOMAIN_NAME+"/toAbout";
            }else if (blog.getType().equals("1")){
                url=MailDataUtils.DOMAIN_NAME+"/toLink";
            }
            if (comment.getExtendsCommentid( ) != null && comment.getExtendsCommentid( ) != 0) {
                Comment ExtendsComment = commentService.queryAllById(comment.getExtendsCommentid( ));
                // 发件人
                message.setFrom(MailDataUtils.MAIL_SENDER);
                // 收件人
                message.setTo(ExtendsComment.getEmail( ));
                // 邮件标题
                message.setSubject(MailDataUtils.MAIL_TITLE);
                // 邮件内容
                message.setText("评论人:" + comment.getNickname( ) +
                        "\n评论内容:" + comment.getContent( ) +
                        "\n文章:" + blog.getTitle( ) +
                        "\n文章地址:" + url);
                mailSender.send(message);
            } else {
                // 发件人
                message.setFrom(MailDataUtils.MAIL_SENDER);
                // 抄送人
                message.setTo(MailDataUtils.MAIL_CC);
                // 邮件标题
                message.setSubject(MailDataUtils.MAIL_TITLE);
                // 邮件内容
                message.setText("评论人:" + comment.getNickname( ) +
                        "\n评论内容:" + comment.getContent( ) +
                        "\n文章:" + blog.getTitle( ) +
                        "\n文章地址:<a href="+url+">"+url+"</a>" );
                mailSender.send(message);
            }
            msg.setMessage(MsgCode.SUCCESS_CODE);
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setMessage(MsgCode.FAIL_CODE);
            msg.setMessage("系统繁忙,请稍后再试");
            return msg;
        }
    }

    /**
     * 拉取qq头像和昵称
     * @param qq
     * @return
     */
    @ResponseBody
    @GetMapping("/getQQInfo/{qq}")
    public Msg qq(@PathVariable ("qq") String qq) {
        Msg msg = new Msg( );
        if (StringUtils.isEmpty(qq)) {
            msg.setMessage("qq号为空");
            msg.setCode("200");
            return  msg ;
        }
        try {
            HashMap resultMap = new HashMap<>(4);
            QQ qqInfo = QQUtils.getQQInfo(Long.parseLong(qq));
            resultMap.put("avatar",qqInfo.getAvatar());
            resultMap.put("nickname",qqInfo.getName());
            resultMap.put("email", qq + "@qq.com");
            msg.setMessage("拉取qq信息成功");
            msg.setCode("100");
            msg.setHashMap(resultMap);
        }catch (Exception e){
            msg.setMessage("拉取qq信息失败");
            msg.setCode("200");
            return  msg ;
        }
        return msg;

    }
}
