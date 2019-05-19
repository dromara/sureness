package com.usthe.sureness.sample.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 工具类
 * @author tomsun28
 * @date 17:37 2019-05-12
 */
public class CommonUtil {

    /** 日志操作 **/
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /** 访问资源成功的信息 **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    /** 访问资源失败的信息 **/
    public static final String DENIED_ACCESS_RESOURCE = "access this resource: %s denied";

    /**
     * description 封装response  统一json返回
     *
     * @param content 内容
     * @param response response
     */
    public static void responseWrite(ResponseEntity content, ServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(content.toString());
        }catch (Exception e) {
            logger.error("responseWrite response error: ", e);
        }
    }

    public static void main(String[] args) {
        String matchRoleString = "[role2]";
        String tmp = matchRoleString.substring(1, matchRoleString.length()-1);
        String[] roless = tmp.split(",");
        String[] roles = matchRoleString.substring(1, matchRoleString.length()-1).split(",");
    }
}
