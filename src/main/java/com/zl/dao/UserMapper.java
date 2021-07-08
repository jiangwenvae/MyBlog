package com.zl.dao;

import com.zl.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    public User getUserById(Integer id);

    public User getUserByNameAndPwd(@Param("name")String name,@Param("pwd")String pwd);
}
