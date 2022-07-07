package com.heshijia.myblog.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Tag;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import com.heshijia.myblog.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class HomeShowController {

    @Autowired
    BlogServiceImpl blogServiceImpl;

    @Autowired
    TagServiceImpl tagServiceImp;
    /**
     * 博客主页博客列表显示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/BlogsShowList")
    public  Object BlogsShowList(@RequestParam(defaultValue = "1") String pageNum,@RequestParam(defaultValue = "5") String pageSize){
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Blog> blogs = blogServiceImpl.queryBlogsList();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 博客文章数量与评论数量
     * @param
     * @param
     * @return
     */
    @GetMapping("/BlogsCount")
    public  Object BlogsCount(){
        Map<String, Object> Map = blogServiceImpl.selectCount( );
        return Map;
    }

    /**
     * 博客标签显示
     * @param
     * @param
     * @return
     */
    @GetMapping("/TagShowList")
    public  Object TagShowList(){
        List<Tag> tags = tagServiceImp.queryTagList( );
        return tags;
    }




}
