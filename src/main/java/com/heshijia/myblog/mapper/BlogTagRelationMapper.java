package com.heshijia.myblog.mapper;
import org.apache.ibatis.annotations.Param;

import com.heshijia.myblog.pojo.BlogTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【t_blog_tag_relation】的数据库操作Mapper
* @createDate 2022-05-23 21:56:21
* @Entity com.heshijia.myblog.pojo.BlogTagRelation
*/
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelation> {

    /**
     * 根据blogid删除与标签关联信息
     * @param blogId
     * @return
     */
    int deleteByBlogId (@Param ( "blogId" ) Long blogId);

    /**
     * 根据tagId删除与标签关联信息
     * @param tagId
     * @return
     */
    int deleteByTagId (@Param ( "tagId" ) Long tagId);
}




