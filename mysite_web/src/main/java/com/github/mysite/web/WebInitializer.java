package com.github.mysite.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * description: System WebInitializer
 * 
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
public class WebInitializer extends SpringBootServletInitializer {

    //@Bean
    //@Order
    //public ServletRegistrationBean statViewServlet() {
    //    StatViewServlet servlet = new StatViewServlet();
    //    ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/druid/*");
    //    return bean;
    //}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }
}
