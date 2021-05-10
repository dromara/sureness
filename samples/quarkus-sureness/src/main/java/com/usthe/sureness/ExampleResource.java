package com.usthe.sureness;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

/**
 * @author tomsun28
 */
@Path("/")
public class ExampleResource {

    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    @GET
    @Path("/api/v1/bar/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock1(@Context Request servletRequest, @PathParam("id") String id) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "get /api/v1/bar/" + id);
    }

    @POST
    @Path("/api/v1/bar")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock2(@Context Request servletRequest) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "post /api/v1/bar");
    }

    @PUT
    @Path("/api/v2/bar")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock3(@Context Request servletRequest) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "put /api/v2/bar");
    }

    @GET
    @Path("/api/v2/foo")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock4(@Context Request servletRequest) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "get /api/v2/foo");
    }

    @DELETE
    @Path("/api/v2/foo")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock5(@Context Request servletRequest) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "delete /api/v2/foo");
    }

    @GET
    @Path("/api/v3/foo")
    @Produces(MediaType.TEXT_PLAIN)
    public String api1Mock6(@Context Request servletRequest) {
        return String.format(SUCCESS_ACCESS_RESOURCE, "get /api/v3/foo");
    }
}