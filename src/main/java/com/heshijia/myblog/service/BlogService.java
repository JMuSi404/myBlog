package com.heshijia.myblog.service;

import com.heshijia.myblog.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【t_blog】的数据库操作Service
* @createDate 2022-05-23 21:56:09
*/
public interface BlogService extends IService<Blog> {
     /**
      * 发布博客
      * @param
      * @return
      */
     int   saveBlog(Blog blog);

     /**
      * 根据条件查询博客列表
      * @param map
      * @return
      */
     List<Blog> queryBlogCondition(Map<String,Object> map);

     /**
      * 查询博客列表
      * @param
      * @return
      */
     List<Blog> queryBlogsList();

     /**
      * 搜索博客列表
      * @param
      * @return
      */
     List<Blog> queryBlogsSearchResults(String condition);

     /**
      * 根据id删除博客信息
      */
     int removeBlogAndBlogTagRelation(Long id);

     /**
      * 根据id查询博客信息
      */
     Blog  queryBlogById(Long id);


     /**
      * 根据Tagid查询博客信息
      */
     List<Blog>  queryBlogByTagId(Long TagId);


     /**
      * 修改博客信息
      */
     int  editBlogs(Blog blog,String tagIds);


     /**
      * 根据id查询博客详情
      */
     Blog  queryBlogDetailsById(Long id) throws NotFoundException;

     /**
      * 博客归档
      */
     Map<String,List<Blog>>  queryBlogArchive();

     /**
      * 查询博客数量和评论
      * @return
      */
     Map<String,Object>  selectCount();

     Boolean updateNumById(Blog blog);

     List<Blog> queryBlogByType (String type);

     Blog queryBlogDetailsByType(String type);
}
