package com.github.mysite.web.mybatis.dao;

import com.github.mysite.common.mybatis.db.User;
import com.github.mysite.web.mybatis.MyBatisDao;

import java.util.List;

/**
 * description: User接口
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-16 12:20
 */
@MyBatisDao
public interface UserMapper {

    public User getUserById(long id);

    public void saveUser(User user);

    public void updateUserById(User user);

    public void deleteUserById(long id);

    public List<User> getAllUser();
}
