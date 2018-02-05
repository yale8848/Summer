package ren.yale.java.verticle;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.core.AbstractVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ren.yale.java.bean.UserInfo;
import ren.yale.java.event.EventMessage;


/**
 * Yale
 * create at: 2018-02-02 16:06
 **/
public class RXDBVerticle extends AbstractVerticle {
    private final static Logger LOGGER = LogManager.getLogger(RXDBVerticle.class.getName());
    private JDBCClient jdbcReader;
    private JDBCClient jdbcWriter;

    @Override
    public void start() throws Exception {
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "db.json"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore);

        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        retriever.rxGetConfig().subscribe(jsonObject -> {
            JsonObject jo = jsonObject.getJsonObject("local").getJsonObject("reader");
            jdbcReader = JDBCClient.createShared(vertx, jo, "dbreader");
            jo = jsonObject.getJsonObject("local").getJsonObject("writer");
            jdbcWriter = JDBCClient.createShared(vertx, jo, "dbwriter");
            jdbcReader.rxGetConnection().flatMap(sqlConnection -> {
                sqlConnection.close();
                return jdbcWriter.rxGetConnection();
            }).subscribe(sqlConnection -> {
                sqlConnection.close();
                LOGGER.info("db connect success");
            },throwable -> {
                LOGGER.error(throwable.getMessage());
            });

        },throwable -> {

        });
    }
    private void registerEvent(){

        vertx.eventBus().consumer("login").toFlowable().subscribe(objectMessage -> {

            EventMessage ev = (EventMessage) objectMessage.body();
            UserInfo u = (UserInfo) ev.getObject();
            jdbcReader.rxGetConnection().flatMap(sqlConnection -> {
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(u.getName()).add(u.getPassword());
                Single<JsonArray> s= sqlConnection.rxQuerySingleWithParams("SELECT COUNT(*) FROM tb_user where name = ? AND password=?",jsonArray);
                return s.doAfterTerminate(sqlConnection::close);
            }).subscribe(jsonArray -> {
                int rows = jsonArray.getInteger(0);
                if (rows==0){
                    objectMessage.reply(ev.setMessage("not find"));
                }else{
                    objectMessage.reply(ev.setSuccess(true));
                }
            },throwable -> {
                objectMessage.reply(ev.setMessage("server error"));
            });
        });
        vertx.eventBus().consumer("register").toFlowable().subscribe(objectMessage -> {

            EventMessage ev = (EventMessage) objectMessage.body();
            UserInfo u = (UserInfo) ev.getObject();
            jdbcReader.rxGetConnection().flatMap(sqlConnection -> {
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(u.getName());
                Single<UpdateResult> s= sqlConnection.rxQuerySingleWithParams("SELECT COUNT(*) FROM tb_user where name = ?",jsonArray)
                        .flatMap(j -> {
                            int rows = j.getInteger(0);
                            if (rows==0){
                                jsonArray.add(u.getPassword());
                               return sqlConnection.rxUpdateWithParams("INSERT INTO tb_user (name,password) VALUES (?,?)",jsonArray);
                            }

                            return  Single.create(observableEmitter->{
                                UpdateResult updateResult = new UpdateResult();
                                   observableEmitter.onSuccess(updateResult.setUpdated(-1));
                            });

                        });
                return s.doAfterTerminate(sqlConnection::close);
            }).subscribe(jsonArray -> {
            },throwable -> {
                objectMessage.reply(ev.setMessage("server error"));
            });
        });


    }

}
