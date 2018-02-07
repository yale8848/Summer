package ren.yale.java;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ren.yale.java.event.EventMessage;
import ren.yale.java.event.EventMessageCodec;

import static io.vertx.ext.sync.Sync.fiberHandler;

/**
 * Yale
 *
 * create at:  2018-02-01 16:40
 **/
public class SummerServer  {


    private final static Logger LOGGER = LogManager.getLogger(SummerServer.class.getName());

    private static Vertx vertx;
    private static Router router;
    private static SummerRouter summerRouter;
    private static int port = 8080;
    private static String host="localhost";


    private SummerServer(String host,int port){
        this(host,port,null);
    }

    private SummerServer(String host,int port,VertxOptions options){
        if (options!=null){
            this.vertx = Vertx.vertx(options);
        }else{
            this.vertx = Vertx.vertx();
        }
        this.router = Router.router(vertx);
        this.summerRouter = new SummerRouter(router,vertx);
        this.port=port;
        this.host = host;
        init();
    }

    private void init(){
        vertx.eventBus().registerDefaultCodec(EventMessage.class, new EventMessageCodec());
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
    public static SummerServer create(String host,int port,VertxOptions options){
        return new SummerServer(host,port,options);
    }

    public void start() {
        vertx.deployVerticle(WebServer.class.getName());
    }
    public void start( DeploymentOptions options) {

        vertx.deployVerticle(WebServer.class.getName(),options);
    }

    public static class WebServer extends SyncVerticle{


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
