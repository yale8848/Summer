package ren.yale.java.controller;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import ren.yale.java.SummerResponse;
import ren.yale.java.bean.BookIndexBook;
import ren.yale.java.utils.HttpUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

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
        WebClient client = WebClient.create(vertx);
        HttpUtils.oneDXH(client,response,dxh,vertx);

    }

}
