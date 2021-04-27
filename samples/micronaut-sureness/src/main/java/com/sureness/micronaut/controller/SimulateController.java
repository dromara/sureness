package com.sureness.micronaut.controller;


import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
public class SimulateController {

    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    @Get("/api/v1/source1")
    public HttpResponse<Map<String, String>> api1Mock1(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Post("/api/v1/source1")
    public HttpResponse<Map<String, String>> api1Mock2(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Put("/api/v1/source1")
    public HttpResponse<Map<String, String>> api1Mock3(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Delete("/api/v1/source1")
    public HttpResponse<Map<String, String>> api1Mock4(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Patch("/api/v1/source1")
    public HttpResponse<Map<String, String>> api1Mock5(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v1/source2/{var1}/{var2}")
    public HttpResponse<Map<String, String>> api1Mock6(HttpRequest request, @PathVariable String var1, @PathVariable Integer var2 ) {
        Map<String, String> resultMap = new HashMap<>(3);
        resultMap.putAll(getResponseMap(request));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        if (Objects.nonNull(var2)) {
            resultMap.put("var2", String.valueOf(var2));
        }
        return HttpResponse.ok(resultMap);
    }

    @Post("/api/v1/source2/{var1}")
    public HttpResponse<Map<String, String>> api1Mock7(HttpRequest request,
                                                       @PathVariable String var1) {
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.putAll(getResponseMap(request));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        return HttpResponse.ok(resultMap);
    }

    @Get("/api/v1/source2")
    public HttpResponse<Map<String, String>> api1Mock8(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Patch("/api/v1/source2")
    public HttpResponse<Map<String, String>> api1Mock10(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v1/source3")
    public HttpResponse<Map<String, String>> api1Mock11(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Post("/api/v1/source3")
    public HttpResponse<Map<String, String>> api1Mock12(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Put("/api/v1/source3")
    public HttpResponse<Map<String, String>> api1Mock13(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Delete("/api/v1/source3")
    public HttpResponse<Map<String, String>> api1Mock14(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v2/source3/{var1}")
    public HttpResponse<Map<String, String>> api1Mock15(HttpRequest request, @PathVariable String var1) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v2/source4")
    public HttpResponse<Map<String, String>> api2Mock16(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Post("/api/v2/source4")
    public HttpResponse<Map<String, String>> api2Mock17(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Put("/api/v2/source4")
    public HttpResponse<Map<String, String>> api2Mock18(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Delete("/api/v2/source4")
    public HttpResponse<Map<String, String>> api2Mock19(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Patch("/api/v2/source4")
    public HttpResponse<Map<String, String>> api2Mock20(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v2/source5")
    public HttpResponse<Map<String, String>> api2Mock21(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Post("/api/v2/source5")
    public HttpResponse<Map<String, String>> api2Mock22(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Delete("/api/v2/source5")
    public HttpResponse<Map<String, String>> api2Mock23(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Put("/api/v2/source5")
    public HttpResponse<Map<String, String>> api2Mock24(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Patch("/api/v2/source5")
    public HttpResponse<Map<String, String>> api2Mock25(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Get("/api/v2/source6")
    public HttpResponse<Map<String, String>> api2Mock26(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    @Post("/api/v2/source6")
    public HttpResponse<Map<String, String>> api2Mock27(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }
    @Delete("/api/v2/source6")
    public HttpResponse<Map<String, String>> api2Mock28(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }
    @Put("/api/v2/source6")
    public HttpResponse<Map<String, String>> api2Mock29(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }
    @Patch("/api/v2/source6")
    public HttpResponse<Map<String, String>> api2Mock30(HttpRequest request) {
        return HttpResponse.ok(getResponseMap(request));
    }

    /**
     * get the response map data from request
     * @param request http request
     * @return map data
     */
    private Map<String, String> getResponseMap(HttpRequest request) {
        StringBuilder builder = new StringBuilder();
        String requestUri = request.getUri().toString();
        builder.append(requestUri);
        builder.append("--");
        String requestType = request.getMethod().toString();
        builder.append(requestType);
        builder.append("--");
        return Collections.singletonMap("result", String.format(SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }
}
