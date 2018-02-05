package ren.yale.java.utils;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.client.HttpRequest;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import ren.yale.java.SummerResponse;
import ren.yale.java.bean.BookIndexBook;
import ren.yale.java.event.EventMessage;

/**
 * Yale
 * create at: 2018-02-05 17:15
 **/
public class HttpUtils {

    public static void oneDXH(WebClient client,HttpServerResponse response,String dxh,Vertx vertx){
        vertx.eventBus().rxSend(EventConst.REDIS_GET, EventMessage.message("dxhbookinfo_"+dxh))
                .subscribe(objectMessage -> {
                    EventMessage eventMessage = (EventMessage) objectMessage.body();
                    if (eventMessage.isSuccess()){

                        if (eventMessage.getObject() == null){

                        }else{

                        }


                    }else{
                        response.end();
                    }


                });
    }

    public static void oneBook(WebClient client,HttpServerResponse response,String dxh,Vertx vertx){
        client.get(80,"dxhslb.daoxuehao.com","/LFT-GuidanceLearn/quest/v2.0/openQuestion")
                .putHeader("call-type","android")
                .putHeader("versionCode","609")
                .putHeader("versionName","4.21.2.0")
                .putHeader("signature","5605f82cc18b536993f721915f50b6f5")
                .putHeader("token","1aaab3317f1bc3a4d7e81aa7d89ade55")
                .putHeader("openId","ycEHHohEMhk=")
                .addQueryParam("dxh",dxh)
                .as(BodyCodec.json(BookIndexBook.class))
                .rxSend().subscribe(book -> {
            BookIndexBook b = book.body();
            if (b.getDxhMode() == 2&&b.isSuccess()){
                response.end(SummerResponse.ok(b).jsonPretty());
            }else{





            }
        },throwable -> {
            response.end(SummerResponse.fail().jsonPretty());
        });
    }
}
