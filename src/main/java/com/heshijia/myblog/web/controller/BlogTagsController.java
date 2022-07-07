package com.heshijia.myblog.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Tag;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import com.heshijia.myblog.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BlogTagsController {
    @Autowired
    BlogServiceImpl blogServiceImpl;
    @Autowired
    TagServiceImpl tagServiceimp;

    @RequestMapping("/blogTagsShowList/{TagId}")
    @ResponseBody
    public  Object blogTagsShowList(@RequestParam (defaultValue = "1") String pageNum, @RequestParam(defaultValue = "3") String pageSize,
                                 @PathVariable("TagId") String TagId){
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Blog> blogs = blogServiceImpl.queryBlogByTagId(Long.parseLong(TagId));
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    @RequestMapping("/blogTagById/{BlogId}")
    @ResponseBody
    public  Object blogTagByBlogId(@PathVariable("BlogId") String BlogId){
        List<Tag> tags = tagServiceimp.queryTagListByBlogId(Long.parseLong(BlogId));
        return tags;
    }



}
