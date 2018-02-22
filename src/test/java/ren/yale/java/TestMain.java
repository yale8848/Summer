package ren.yale.java;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import ren.yale.java.controller.DXHResource;
import ren.yale.java.controller.Hello;
import ren.yale.java.verticle.RXDBVerticle;

/**
 * Yale
 * create at: 2018-02-02 14:25
 **/
public class TestMain {

    public static void main(String args[]){

        VertxOptions options = new VertxOptions();
        options.setBlockedThreadCheckInterval(20000);
        options.setMaxEventLoopExecuteTime(20000);
        SummerServer summerServer =SummerServer.create("localhost",8080,options);

        DeploymentOptions deploymentOptions = new DeploymentOptions();

        deploymentOptions.setWorker(true);
        summerServer.getSummerRouter().registerResource(Hello.class);
         summerServer.getVertx().deployVerticle(RXDBVerticle.class.getName());

        summerServer.getVertx().
                deployVerticle(SummerServer.WebServer.class.getName());
        summerServer.start();
    }


}
