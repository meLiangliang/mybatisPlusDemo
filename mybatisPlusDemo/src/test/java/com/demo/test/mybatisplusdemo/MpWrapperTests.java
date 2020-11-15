package com.demo.test.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.demo.test.mybatisplusdemo.mapper.UserMapper;
import com.demo.test.mybatisplusdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpWrapperTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);
    }


    @Test
    public void testSelectList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .notLike("name", "e")
                .likeRight("email", "t");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

//  注意：这里使用的是 UpdateWrapper
//  不调用or则默认为使用and连
    @Test
    public void testUpdate1() {
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .or()
                .between("age", 20, 30);
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }

}
