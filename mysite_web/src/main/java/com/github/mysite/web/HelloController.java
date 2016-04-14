package com.github.mysite.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: HelloController
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String test() {
        return "Greetings from Spring Boot!";
    }
}
