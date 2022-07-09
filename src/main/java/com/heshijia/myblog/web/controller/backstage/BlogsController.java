package com.heshijia.myblog.web.controller.backstage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.*;
import com.heshijia.myblog.service.impl.BlogServiceImpl;
import com.heshijia.myblog.service.impl.BlogTagRelationServiceImpl;
import com.heshijia.myblog.service.impl.TagServiceImpl;
import com.heshijia.myblog.utils.DateTimeFormatUtils;
import com.heshijia.myblog.utils.MsgCode;
import com.heshijia.myblog.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping ("/admin")
public class BlogsController {
    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private BlogServiceImpl blogServiceimpl;

    @Autowired
    private BlogTagRelationServiceImpl blogTagRelationService;
    /**
     * 跳转博客管理页面
     * @return
     */
    @RequestMapping ("/toBlogs")
    public String toBlogs(Model model){
        List<Tag> tags = tagService.queryTagList();
        model.addAttribute("tags",tags);
        return "admin/blogs";
    }
    /**
     * 跳转博客发布页面
     * @return
     */
    @RequestMapping ("/toBlogsInput")
    public String toBlogsInput(Model model){
        List<Tag> tags = tagService.queryTagList();
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }
    /**
     * 跳转博客修改页面
     * @return
     */
    @RequestMapping ("/toEditBlogs")
    public String toEditBlogs(String id,Model model){
        Blog blog = blogServiceimpl.queryBlogById(Long.parseLong(id));
        if (blog.getType().equals("0")) {
            List<Tag> tag = tagService.queryTagListByBlogId(Long.parseLong(id));
            List<Tag> tags = tagService.queryTagList( );
            String ids = "";
            for (Tag t : tag) {
                ids += t.getId( ) + ",";
            }
            if (ids != null && ids != "") {
                String substringId = ids.substring(0 , ids.lastIndexOf(","));
                model.addAttribute("ids" , substringId);
            }
            model.addAttribute("tags" , tags);
        }
        model.addAttribute("blogs",blog);
        return "admin/blogs-edit";
    }

    /**
     * 添加博客信息
     */
    @PostMapping("/Blogs")
    public String saveBlogs(Blog blog, String tagIds, HttpSession session, RedirectAttributes redirectAttributes){
   try{
       if (!"0".equals(blog.getType())) {
           if (blogServiceimpl.queryBlogByType(blog.getType()).size( ) > 0) {
               redirectAttributes.addFlashAttribute("msg" , "1".equals(blog.getType())?"友链页已经存在,请勿重复添加":"关于我页已经存在,请勿重复添加");
               return "redirect:/admin/toBlogs";
           }
       }
       blog.setRecommendned(blog.getRecommendned()==null?false:true);
       blog.setCommentabled(blog.getCommentabled()==null?false:true);
    blog.setCreatetime(DateTimeFormatUtils.getDateTime(new Date()));
    User user =(User) session.getAttribute(SessionInfo.LOGIN_INFO);
    blog.setUserId(user.getId());
    blog.setViews(0);
       int i = blogServiceimpl.saveBlog(blog);
    if (i>0){
        if (tagIds!=null&&tagIds!=""){
        String[] ids = tagIds.split(",");
        for (String id : ids) {
            int j = blogTagRelationService.saveBlogTagRelation(new BlogTagRelation(null , blog.getId( ) , Long.parseLong(id)));
            if (j<=0){
                redirectAttributes.addFlashAttribute("msg","添加标签失败");
                return  "redirect:/admin/toBlogs";
            }
        }
        }
        redirectAttributes.addFlashAttribute("msg","添加成功");
    }else {
        redirectAttributes.addFlashAttribute("msg","添加失败");
    }
    }catch (Exception e){
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("msg","系统暂忙,添加失败,请稍后再试");
        return  "redirect:/admin/toBlogs";
    }
        return  "redirect:/admin/toBlogs";
    }


    /**
     * 查询博客信息
     */
    @GetMapping ("/Blogs")
    @ResponseBody
    public Object queryBlogs(String tagId, String title, boolean recommend,
             @RequestParam(defaultValue = "1")String pageNum
            ,@RequestParam(defaultValue = "5") String pageSize){
        Msg msg = new Msg();
        try{
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("tagIdConditions",tagId);
            hashMap.put("titleConditions",title);
            hashMap.put("recommendConditions",recommend?1:0);
            PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            List<Blog> blogs = blogServiceimpl.queryBlogCondition(hashMap);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            HashMap<String, Object> MsghashMap = msg.getHashMap( );
            MsghashMap.put("pageList",pageInfo);
            return  msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统太忙了,请稍后再查");
            return msg;
        }
    }

    /**
     * 删除博客信息
     */
    @DeleteMapping ("/Blogs")
    @ResponseBody
    public Object removeBlogs(String id){
        Msg msg = new Msg( );
        System.out.println(id );
        try{
        int i = blogServiceimpl.removeBlogAndBlogTagRelation(Long.parseLong(id));
        if (i>0){
            msg.setCode(MsgCode.SUCCESS_CODE);
        }else {
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("删除博客信息失败");
        }
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统繁忙,删除失败请稍后再试");
            return  msg;
        }
        return msg;
    }

    /**
     * 修改博客信息
     */
    @PutMapping("/Blogs")
    public String editBlogs(Blog blog, String tagIds,RedirectAttributes redirectAttributes){
        try{
            blog.setRecommendned(blog.getRecommendned()==null?false:true);
            blog.setCommentabled(blog.getCommentabled()==null?false:true);
            blog.setEdittime(DateTimeFormatUtils.getDateTime(new Date()));
            int i = blogServiceimpl.editBlogs(blog , tagIds);
            if (i>0){
                redirectAttributes.addFlashAttribute("msg","修改成功");
            }else {
                redirectAttributes.addFlashAttribute("msg","修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg","系统暂忙,修改失败,请稍后再试");
            return  "redirect:/admin/toBlogs";
        }
        return  "redirect:/admin/toBlogs";
    }


}
