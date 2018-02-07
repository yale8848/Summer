package ren.yale.java;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import ren.yale.java.controller.DXHResource;
import ren.yale.java.event.EventMessage;
import ren.yale.java.service.TestService;
import ren.yale.java.verticle.DB2Verticle;

import static io.vertx.ext.sync.Sync.awaitResult;
import static io.vertx.ext.sync.Sync.fiberHandler;

/**
 * Yale
 * create at: 2018-02-02 14:25
 **/
public class TestMain {

    public static void main(String args[]){
        SummerServer summerServer =SummerServer.create(8080);
        DeploymentOptions deploymentOptions = new DeploymentOptions();

        deploymentOptions.setWorker(true);
        //summerServer.getSummerRouter().registerResource(DXHResource.class);
        //summerServer.getVertx().deployVerticle(RedisVerticle.class.getName(),deploymentOptions);
        summerServer.getVertx().deployVerticle(DB2Verticle.class.getName());

        summerServer.getVertx().
                deployVerticle(WebServer.class.getName());
        //summerServer.start();
    }

    public static class WebServer extends SyncVerticle {

        TestService testService = new TestService();
        @Override
        public void start() throws Exception {

            Router router = Router.router(vertx);
            router.route("/aaa").handler(fiberHandler(routingContext -> {


                testService.test(vertx,routingContext);

            }));
            vertx.createHttpServer()
                    .requestHandler((router::accept))
                    .listen(8080,"localhost",httpServerAsyncResult -> {
                        if (httpServerAsyncResult.succeeded()){
                            System.out.println("listen at: http://8080");
                        }else{
                            System.out.println(httpServerAsyncResult.cause().getCause());
                        }
                    });

        }
    }
}
