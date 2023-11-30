package com.yxy.store.service.impl;

import com.yxy.store.entity.User;
import com.yxy.store.mapper.UserMapper;
import com.yxy.store.service.IUserService;
import com.yxy.store.service.ex.InsertException;
import com.yxy.store.service.ex.PasswordNotMatchException;
import com.yxy.store.service.ex.UserNotFindException;
import com.yxy.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/* 用户模块业务层的实现类 */
@Service // @Service注解：将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        // 通过user参数来获取传递过来的username
        String username=user.getUsername();
        // 调用findByUsername(username)判断用户是否被注册过
        User result=userMapper.findByUsername(username);
        if(result!=null){
            // 抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 密码加密处理的实现： md5算法的形式21adsf-asdfadfs-545adfafsd-afd54f-35468
        // 串 + password +串 ---- 交给md5算法进行加密，连续加载三次
        // 盐值 + password +盐值 ---- 盐值就是一个随机的字符串
        // 获取原有密码：oldPassword
        String oldPassword=user.getPassword();
        // 获取盐值（随机生成一个盐值（串））
        String salt=UUID.randomUUID().toString().toUpperCase();
        // 补全数据：盐值的记录
        user.setSalt(salt);
        // 将密码和盐值作为一个整体进行加密处理，忽略原有的密码强度，提升了数据安全性
        String md5Password=getMD5Password(oldPassword,salt);
        // 将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        // 补全数据：is_delete设置成0
        user.setIsDelete(0);
        // 补全数据：4个日志字段
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date=new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务功能的实现(rows==1)
        Integer rows=userMapper.insert(user);
        if(rows!=1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户的数据是否存在，如果不存在则抛出异常
        User result=userMapper.findByUsername(username);
        if(result==null){
            throw new UserNotFindException("用户数据不存在");
        }
        // 检测用户的密码是否匹配
        // 1.先获取到数据库中加密之后的密码
        String oldMd5Password= result.getPassword();
        // 2.和用户传递过来的密码进行比较
        // 2.1 先获取salt值
        String salt=result.getSalt();
        // 2.2 将用户的密码按照相同的md5算法的规则进行加密
        String newMd5Password=getMD5Password(password,salt);

        //String md5Password=getMD5Password(oldPassword,salt);
        if(!oldMd5Password.equals(newMd5Password)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        // 判断is_delete字段值是否为1,为1代表该用户已被标记为删除
        if(result.getIsDelete()==1){
            throw new UserNotFindException("用户数据不存在");
        }

        // 提升系统的性能，将大体量数据(result)减小成小体量数据(new User())
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用(uid, username, avatar)
        return user;
    }

    /* 定义一个md5算法的加密 */
    private String getMD5Password(String password,String salt){
        // md5加密算法方法的调用（3次加密）
        for(int i=0;i<3;i++) {
            password=DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        // 返回加密之后的密码
        return password;
    }
}
