package com.heshijia.myblog.service;

import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2022-05-23 21:56:41
*/
public interface CommentService extends IService<Comment> {

    int saveComment(Comment comment);

    List<Comment> queryByBlogIdAndExtendsCommentidIsNull (Long blogId);

    List<Comment> queryByExtendsCommentid (Long extendsCommentid);

    List<Blog> queryIdAndTitle ();

    List<Comment> queryByBlogId (Long blogId);

    int removeCommentById (Long id);

    Comment queryAllById (Long id);

}
