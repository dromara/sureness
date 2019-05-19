package com.usthe.sureness.util;

import com.usthe.sureness.subject.SubjectAuToken;

import javax.servlet.http.HttpServletRequest;

/**
 * 一些公共处理工具类
 * @author tomsun28
 * @date 19:07 2019-03-09
 */
public class SurenessCommonUtil {

    private static final String USER_AGENT = "User-Agent";
    private static final String UNKNOWN = "unknown";
    private static final String ANDROID = "Android";
    private static final String LINUX = "Linux";
    private static final String IPHONE = "iPhone";
    private static final String WINDOWS = "Windows";
    private static final String CHROME = "Chrome";


    /**
     * description 通过传入的Object来创建具有基本信息的token
     * 基本信息： url===method
     * 此方法在web模块实现
     * @param var1 1
     * @return com.usthe.sureness.subject.SubjectAuToken
     */
    public static SubjectAuToken createSubjectAuToken(Object var1) {
        return null;
    }

    public static String findUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(USER_AGENT);
        if (userAgent == null || "".equals(userAgent)) {
            userAgent = UNKNOWN;
        } else if (userAgent.contains(ANDROID)) {
            userAgent = ANDROID;
        } else if (userAgent.contains(LINUX)) {
            userAgent = LINUX;
        } else if (userAgent.contains(IPHONE)) {
            userAgent = IPHONE;
        } else if (userAgent.contains(WINDOWS)) {
            userAgent = WINDOWS;
        } else if (userAgent.contains(CHROME)) {
            userAgent = CHROME;
        } else {
            userAgent = UNKNOWN;
        }
        // TODO more
        return userAgent;
    }


}
