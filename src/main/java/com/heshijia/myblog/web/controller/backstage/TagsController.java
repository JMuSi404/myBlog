package com.heshijia.myblog.web.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heshijia.myblog.pojo.Msg;
import com.heshijia.myblog.utils.MsgCode;
import com.heshijia.myblog.pojo.Tag;
import com.heshijia.myblog.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping ("/admin")
public class TagsController {
    @Autowired
  private   TagServiceImpl tagServiceimpl;

    /**
     * 跳转博客分类页面
     * @return
     */
    @RequestMapping("/toTags")
    public String toTags(){
        return "admin/tags";
    }
    /**
     * 跳转博客分类添加页面
     * @return
     */
    @RequestMapping ("/toTagsInput")
    public String toTagsInput(){
        return "admin/tags-input";
    }
    /**
     * 跳转博客分类修改页面
     * @return
     */
    @RequestMapping ("/toTagsEdit")
    public String toTagsEdit(String id, Model model){
        Tag tag = tagServiceimpl.queryAllById(Long.parseLong(id));
        model.addAttribute("Tag",tag);
        return "admin/tags-edit";
    }


    /**
     * 添加标签
     * @param tagName
     * @return
     */
    @RequestMapping(value = "/Tags",method = RequestMethod.POST)
    @ResponseBody
    public  Object saveTypes(String tagName){
        Msg msg = new Msg( );
        try{
            List<Tag> tags = tagServiceimpl.queryByName(tagName);
            if (tags.size()>0){
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("name已经被使用了");
                return  msg;
            }
            Tag tag = new Tag();
            tag.setName(tagName);
            int i = tagServiceimpl.saveTag(tag);
            if (i>0){
                msg.setCode(MsgCode.SUCCESS_CODE);
                return  msg;
            }else {
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("添加失败");
                return  msg;
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return  msg;
        }
    }

    /**
     * 查询标签
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/Tags",method = RequestMethod.GET)
    @ResponseBody
    public  Object QueryTypes(@RequestParam(defaultValue = "5")String pageSize,@RequestParam(defaultValue = "1") String pageNum) {
          Msg msg = new Msg( );
          try {
                Page<Tag> page = new Page<>(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
                Page<Tag> tagPage = tagServiceimpl.queryTagsPage(page);
              HashMap<String, Object> hashMap = msg.getHashMap( );
              hashMap.put("TagsPage",tagPage);
              return msg;
            }catch (Exception e){
                 e.printStackTrace();
              msg.setCode(MsgCode.FAIL_CODE);
              msg.setMessage("系统暂忙,请稍后再试!");
              return  msg;
            }
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/Tags",method = RequestMethod.DELETE)
    @ResponseBody
    public  Object QueryTypes(String id){
        Msg msg = new Msg( );
        try {
            int i = tagServiceimpl.removeTagsById(Long.parseLong(id));
            if (i>0){
                msg.setCode(MsgCode.SUCCESS_CODE);
                return  msg;
            }else {
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("删除失败");
                return  msg;
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return  msg;
        }

    }

    /**
     * 修改标签
     * @param
     * @return
     */
    @RequestMapping(value = "/Tags",method = RequestMethod.PUT)
    public  Object editTypes(Tag tag, RedirectAttributes redirectAttributes){
        int i = tagServiceimpl.editTagNameById(tag.getName( ),tag.getId( ));
        if (i>0){
            redirectAttributes.addFlashAttribute("msg","修改成功");
        }else {
            redirectAttributes.addFlashAttribute("msg","修改失败");
        }
        return  "redirect:/admin/toTags";
    }




}
