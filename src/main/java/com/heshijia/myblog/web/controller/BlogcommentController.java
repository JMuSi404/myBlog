package com.heshijia.myblog.web.controller;

import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Comment;
import com.heshijia.myblog.pojo.Msg;
import com.heshijia.myblog.pojo.User;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import com.heshijia.myblog.service.impl.CommentServiceImpl;
import com.heshijia.myblog.utils.DateTimeFormatUtils;
import com.heshijia.myblog.utils.MsgCode;
import com.heshijia.myblog.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
      }else {
          comment.setAvatar("/img/commentAvatar.png");
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
            String url="https://www.hsjhome.top/" +"toBlog/"+ blog.getId( );

            if (blog.getType().equals("2")){
                url="https://www.hsjhome.top/toAbout";
            }else if (blog.getType().equals("1")){
                url="https://www.hsjhome.top/toLink";
            }
            if (comment.getExtendsCommentid( ) != null && comment.getExtendsCommentid( ) != 0) {
                Comment ExtendsComment = commentService.queryAllById(comment.getExtendsCommentid( ));
                // 发件人
                message.setFrom("heshijiablog@163.com");
                // 收件人
                message.setTo(ExtendsComment.getEmail( ));
                // 邮件标题
                message.setSubject("来自Joe站点的新消息");
                // 邮件内容
                message.setText("评论人:" + comment.getNickname( ) +
                        "\n评论内容:" + comment.getContent( ) +
                        "\n文章:" + blog.getTitle( ) +
                        "\n文章地址:" + url);
                mailSender.send(message);
            } else {
                // 发件人
                message.setFrom("heshijiablog@163.com");
                // 收件人
                message.setTo("1870562227@qq.com");
                // 邮件标题
                message.setSubject("Joe站点有新评论");
                // 邮件内容
                message.setText("评论人:" + comment.getNickname( ) +
                        "\n评论内容:" + comment.getContent( ) +
                        "\n文章:" + blog.getTitle( ) +
                        "\n文章地址:" + url);
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

}
