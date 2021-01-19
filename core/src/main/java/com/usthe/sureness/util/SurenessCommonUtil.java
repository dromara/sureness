package com.usthe.sureness.util;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * common util
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
    private static final String PATH_SPLIT = "/";

    private static final String RANDOM_CHAR = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    /**
     * match the userAgent
     * @param userAgent string from request
     * @return userAgent
     */
    public static String findUserAgent(String userAgent) {
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
     *  get random string
     *
     * @param length string length
     * @return random string
     */
    public static String getRandomString(int length) {
        // default length is 6
        if (length < 1) {
            length = 6;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = RANDOM.nextInt(RANDOM_CHAR.length());
            sb.append(RANDOM_CHAR.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Splice the contextPath to the head of the each uriResource
     * @param contextPath context path eg: "/v2"
     * @param uriResource set of uriResource eg: "/school/book===get===[role1]"
     * @return set of uriResource eg: "/v2/school/book===get===[role1]"
     */
    public static Set<String> attachContextPath(String contextPath, Set<String> uriResource) {
        if (contextPath == null || "".equals(contextPath) || uriResource == null || uriResource.isEmpty()) {
            return uriResource;
        }
        // format context path
        contextPath = contextPath.toLowerCase().trim();
        contextPath = contextPath.replace("//", "/");
        if (!contextPath.startsWith(PATH_SPLIT)) {
            contextPath = PATH_SPLIT.concat(contextPath);
        }
        if (contextPath.endsWith(PATH_SPLIT)) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        final String finalContextPath = contextPath;
        return uriResource.stream().map(resource ->
                resource.startsWith(PATH_SPLIT)
                    ? finalContextPath.concat(resource)
                    : finalContextPath.concat(PATH_SPLIT).concat(resource)
        ).collect(Collectors.toSet());
    }
}
