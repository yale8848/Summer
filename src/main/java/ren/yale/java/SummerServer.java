package ren.yale.java;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

/**
 * Yale
 *
 * create at:  2018-02-01 16:40
 **/
public class SummerServer  {
    private static Vertx vertx;
    private static Router router;
    private static SummerRouter summerRouter;
    private static int port = 8080;
    private static String host="localhost";

    private SummerServer(String host,int port){
        this.vertx = Vertx.vertx();
        this.router = Router.router(vertx);
        this.summerRouter = new SummerRouter(router,vertx);
        this.port=port;
        this.host = host;
    }
    public Vertx getVertx(){
        return vertx;
    }

    public Router getRouter(){
        return router;
    }

    public SummerRouter getSummerRouter(){
        return summerRouter;
    }
    public static SummerServer create(int port){
        return new SummerServer(host,port);
    }
    public static SummerServer create(){
        return new SummerServer(host,port);
    }
    public static SummerServer create(String host,int port){
        return new SummerServer(host,port);
    }

    public void start() {
        vertx.deployVerticle(WebServer.class.getName());
    }
    public void start( DeploymentOptions options) {
        vertx.deployVerticle(WebServer.class.getName(),options);
    }

    public static class WebServer extends AbstractVerticle{
        @Override
        public void start() throws Exception {


            vertx.createHttpServer()
                    .requestHandler(router::accept)
                    .listen(port,host,httpServerAsyncResult -> {
                        if (httpServerAsyncResult.succeeded()){
                            System.out.println("listen at: http://"+host+":"+port);
                        }else{
                            System.out.println(httpServerAsyncResult.cause().getCause());
                        }
                    });

        }
    }
}
