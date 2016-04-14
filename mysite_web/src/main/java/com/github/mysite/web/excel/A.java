package com.github.mysite.web.excel;

import com.github.mysite.common.excel.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description: A
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
public class A {

    public A(){
    }

    @ExcelField(title = "名称", align = 2, sort = 2)
    private String     name;
    @ExcelField(title = "密码", type = 1, align = 2, sort = 1)
    private String     pwd;
    @ExcelField(title = "日期", align = 2, sort = 3)
    private Date       date;
    @ExcelField(value = "b.age", title = "年龄", align = 1, sort = 4, fieldType = B.class)
    private B          b;

    @ExcelField(title = "数量", align = 1, sort = 7)
    private BigDecimal num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getDate() {
        return date;
    }

    public A(String name, String pwd, Date date, BigDecimal num){
        this.num = num;
        this.name = name;
        this.pwd = pwd;
        this.date = date;

    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public BigDecimal getNum() {
        return num;
    }
}
