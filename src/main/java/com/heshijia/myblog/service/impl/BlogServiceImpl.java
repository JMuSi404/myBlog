package com.heshijia.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshijia.myblog.mapper.BlogTagRelationMapper;
import com.heshijia.myblog.mapper.CommentMapper;
import com.heshijia.myblog.mapper.TagMapper;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.BlogTagRelation;
import com.heshijia.myblog.pojo.Tag;
import com.heshijia.myblog.service.BlogService;
import com.heshijia.myblog.mapper.BlogMapper;
import com.heshijia.myblog.utils.MarkDownUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【t_blog】的数据库操作Service实现
* @createDate 2022-05-23 21:56:09
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

    @Autowired
private   BlogMapper blogMapper;

    @Autowired
private BlogTagRelationMapper blogTagRelationMapper;

    @Autowired
 private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public int saveBlog (Blog blog) {
        return blogMapper.insert(blog);
    }

    @Override
    public List<Blog> queryBlogCondition (Map<String, Object> map) {
        return blogMapper.selectBlogConditionPage(map);
    }

    @Override
    public List<Blog> queryBlogsList ( ) {
        return blogMapper.selectBlogsShowList();
    }

    @Override
    public List<Blog> queryBlogsSearchResults (String condition) {
        return blogMapper.selectBlogsSearchResults(condition);
    }

    @Override
    public int removeBlogAndBlogTagRelation (Long id) {
         blogTagRelationMapper.deleteByBlogId(id);
          commentMapper.deleteByBlogId(id);
            int j = blogMapper.deleteById(id);
            return j;
    }

    @Override
    public Blog queryBlogById (Long id) {
        return blogMapper.selectAllById(id);
    }

    @Override
    public List<Blog> queryBlogByTagId (Long TagId) {
        return blogMapper.selectBlogByTagId(TagId);
    }

    @Override
    public int editBlogs (Blog blog,String tagIds) {
        blogTagRelationMapper.deleteByBlogId(blog.getId());
            int j = blogMapper.updateById(blog);
            System.out.println("j "+j );
            if (j>0){
                String[] ids = tagIds.split(",");
                for (String id : ids) {
                   int k=blogTagRelationMapper.insert(new BlogTagRelation(null , blog.getId( ) , Long.parseLong(id)));
                   if (k<=0){
                       return k;
                   }
                }
            }
            return j;
    }

    @Override
    public Blog queryBlogDetailsById (Long id){
        Blog blog = blogMapper.selectBlogDetailsById(id);
        String content = blog.getContent( );
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
//            blogMapper.updateViewsById((blog.getViews()+1),blog.getId());
        return blog;
    }

    @Override
    public Map<String, List<Blog>> queryBlogArchive ( ) {
        Map<String, List<Blog>> Map = new HashMap<>(  );
        List<String> Years = blogMapper.selectBlogYear( );
        for (String year : Years) {
            List<Blog> blogs = blogMapper.selectBlogArchive(year);
            Map.put(year,blogs);
        }
        return Map;
    }

    @Override
    public Map<String, Object> selectCount ( ) {
        HashMap<String, Object> HashMap = new HashMap<>( );
        Integer BlogCount = blogMapper.selectBlogCount( );
        Integer CommentCount = commentMapper.selectCommentCount( );
        Integer TagCount = tagMapper.selectTagCount( );
        Integer ViewCount = blogMapper.selectBlogViewsCount( );
        HashMap.put("ViewCount",ViewCount);
        HashMap.put("TagCount",TagCount);
        HashMap.put("BlogCount",BlogCount);
        HashMap.put("CommentCount",CommentCount);
        return HashMap;
    }

    @Override
    public Boolean updateNumById (Blog blog) {
        return  blogMapper.updateViewsById(blog.getViews(),blog.getId())>1;
    }


}




