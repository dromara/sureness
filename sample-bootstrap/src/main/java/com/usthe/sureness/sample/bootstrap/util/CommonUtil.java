package com.usthe.sureness.sample.bootstrap.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * util
 * @author tomsun28
 * @date 17:37 2019-05-12
 */
public class CommonUtil {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    /** access failed message **/
    public static final String DENIED_ACCESS_RESOURCE = "access this resource: %s denied";

    /**
     * write response json data
     * @param content content
     * @param response response
     */
    public static void responseWrite(ResponseEntity content, ServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        ((HttpServletResponse)response).setStatus(content.getStatusCodeValue());
        content.getHeaders().forEach((key, value) ->
                ((HttpServletResponse) response).addHeader(key, value.get(0)));
        try (PrintWriter printWriter = response.getWriter()) {
            if (content.getBody() != null) {
                if (content.getBody() instanceof String) {
                    printWriter.write(content.getBody().toString());
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    printWriter.write(objectMapper.writeValueAsString(content.getBody()));
                }
            } else {
                printWriter.flush();
            }
        } catch (IOException e) {
            logger.error("responseWrite response error: ", e);
        }
    }
}
