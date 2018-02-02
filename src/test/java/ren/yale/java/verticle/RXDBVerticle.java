package ren.yale.java.verticle;

import io.reactivex.Single;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.core.AbstractVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



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
}
