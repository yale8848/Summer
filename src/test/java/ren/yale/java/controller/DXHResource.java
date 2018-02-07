package ren.yale.java.controller;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import ren.yale.java.event.EventMessage;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import static io.vertx.ext.sync.Sync.awaitResult;

/**
 * Yale
 * create at: 2018-02-05 14:06
 **/
@Path("/api")
public class DXHResource {


    @Path("/dxh/{dxhv}")
    @GET
    public void dxh(@PathParam("dxhv") String dxh, @Context Vertx vertx,
                    @Context HttpServerResponse response){
        dxh="8454005";
        //WebClient client = WebClient.create(vertx);
        //HttpUtils.oneDXH(client,response,dxh,vertx);
        //response.end(dxh);


  /*      try {
            EventBus eb = vertx.eventBus();
            EventMessage eventMessage=EventMessage.message(null);
            eventMessage.setKey("68257");
            Message<EventMessage> reply = awaitResult(h -> eb.send("aaa", eventMessage, h));

            //Message<EventMessage> reply1 = awaitResult(h -> eb.send("aaa", eventMessage, h));
            response.end(reply.body().getMessage());
        }catch (Exception e){
            response.end(e.getMessage());
        }*/

        EventBus eb = vertx.eventBus();
        EventMessage eventMessage=EventMessage.message(null);
        eventMessage.setKey("68257");
        Message<EventMessage> reply = awaitResult(h -> eb.send("aaa", eventMessage, h));

        Message<EventMessage> reply1 = awaitResult(h -> eb.send("aaa", eventMessage, h));
        response.end(reply.body().getMessage());


    }

}
