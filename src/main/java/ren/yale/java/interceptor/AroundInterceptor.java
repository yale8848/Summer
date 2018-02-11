package ren.yale.java.interceptor;

import io.vertx.ext.web.RoutingContext;

/**
 * Yale
 *
 * create at:  2018-02-01 15:59
 **/
public interface AroundInterceptor {
    boolean before(RoutingContext routingContext);
    boolean after(RoutingContext routingContext);
}
