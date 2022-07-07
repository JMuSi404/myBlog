package com.heshijia.myblog.web.controller.backstage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heshijia.myblog.pojo.Msg;
import com.heshijia.myblog.pojo.Saying;
import com.heshijia.myblog.service.impl.SayingServiceImpl;
import com.heshijia.myblog.utils.MsgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 废弃了
 * 找到了更好的开放 api 接口
 */
@Controller
@RequestMapping ("/admin")
public class SayingController {
    @Autowired
    SayingServiceImpl sayingServiceImpl;

    /**
     * 跳转名言管理页面
     * @return
     */
    @RequestMapping("/toSaying")
    public String toSaying(){
        return "admin/saying";
    }
    /**
     * 跳转名言添加页面
     * @return
     */
    @RequestMapping ("/toSayingInput")
    public String toSayingInput(){
        return "admin/saying-input";
    }
    /**
     * 跳转名言修改页面
     * @return
     */
    @RequestMapping ("/toSayingEdit")
    public String toSayingEdit(String id,Model model){
        Saying saying = sayingServiceImpl.selectAllById(Long.parseLong(id));
        model.addAttribute("saying", saying);
        return "admin/saying-edit";
    }


    /**
     * 添加名言
     * @param
     * @return
     */
    @RequestMapping(value = "/saying",method = RequestMethod.POST)
    @ResponseBody
    public  Object saveSaying(String sayingName,String sayingNumber){
        Msg msg = new Msg( );
        try{
            List<Saying> sayings = sayingServiceImpl.queryByName(sayingName);
            if (sayings.size()>0){
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("已经有相同的名言了");
                return  msg;
            }
            List<Saying> Serialnumber = sayingServiceImpl.selectAllBySerialnumber(Integer.parseInt(sayingNumber));
            if (Serialnumber.size()>0){
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("序号重复了");
                return  msg;
            }

            Saying saying = new Saying( );
            saying.setName(sayingName);
            saying.setSerialnumber(Integer.parseInt(sayingNumber));
            int i = sayingServiceImpl.saveSaying(saying);
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
     * 查询名言
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/saying",method = RequestMethod.GET)
    @ResponseBody
    public  Object Querysaying(@RequestParam (defaultValue = "5")String pageSize, @RequestParam(defaultValue = "1") String pageNum) {
        Msg msg = new Msg( );
        try {
            PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt( pageSize));
            List<Saying> sayings = sayingServiceImpl.querySayingListPage( );
            PageInfo<Saying> PageInfo = new PageInfo<>( sayings );
            HashMap<String, Object> hashMap = msg.getHashMap( );
            hashMap.put("sayingPage",PageInfo);
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return  msg;
        }
    }

    /**
     * 删除名言
     * @param id
     * @return
     */
    @RequestMapping(value = "/saying",method = RequestMethod.DELETE)
    @ResponseBody
    public  Object deletesaying(String id){
        Msg msg = new Msg( );
        try {
            int i = sayingServiceImpl.removeSayingById(Long.parseLong(id));
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
     * 修改名言
     * @param
     * @return
     */
    @RequestMapping(value = "/saying",method = RequestMethod.PUT)
    public  Object editSaying(Saying saying, RedirectAttributes redirectAttributes){
        int i = sayingServiceImpl.editSaying(saying);
        if (i>0){
            redirectAttributes.addFlashAttribute("msg","修改成功");
        }else {
            redirectAttributes.addFlashAttribute("msg","修改失败");
        }
        return  "redirect:/admin/toSaying";
    }


    /**
     * 随机获取名言
     * @param
     * @return
     */
    @RequestMapping(value = "/RandomlySaying")
    @ResponseBody
    public  Object RandomlySaying(){
        Msg msg = new Msg();
        try {
            Random random = new Random( );
            int j = sayingServiceImpl.selectSayingCount();
            int i = random.nextInt(j);
            Saying saying = sayingServiceImpl.selectBySerialnumber((i+1));
            if (saying != null) {
                msg.setCode(MsgCode.SUCCESS_CODE);
                HashMap<String, Object> hashMap = msg.getHashMap( );
                hashMap.put("saying",saying);
            } else {
                msg.setCode(MsgCode.FAIL_CODE);
                msg.setMessage("获取名言失败");
            }
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(MsgCode.FAIL_CODE);
            msg.setMessage("系统暂忙,请稍后再试!");
            return  msg;
        }
    }
}
