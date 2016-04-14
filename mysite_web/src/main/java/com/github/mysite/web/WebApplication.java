package com.github.mysite.web;

import com.github.mysite.web.error.WebErrorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;

/**
 * description: WebInitializer
 * 
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
// same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
public class WebApplication extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebApplication.class);

    @Bean
    public ResourceBundleViewResolver viewResolver() {
        ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
        resolver.setBasename("views");
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                LOG.info("ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                LOG.info("ServletContext destroyed");
            }

        };
    }

    @Bean
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
        ErrorProperties errorProperties = new ErrorProperties();
        errorProperties.setIncludeStacktrace(ErrorProperties.IncludeStacktrace.ALWAYS);
        return new WebErrorController(errorAttributes, errorProperties);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
