package com.heshijia.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshijia.myblog.mapper.BlogMapper;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Comment;
import com.heshijia.myblog.service.CommentService;
import com.heshijia.myblog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2022-05-23 21:56:41
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
@Autowired
CommentMapper commentMapper;
@Autowired
    BlogMapper blogMapper;

    @Override
    public int saveComment (Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public List<Comment> queryByBlogIdAndExtendsCommentidIsNull (Long blogId) {
        return commentMapper.selectByBlogIdAndExtendsCommentidIsNull(blogId);
    }

    @Override
    public List<Comment> queryByExtendsCommentid (Long extendsCommentid) {
        return commentMapper.selectByExtendsCommentid(extendsCommentid);
    }

    @Override
    public List<Blog> queryIdAndTitle ( ) {
        return blogMapper.selectIdAndTitle();
    }

    @Override
    public List<Comment> queryByBlogId (Long blogId) {
        return commentMapper.selectByBlogId(blogId);
    }

    @Override
    public int removeCommentById (Long id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public Comment queryAllById (Long id) {
        return commentMapper.selectAllById(id);
    }
}




