package com.github.mysite.web.test;

import com.github.mysite.common.mybatis.db.User;
import com.github.mysite.web.WebApplication;
import com.github.mysite.web.mybatis.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
public class TestDruid {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void Test1() {
        List<User> user = userMapper.getAllUser();
        System.out.println(user.get(0).toString());
    }
}
