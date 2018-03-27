package ren.yale.java.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ren.yale.java.bean.DBTest;
import ren.yale.java.event.EventMessage;
import ren.yale.java.sql.ResultSetMapper;
import ren.yale.java.sql.SummerSQL;

import java.util.List;


/**
 * Yale
 * create at: 2018-02-02 16:06
 **/
public class MyVerticle extends AbstractVerticle {
    private final static Logger LOGGER = LogManager.getLogger(MyVerticle.class.getName());
    private JDBCClient jdbcReader;

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("user",message -> {
            EventMessage eventMessage = (EventMessage) message.body();
            eventMessage.success(true);
            eventMessage.setMessage("Hello "+eventMessage.getObject());
            message.reply(eventMessage);
        });

        vertx.fileSystem().readFile("dev/dbt.json",bufferAsyncResult -> {
            if (bufferAsyncResult.succeeded()){
                JsonObject jsonObject = bufferAsyncResult.result().toJsonObject().getJsonObject("reader");

                jdbcReader = JDBCClient.createShared(vertx, jsonObject, "reader");

                jdbcReader.getConnection(sqlConnectionAsyncResult -> {
                    if (sqlConnectionAsyncResult.succeeded()){
                        LOGGER.info("+++++ db success");
                       SQLConnection sqlConnection =  sqlConnectionAsyncResult.result();

                        sqlConnection.query(new SummerSQL().SELECT("*")
                                .FROM("db_test.tb_test").toString(), resultSetAsyncResult -> {
                            if (resultSetAsyncResult.succeeded()){

                               List<DBTest> tests =  ResultSetMapper.create().camelName()
                                        .mapperList(resultSetAsyncResult.result(), DBTest.class);
                            }
                            sqlConnection.close();
                        });
                    }else{

                    }

                });




            }



        });
    }

}
