package com.demo.test.mybatisplusdemo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.test.mybatisplusdemo.mapper.UserMapper;
import com.demo.test.mybatisplusdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisplusdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;


    /*查询数据操作*/
    @Test
    public void testGetAll() {
        List<Map<String, Object>> users = userMapper.selectMaps(null);
        for (int i = 0; i < users.size(); i++) {
            System.out.println("users = " + users.get(i));
        }
    }
    /*增加数据操作*/
    @Test
    public void  insertUser(){
        User user = new User();
        user.setAge(24);
        user.setEmail("Bob@q]163.com");
        user.setName("Bob Alen");
        int i = userMapper.insert(user);
        System.out.println("受影响的行数 = " + i);
    }

    /*修改数据操作*/
    @Test
    public void modifyUser(){
        User user = new User();
        user.setId(8L);
        user.setName("Bill");
        int result = userMapper.updateById(user);
        System.out.println("受影响的行数 = " + result);
    }

    /**
     2
     * 测试 乐观锁插件
     3
     */
    @Test
    public void testOptimisticLocker() {
        //查询
        User user = userMapper.selectById(8L);
        //修改数据
        user.setName("Helen");
        user.setEmail("helen@qq.com");
        //执行更新
        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁插件 失败
     */
    @Test
    public void testOptimisticLockerFail() {
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        //执行更新
        userMapper.updateById(user);
    }

    /**
     * 测试分页
     */
    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1,5);//当前页，每页多少条
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
        System.out.println("总条数="+page.getTotal());
        System.out.println("是否有下一页："+page.hasNext());
        System.out.println("是否有上一页："+page.hasPrevious());
    }
    /**
     * 测试selectMapsPage分页：结果集是Map
     */
    @Test
    public void testSelectMapPage(){
        Page<User> page = new Page<>(1, 5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);
        mapIPage.getRecords().forEach(System.out::println);
    }

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(10L);
        System.out.println(result);
    }

}
