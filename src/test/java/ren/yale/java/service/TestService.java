package ren.yale.java.service;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;
import ren.yale.java.SummerResponse;
import ren.yale.java.event.EventMessage;


/**
 * Yale
 * create at: 2018-02-07 18:47
 **/
public class TestService {


    public SummerResponse test(Vertx vertx,RoutingContext routingContext){

        EventBus eb = vertx.eventBus();
        EventMessage eventMessage=EventMessage.message(null);
        eventMessage.setKey("68257");
       // Message<EventMessage> reply = awaitResult(h -> eb.send("aaa", eventMessage, h));

       // Message<EventMessage> reply1 = awaitResult(h -> eb.send("aaa", eventMessage, h));

       // routingContext.response().end(SummerResponse.ok().setResult(reply.body().getMessage()).jsonPretty());

        return SummerResponse.fail().setMessage("bbb");
    }
}
