package com.github.mysite.web.test;

import com.github.mysite.web.WebApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * description: 测试用例
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-31 16:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=9090"})
public class HelloControllerIT {
    private String base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = "http://localhost:9090/";
        template = new TestRestTemplate();
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response =
                template.getForEntity(base, String.class);
        assertThat(response.getBody(),
                is("Greetings from Spring Boot!"));
    }
}
