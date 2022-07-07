package com.heshijia.myblog.service;

import com.heshijia.myblog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-05-23 21:56:47
*/
public interface UserService extends IService<User> {


  User  getUserByUsernameAndPassword(String username,String password);
}
