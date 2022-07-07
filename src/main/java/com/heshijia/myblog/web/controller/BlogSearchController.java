package com.heshijia.myblog.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BlogSearchController {

    @Autowired
    BlogServiceImpl blogServiceImp;

    /**
     * 搜索博客页面
     * @param
     * @param
     * @return
     */
    @GetMapping ("/toblogSearch")
    public  String toblogSearch(String title, Model model){
        model.addAttribute("title",title);
        return "blog/search";
    }

    /**
     * 搜索博客遍历
     * @param
     * @param
     * @return
     */
    @GetMapping ("/blogSearchResults")
    @ResponseBody
    public  Object BlogSearchResults(String title,
     @RequestParam(defaultValue = "1") String pageNum,
     @RequestParam(defaultValue = "3") String pageSize){
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Blog> blogs = blogServiceImp.queryBlogsSearchResults(title);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }
}
