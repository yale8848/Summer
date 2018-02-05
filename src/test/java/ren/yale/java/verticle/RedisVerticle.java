package ren.yale.java.verticle;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import ren.yale.java.event.EventMessage;
import ren.yale.java.utils.EventConst;

/**
 * Yale
 * create at: 2018-02-05 17:42
 **/
public class RedisVerticle extends AbstractVerticle {

    RedisClient redis;
    @Override
    public void start() throws Exception {

        RedisOptions config = new RedisOptions()
                .setHost("121.42.232.41")
                .setAuth("")
                .setSelect(11);

        redis = RedisClient.create(vertx, config);

        registerEvent();


    }

    private void registerEvent() {

        registerGet();
        registerSet();


    }

    private void registerGet() {
        vertx.eventBus().consumer(EventConst.REDIS_GET,message -> {
            EventMessage eventMessage = (EventMessage) message.body();
            String key = (String) eventMessage.getObject();
            redis.rxGet(key).subscribe(s -> {
                eventMessage.setSuccess(true).setObject(s);
                message.reply(eventMessage);
            },throwable -> {
                message.reply(eventMessage.setSuccess(false).setMessage("redis get error"));
            });
        });
    }

    private void registerSet() {

        vertx.eventBus().consumer(EventConst.REDIS_GET,message -> {

        });
    }
}
