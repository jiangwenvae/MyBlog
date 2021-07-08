package com.zl.service;

import com.zl.bean.User;
import com.zl.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUserById(Integer id){
        if(id<0) return new User();
        User user = userMapper.getUserById(id);
        return user;
    }

    /**
     * 登陆验证
     */
    public boolean cheklogin(String name,String pwd){
        User user = userMapper.getUserByNameAndPwd(name, pwd);
        if(user!=null){
            return true;
        }else {
            return false;
        }
    }
}
