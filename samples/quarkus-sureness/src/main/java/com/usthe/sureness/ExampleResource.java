package com.usthe.sureness;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

/**
 * @author tomsun28
 */
@Path("/")
public class ExampleResource {

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context Request servletRequest) {
        return "hello";
    }

    @GET
    @Path("api/v3/host")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock1(@Context Request servletRequest) {
        return "get api/v3/host";
    }

    @GET
    @Path("api/v2/host")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock2(@Context Request servletRequest) {
        return "get api/v2/host";
    }

    @POST
    @Path("api/v2/host")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock3(@Context Request servletRequest) {
        return "post api/v2/host";
    }

    @PUT
    @Path("api/v2/host")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock4(@Context Request servletRequest) {
        return "put api/v2/host";
    }

    @DELETE
    @Path("api/v2/host")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock5(@Context Request servletRequest) {
        return "delete api/v2/host";
    }

    @DELETE
    @Path("api/v2/getSource2/book")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock6(@Context Request servletRequest) {
        return "get /api/v2/getSource2/book";
    }

    @GET
    @Path("api/v1/source1")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock7(@Context Request servletRequest) {
        return "get api/v1/source1";
    }

    @DELETE
    @Path("api/v1/source1")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock8(@Context Request servletRequest) {
        return "delete api/v1/source1";
    }

    @POST
    @Path("api/v1/source1")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock9(@Context Request servletRequest) {
        return "post api/v1/source1";
    }

    @PUT
    @Path("api/v1/source1")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock0(@Context Request servletRequest) {
        return "put api/v1/source1";
    }
}