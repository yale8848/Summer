package ren.yale.java;

import io.vertx.core.DeploymentOptions;
import ren.yale.java.controller.DXHResource;
import ren.yale.java.controller.Hello;
import ren.yale.java.event.EventMessage;
import ren.yale.java.event.EventMessageCodec;
import ren.yale.java.verticle.RXDBVerticle;
import ren.yale.java.verticle.RedisVerticle;

/**
 * Yale
 * create at: 2018-02-02 14:25
 **/
public class TestMain {

    public static void main(String args[]){
        SummerServer summerServer =SummerServer.create(8080);
        DeploymentOptions deploymentOptions = new DeploymentOptions();

        deploymentOptions.setWorker(true);
        summerServer.getSummerRouter().registerResource(DXHResource.class);
        summerServer.getVertx().deployVerticle(RedisVerticle.class.getName(),deploymentOptions);
        summerServer.start(deploymentOptions);
    }
}
