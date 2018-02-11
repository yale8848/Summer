package ren.yale.java.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
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
                .setConfig(new JsonObject().put("path", "dev/db.json"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore);

        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
    }

}
