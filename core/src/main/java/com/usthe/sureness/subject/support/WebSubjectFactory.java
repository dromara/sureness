package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tomsun28
 * @date 23:35 2019-05-12
 */
public class WebSubjectFactory extends BaseSubjectFactory {

    @Override
    public SubjectAuToken createSubjectAuToken(Object request) {
        if (request instanceof ServletRequest) {
            Enumeration enums = ((HttpServletRequest)request).getHeaderNames();
            Map<String, String> headerMap = new HashMap<>();
            while (enums.hasMoreElements()) {
                String name = (String) enums.nextElement();
                String value = ((HttpServletRequest)request).getHeader(name);
                if (null != value && !"".equals(value)) {
                    headerMap.put(name,value);
                }
            }
            // 根据head里面的参数内容，判断其请求认证鉴权的方式，新建对应的token
            // 现在支持
            // ("Authorization", "JWT eyJhbGciOiJIUzUxMi...")
            // ("Authorization", "JWT eyJhbGciOiJIUzUxMi...")



            // 根据 request里的不同参数创建不同的token

           return null;
        } else {
            return null;
        }
    }
}
