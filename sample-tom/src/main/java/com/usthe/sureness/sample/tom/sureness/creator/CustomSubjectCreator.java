package com.usthe.sureness.sample.tom.sureness.creator;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.PasswordSubject;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的subject creator
 * 这里演示一个自定义的creator
 * 我们平时账户密码认证除了basic auth方式之外
 * 可能会自定义从其他地方获取我们的账户密码来认证 eg: header的username字段作为账号,password字段作为密码
 * 即 header {
 *     "username": "userTom",
 *     "password": "123456"
 * }
 * 我们自定义一个creator 从header上面取出信息创建PasswordSubject
 * @author tomsun28
 * @date 22:59 2020-03-02
 */
public class CustomSubjectCreator implements SubjectCreate {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public boolean canSupportSubject(Object context) {
        // 定义什么样的请求信息才能被CustomSubjectCreator创建subject
        if (context instanceof HttpServletRequest) {

            String username = ((HttpServletRequest)context).getHeader(USERNAME);
            String password = ((HttpServletRequest)context).getHeader(PASSWORD);
            return username != null && password != null;
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        // 创建PasswordSubject
        String username = ((HttpServletRequest)context).getHeader(USERNAME);
        String password = ((HttpServletRequest)context).getHeader(PASSWORD);

        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ((HttpServletRequest) context).getRequestURI();
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
