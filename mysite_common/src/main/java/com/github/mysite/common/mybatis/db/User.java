package com.github.mysite.common.mybatis.db;

import com.google.common.base.MoreObjects;

/**
 * description: User实体类
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-16 12:10
 */
public class User {

    private long   id;
    private String userName;
    private String passWord;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id).add("userName", userName).add("passWord",
                                                                                                             passWord).toString();
    }
}
