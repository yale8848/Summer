package ren.yale.java.controller;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

/**
 * Yale
 *
 * @create 2018-01-31 19:05
 **/
public class Index {

    public void filter(RoutingContext routingContext) {

    }

    public void index(RoutingContext routingContext) {
        Cookie someCookie = routingContext.getCookie("yale.session");
        String sessions="";
        if (someCookie!=null){
            sessions = someCookie.getValue();
        }
        Session session = routingContext.session();
        String lo = "";
        if (session.get("login")!=null){
            lo = session.get("login").toString();
        }
        routingContext.put("text","welcome!").reroute("list.html");

    }
}
