package ren.yale.java.controller;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import ren.yale.java.aop.After;
import ren.yale.java.aop.Before;
import ren.yale.java.bean.User;
import ren.yale.java.event.EventMessage;
import ren.yale.java.test.ChangeUserInterceptor;
import ren.yale.java.test.LogInterceptor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Yale
 *
 * create at:  2018-01-31 17:04
 **/
@Path("/test")
@Before(LogInterceptor.class)
public class Hello {

    @GET
    @Path("/html/{name}")
    @Produces({MediaType.TEXT_HTML})
    public String h1(@Context RoutingContext routingContext,
                     @PathParam("name") String name,
                     @DefaultValue("18") @QueryParam("age") int age, String text){
        return String.format("<html><body>name:%s,age:%d</body></html>",name,age);
    }

    @POST
    @Path("/name/bob")
    @Produces({MediaType.APPLICATION_JSON})
    public User h2(@QueryParam("age") int age,@FormParam("name") String name) {
        User u = new User();
        u.setName(name);
        u.setAge(age);
        return u;
    }

    @GET
    @Path("/async")
    public void h3(@Context HttpServerResponse response, @Context Vertx vertx){

        vertx.eventBus().send("user",EventMessage.message("bob").setKey("name"),message->{
            EventMessage eventMessage = (EventMessage) message.result().body();
           if (eventMessage.isSuccess()){
               String ret= String.format("name:%s,age:%d",eventMessage.getMessage(),18);
               response.end(ret);
          }else{
               response.end("error");
           }
        });

    }

    @GET
    @Path("/xml")
    @Produces({MediaType.TEXT_XML})
    public User xml(){
        return new User();
    }

    @GET
    @Path("/aop")
    @Produces({MediaType.APPLICATION_JSON})
    @After(ChangeUserInterceptor.class)
    public User getInter(){
        User u = new User();
        u.setName("bob");
        u.setAge(18);
        return u;
    }


    @HEAD
    @Path("/head")
    public void head(@Context RoutingContext routingContext){
        routingContext.response().setStatusCode(200).end();
    }
    @PUT
    @Path("/put/bob/{age}")
    @Produces({MediaType.APPLICATION_JSON})
    public User put(@Context RoutingContext routingContext,@PathParam("age") int age){

        User u = new User();
        u.setName("bob");
        u.setAge(age);
        return u;
    }

    @DELETE
    @Path("/delete/{name}")
    public String delete(@Context RoutingContext routingContext, @PathParam("name") String name){
        return "delete "+name+" success";
    }

}
