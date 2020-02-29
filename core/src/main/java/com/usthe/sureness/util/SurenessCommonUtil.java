package com.usthe.sureness.util;


import javax.servlet.http.HttpServletRequest;
import java.util.Random;

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

    private static final String RANDOM_CHAR = "abcdefghijklmnopqrstuvwxyz0123456789";

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
        return userAgent;
    }


    /**
     *  获取指定位数的随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        // 默认6位
        if (length < 1) {
            length = 6;
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(RANDOM_CHAR.length());
            sb.append(RANDOM_CHAR.charAt(number));
        }
        return sb.toString();
    }
}
