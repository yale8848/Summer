package ren.yale.java;

import ren.yale.java.controller.Hello;
import ren.yale.java.verticle.RXDBVerticle;

/**
 * Yale
 * create at: 2018-02-02 14:25
 **/
public class TestMain {

    public static void main(String args[]){
        SummerServer summerServer =SummerServer.create(8080);

        summerServer.getSummerRouter().registerResource(Hello.class);
        summerServer.getVertx().deployVerticle(RXDBVerticle.class.getName());
        summerServer.start();
    }
}
