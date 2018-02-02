package ren.yale.java.interceptor;


import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Created by yale on 2018/2/1.
 */
public interface Interceptor {
    boolean handle(RoutingContext routingContext);
}
