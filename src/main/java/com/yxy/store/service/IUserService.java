package com.yxy.store.service;

import com.yxy.store.entity.User;

/* 用户模块业务层接口 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param usename 用户名
     * @param password 密码
     * @return 登录成功的用户数据
     */
    User login(String usename,String password);
}
