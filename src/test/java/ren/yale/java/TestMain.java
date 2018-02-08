package ren.yale.java;

import io.vertx.core.DeploymentOptions;

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
       // summerServer.getVertx().deployVerticle(DB2Verticle.class.getName());

        //summerServer.getVertx().
       //         deployVerticle(WebServer.class.getName());
        //summerServer.start();
    }


}
