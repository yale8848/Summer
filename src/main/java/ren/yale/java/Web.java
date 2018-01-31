package ren.yale.java;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import ren.yale.java.controller.Index;

/**
 * Yale
 *
 * @create 2018-01-31 19:04
 **/
public class Web extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        SessionHandler handler = SessionHandler.create(LocalSessionStore.create(vertx,"yale.session",100000));
        handler.setSessionCookieName("yale.session");
        handler.setNagHttps(true);
        router.route().handler(handler);

        Index indexHandler = new Index();
        router.route().handler(BodyHandler.create());
        router.route().handler(indexHandler::filter);
        router.get("/").handler(indexHandler::index);
        router.post("/user/login").handler(indexHandler::filter);
        router.post("/user/register").handler(indexHandler::filter);

        Handler<RoutingContext> var1 = new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {

            }
        };

        router.route("/static/*").handler(var1);
    }
}
