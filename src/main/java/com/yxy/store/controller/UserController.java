package com.yxy.store.controller;

import com.yxy.store.entity.User;
import com.yxy.store.service.IUserService;
import com.yxy.store.service.ex.InsertException;
import com.yxy.store.service.ex.UsernameDuplicatedException;
import com.yxy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

// @Controller
@RestController // @Controller +@ResponseBody
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /*
    @RequestMapping("reg")
    // @ResponseBody  // 表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
        // 创建响应结果对象
        JsonResult<Void> result=new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }
     */

    /*
    * 约定大于配制：开发思想来完成，省略大量的配制甚至注解的编写
    * 1. 接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据
    *   SpringBoot会将前端的url地址中的参数名和pojo类的属性名进行比较，
    *   如果这两个名称相同，则将值注入到pojo类中对应属性上
    */
    @RequestMapping("reg")
    // @ResponseBody  // 表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        // 返回给前端
        return new JsonResult<>(OK);
    }

    /*
     * 2. 接收数据方式：请求处理方法的参数列表设置为非pojo类型
     *   SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，
     *   如果名称相同则自动完成值的依赖注入
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session){
        User data=userService.login(username,password);
        // 向session对象中完成数据的绑定（session全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        // 获取session中绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        // 返回给前端
        return new JsonResult<User>(OK,data);
    }
}
