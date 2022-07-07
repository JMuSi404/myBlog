package com.heshijia.myblog.web.controller.backstage;

import com.heshijia.myblog.utils.SessionInfo;
import com.heshijia.myblog.pojo.User;
import com.heshijia.myblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
  private UserServiceImpl userServiceImpl;
    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    @RequestMapping ("/login")
    public String login(String username,String password,RedirectAttributes redirectAttributes, HttpSession session){
        try{
            User user = userServiceImpl.getUserByUsernameAndPassword(username,password);
            if (user!=null){
                session.setAttribute(SessionInfo.LOGIN_INFO,user);
                return "admin/index";
            }else {
                redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
                return  "redirect:/toLogin";
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg","系统繁忙,请稍后再试");
            return  "redirect:/toLogin";
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/toIndex";
    }







}
