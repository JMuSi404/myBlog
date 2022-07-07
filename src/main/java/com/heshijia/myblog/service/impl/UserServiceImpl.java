package com.heshijia.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshijia.myblog.pojo.User;
import com.heshijia.myblog.service.UserService;
import com.heshijia.myblog.mapper.UserMapper;
import com.heshijia.myblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-05-23 21:56:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
@Autowired
private  UserMapper userMapper;

    @Override
    public User getUserByUsernameAndPassword (String username , String password) {
        User user = userMapper.selectByUsernameAndPassword(username , MD5Utils.getMD5(password));
        return user;
    }
}




