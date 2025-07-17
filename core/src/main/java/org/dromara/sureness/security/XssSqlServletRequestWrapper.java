package org.dromara.sureness.security;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * request xss sql filter wrapper
 * @author tomsun28
 * @date 20:41 2018/4/15
 */
public class XssSqlServletRequestWrapper extends HttpServletRequestWrapper {

    public XssSqlServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0 ; i < count ; i++ ) {
            encodedValues[i] = filterParamString(values[i]);
        }
        return encodedValues;
    }

    @Override
    public Map<String,String[]> getParameterMap() {
        Map<String,String[]> primary = super.getParameterMap();
        Map<String,String[]> result = new HashMap<>(16);
        for (Map.Entry<String,String[]> entry : primary.entrySet()) {
            result.put(entry.getKey(),filterEntryString(entry.getValue()));
        }
        return result;
    }

    @Override
    public String getParameter(String parameter) {
        return filterParamString(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return filterParamString(super.getHeader(name));
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(filterParamString(cookie.getValue()));
            }
        }
        return cookies;
    }

    private String[] filterEntryString(String[] value) {
        for (int i = 0; i < value.length; i++) {
            value[i] = filterParamString(value[i]);
        }
        return value;
    }

    /**
     * filter value xss and sql inject
     * @param value content
     * @return java.lang.String content
     */
    private String filterParamString(String value) {
        return value == null ? null : XssSqlUtil.stripSqlXss(value);
    }

}
