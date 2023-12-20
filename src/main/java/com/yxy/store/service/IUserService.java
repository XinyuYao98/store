package com.yxy.store.service;

import com.yxy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/* 用户模块业务层接口 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户数据
     */
    User login(String username,String password);

    /**
     * 用户修改密码的方法
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 用户输入的旧密码
     * @param newPassword 用户输入的新密码
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据操作
     * @param uid 用户的id
     * @param username 用户的名称
     * @param user 用户对象的数据
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 修改用户的头像
     * @param uid 用户的id
     * @param avatar 用户头像的路径
     * @param username 用户的名称
     */
    void changeAvatar(Integer uid,
                      String avatar,
                      String username);

}
