package com.yxy.store.mapper;

import com.yxy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


// @SpringBootTest: 表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
// @RunWith: 表示启动这个单元测试类（不写单元测试类是不能够运行的），需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    // idea有检测的功能，接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法：就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1. 必须被@Test注解所修饰
     * 2. 返回值类型必须是void
     * 3. 方法的参数列表不指定任何类型
     * 4. 方法的访问修饰符必须是public
     */

    @Test
    public void insert(){
        User user=new User();
        user.setUsername("tom");
        user.setPassword("123");
        Integer rows=userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user=userMapper.findByUsername("tom");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(6,"321","管理员",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(6));
    }

    @Test
    public void updateInfoByUid(){
        User user=new User();
        user.setUid(7);
        user.setPhone("12345678900");
        user.setEmail("test02@163.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(8,
                "/upload/avatar.png",
                "管理员",
                new Date());
    }
}
