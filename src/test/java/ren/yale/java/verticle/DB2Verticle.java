package ren.yale.java.verticle;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.Suspendable;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sync.Sync;
import io.vertx.ext.sync.SyncVerticle;
import ren.yale.java.event.EventMessage;

import static io.vertx.ext.sync.Sync.awaitResult;
import static io.vertx.ext.sync.Sync.fiberHandler;

/**
 * Yale
 * create at: 2018-02-07 14:47
 **/
public class DB2Verticle extends SyncVerticle {
    private JDBCClient jdbcReader;

    private static final String ADDRESS = "ping-address";

    @Override
    public void start() throws Exception {

        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "dev/db.json"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        retriever.getConfig(jo -> {
            jdbcReader = JDBCClient.createShared(vertx, jo.result().getJsonObject("reader"), "reader");
        });

        vertx.eventBus().consumer("aaa").handler(fiberHandler(objectMessage -> {

            EventMessage eventMessage = (EventMessage) objectMessage.body();

            String bookId = eventMessage.getKey();

            try (SQLConnection conn = awaitResult(jdbcReader::getConnection)){
                ResultSet v = awaitResult(h -> conn.queryWithParams("SELECT\n" +
                        "\t\t\tbookmark_store.id,\n" +
                        "\t\t\tbookmark_store.price,\n" +
                        "\t\t\tbookmark_store.vip_free AS vipFree,\n" +
                        "\t\t\tbookmark_store.bookmark_id,\n" +
                        "\t\t\tbookmark_store.free_page_num,\n" +
                        "\t\t\tbookmark_store.question_num,\n" +
                        "\t\t\tbookmark_store.exam_question_num,\n" +
                        "\t\t\tbookmark_store.exam_rate,\n" +
                        "\t\t\tbookmark.`name` AS bookmark_name,\n" +
                        "\t\t\tbookmark.grade,\n" +
                        "\t\t\tbookmark.edition,\n" +
                        "\t\t\tbookmark.volume,\n" +
                        "\t\t\tbookmark.`subject`,\n" +
                        "\t\t\tbookmark.image,\n" +
                        "\t\t\t(SELECT edition_name FROM lftquestdb.edition_info WHERE edition_key=bookmark.edition) AS editionName\n" +
                        "\t\tFROM\n" +
                        "\t\t\tlftquestdb.bookmark_store,\n" +
                        "\t\t\tlftquestdb.bookmark\n" +
                        "\t\tWHERE\n" +
                        "\t\t\tbookmark_store.bookmark_id = bookmark.id\n" +
                        "\t\tAND bookmark_id=?",new JsonArray().add(bookId), h));

                if (v!=null){

                    if (v.getResults().size()>0){
                        objectMessage.reply(eventMessage.setMessage("ok"));
                        return;
                    }

                }

                objectMessage.reply(eventMessage.setMessage("fail"));
            } catch (Exception e) {
                e.printStackTrace();
                objectMessage.reply(eventMessage.setMessage("fail"));
            }


        }));

    }
}
