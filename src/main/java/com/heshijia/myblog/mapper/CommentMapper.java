package com.heshijia.myblog.mapper;
import org.apache.ibatis.annotations.Param;

import com.heshijia.myblog.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2022-05-23 21:56:41
* @Entity com.heshijia.myblog.pojo.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    Comment selectAllById (@Param ( "id" ) Long id);

    List<Comment> selectByBlogIdAndExtendsCommentidIsNull (@Param ( "blogId" ) Long blogId);


    List<Comment> selectByExtendsCommentid (@Param ( "extendsCommentid" ) Long extendsCommentid);

    int deleteByBlogId (@Param ( "blogId" ) Long blogId);

    List<Comment> selectByBlogId (@Param ( "blogId" ) Long blogId);

    int deleteById (@Param ( "id" ) Long id);

    Integer  selectCommentCount();
}




