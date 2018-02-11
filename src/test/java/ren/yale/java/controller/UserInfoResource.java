package ren.yale.java.controller;

import io.vertx.core.Vertx;
import io.vertx.core.Vertx;
import ren.yale.java.bean.UserInfo;
import ren.yale.java.event.EventMessage;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Yale
 * create at: 2018-02-05 9:56
 **/
@Path("/user")
public class UserInfoResource {

    @Path("/login")
    public void login(@Context Vertx vertx){

        UserInfo userInfo = new UserInfo();
        userInfo.setName("aaa");
        userInfo.setPassword("bbb");




    }


}
