package com.heshijia.myblog.web.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Comment;
import com.heshijia.myblog.pojo.Msg;
import com.heshijia.myblog.pojo.Tag;
import com.heshijia.myblog.service.impl.CommentServiceImpl;
import com.heshijia.myblog.utils.MsgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping ("/admin")
public class CommentController {
    @Autowired
    CommentServiceImpl commentServiceImp;
    /**
     * 跳转博客评论管理页面
     * @return
     */
    @RequestMapping ("/tocomment")
    public String tocomment(Model model){
        List<Blog> blogs = commentServiceImp.queryIdAndTitle();
        model.addAttribute("blogs",blogs);
        return "admin/comment";
    }
    /**
     * 查询博客评论
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    @ResponseBody
    public  Object QueryComment(@RequestParam (defaultValue = "3")String pageSize, @RequestParam(defaultValue = "1") String pageNum
            ,String blogId) {
        Msg msg = new Msg( );
        try {
            PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            List<Comment> comments = commentServiceImp.queryByBlogId(Long.parseLong(blogId));
            PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
            HashMap<String, Object> hashMap = msg.getHashMap( );
            hashMap.put("commentsPage",commentPageInfo);
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return  msg;
        }
    }


    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment",method = RequestMethod.DELETE)
    @ResponseBody
    public  Object deleteComment(String id) {
        Msg msg = new Msg( );
        try {
            int i = commentServiceImp.removeCommentById(Long.parseLong(id));
            if (i > 0) {
                msg.setCode(MsgCode.SUCCESS_CODE);
                return msg;
            } else {
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("删除失败");
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace( );
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return msg;
        }
    }



    }
