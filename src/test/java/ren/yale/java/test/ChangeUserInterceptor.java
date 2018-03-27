package ren.yale.java.test;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import ren.yale.java.bean.User;
import ren.yale.java.interceptor.Interceptor;

import javax.ws.rs.core.MediaType;

/**
 * Yale
 *
 * create at:  2018-02-01 17:24
 **/
public class ChangeUserInterceptor implements Interceptor {
    @Override
    public boolean handle(RoutingContext routingContext,Object obj) {
        User user = (User) obj;
        user.setName("Alice");
        routingContext.response()
                .end(JsonObject.mapFrom(user).encodePrettily());
        return true;
    }
}
