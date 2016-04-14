package com.github.mysite.web.excel;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * description: Excel export Controller
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
@Controller
@RequestMapping("excel")
public class ExcelController {

    @RequestMapping(value = {"test", "test.xls"})
    public ModelAndView test() {
        List<A> data = Lists.newArrayList();
        A a1 = new A("a", "b",new Date(),new BigDecimal(122334));
        A a2 = new A("c", "d", new Date(),new BigDecimal(234232345));

        B b1 = new B();
        B b2= new B();
        b1.setAge("1231231");
        b2.setAge("adfasdfas");
        a1.setB(b1);
        a2.setB(b2);
        data.add(a1);
        data.add(a2);
        String fileName = "文件名.xlsx";
        String titleName = "测试";
        ExcelBuilder excelBuilder = new ExcelBuilder(fileName,titleName, data,A.class);
        return new ModelAndView("excelBuilder", "excelBuilder", excelBuilder);
    }

}

