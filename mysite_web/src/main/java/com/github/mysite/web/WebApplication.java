package com.github.mysite.web;

import com.alibaba.druid.support.http.StatViewServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;

/**
 * spring-boot的一些注解 1.@Configuration 注解是spring去xml配置,是spring基于java的配置
 * 
 * @Configuration类级别的注解， 一般这个注解， 我们用来标识main方法所在的类,完成元数据bean的初始化。
 * @Bean 一个带有 @Bean 的注解方法将返回一个对象 2.@ComponentScan 收集自动收集所有的spring组件 搜索beans
 * 类级别的注解，自动扫描加载所有的Spring组件包括Bean注入，一般用在main方法所在的类上 3.@Import导入其他的Configuration类 4. @ImportResource附加注入一个外置的xml 5. @EnableAutoConfiguration
 * 和 @SpringBootApplication是类级别的注解， 根据maven依赖的jar来自动猜测完成正确的spring的对应配置，只要引入了spring-boot-starter-web的依赖，默认会自动配置Spring
 * MVC和tomcat容器 6. @Component类级别注解，用来标识一个组件，比如我自定了一个filter，则需要此注解标识之后，Spring Boot才会正确识别。 Spring-boot加载配置文件的顺序是 1.
 * ./config/application.properties 2. ./application.properties 3. classpath:config/application.properties 4.
 * classpath:application.properties yaml文件里配置项名和bean里的属性名相同
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
@SpringBootApplication
public class WebApplication extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebApplication.class);

    @Value("${druid.allow}")
    private String              druidAllowUrl;

    @Value("${druid.deny}")
    private String              druidDenyUrl;

    @Value("${druid.loginUsername}")
    private String              loginUsername;

    @Value("${druid.loginPassword}")
    private String              loginPassword;

    @Value("${druid.resetEnable}")
    private String              resetEnable;                                         // 禁用HTML页面上的“Reset All”功能

    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", druidAllowUrl);
        reg.addInitParameter("deny", druidDenyUrl);
        reg.addInitParameter("loginPassword", loginPassword);
        reg.addInitParameter("loginUsername", loginUsername);
        reg.addInitParameter("resetEnable", resetEnable);

        return reg;
    }

    @Bean
    public ResourceBundleViewResolver viewResolver() {
        ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
        resolver.setBasename("mysite_views");
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

    // 注释，待排查
    // @Bean
    // public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
    // ErrorProperties errorProperties = new ErrorProperties();
    // errorProperties.setIncludeStacktrace(ErrorProperties.IncludeStacktrace.ALWAYS);
    // return new WebErrorController(errorAttributes, errorProperties);
    // }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/WEB-INF/static/");
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
