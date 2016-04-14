package com.github.mysite.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: Spring boot Error Handler
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
public class WebErrorController extends BasicErrorController {

    private static final Logger  LOG     = LoggerFactory.getLogger(WebErrorController.class);
    private final static Pattern pattern = Pattern.compile("Android|Windows Phone|webOS|iPhone|iPod|BlackBerry|iPad");

    private static boolean isMobile(HttpServletRequest request) {
        Matcher matcher = pattern.matcher(request.getHeader("user-agent"));
        return matcher.find();
    }

    public WebErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties){
        super(errorAttributes, errorProperties);
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        setMessage(status, model);
        LOG.error("页面错误：{}", model);
        return new ModelAndView(isMobile(request) ? "error.mobile" : "error", model);
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {

        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        setMessage(status, body);
        // 业务异常当正常返回,因JS要进行异常信息提示
        String exception = body.get("exception") == null ? "" : body.get("exception").toString();
        if (RuntimeException.class.getName().equals(exception)) {
            status = HttpStatus.OK;
        }
        LOG.error("数据错误：{}", body);
        body.remove("trace");// 移除异常堆栈
        return new ResponseEntity<>(body, status);
    }

    public void setMessage(HttpStatus status, Map<String, Object> model) {
        Object message = model.get("message");
        if (message != null) {
            if (status != null && status == HttpStatus.NOT_FOUND) {
                model.put("message", "找不到页面");
            } else if ("No message available".equals(message)) {
                model.put("message", "系统出现异常");
            }
        }
    }

}
