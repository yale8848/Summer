package ren.yale.java.controller;

import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Yale
 *
 * @create 2018-01-31 17:04
 **/
@Path("/hello")
public class Hello {

    @GET
    @Path("/h1")
    @Produces({MediaType.TEXT_HTML})
    public String h1(@Context RoutingContext routingContext){

        return "<html><body>bb</body></html>";
    }

}
