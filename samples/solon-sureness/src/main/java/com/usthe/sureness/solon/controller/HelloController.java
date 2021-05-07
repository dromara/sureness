package com.usthe.sureness.solon.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ContextUtil;
import org.noear.solon.core.handle.MethodType;

/**
 * @author tomsun28
 * @date 2021/5/7 19:58
 */
@Controller
public class HelloController {

    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: {%s - %s} success";

    @Mapping(value = "/api/v1/source1", method = MethodType.GET)
    public String api1Mock1() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v1/source1", method = MethodType.POST)
    public String api1Mock2() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v1/source1", method = MethodType.PUT)
    public String api1Mock3() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v1/source1", method = MethodType.DELETE)
    public String api1Mock4() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v2/host", method = MethodType.GET)
    public String api1Mock5() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v2/host", method = MethodType.POST)
    public String api1Mock6() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v2/host", method = MethodType.PUT)
    public String api1Mock7() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v2/host", method = MethodType.DELETE)
    public String api1Mock8() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v3/host", method = MethodType.GET)
    public String api1Mock9() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }

    @Mapping(value = "/api/v3/book", method = MethodType.PUT)
    public String api1Mock0() {
        Context context = ContextUtil.current();
        return String.format(SUCCESS_ACCESS_RESOURCE, context.method(), context.path());
    }
}
