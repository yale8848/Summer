package ren.yale.java.verticle;


import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.sql.SQLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ren.yale.java.event.EventMessage;
import ren.yale.java.utils.EventConst;

/**
 * Yale
 * create at: 2018-02-02 14:35
 **/
public class DBVerticle extends AbstractVerticle {
    private final static Logger LOGGER = LogManager.getLogger(DBVerticle.class.getName());
    private JDBCClient jdbcReader;

    @Override
    public void start() throws Exception {

        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "dev/db.json"));

        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore);

        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        retriever.rxGetConfig().flatMap(jo->{

            jdbcReader = JDBCClient.createShared(vertx, jo.getJsonObject("reader"), "reader");
            LOGGER.info(" connect "+jo.getJsonObject("reader").getString("url"));
            return jdbcReader.rxGetConnection();

        }).subscribe(sqlConnection -> {
            sqlConnection.close();
            LOGGER.info(" connect success");
        },throwable -> {
            LOGGER.error(throwable.getMessage());
        });

        registerEvent();
    }

    private void registerEvent() {

        vertx.eventBus().consumer(EventConst.DB_GET_BOOK_CONTENT,message -> {
            EventMessage eventMessage = (EventMessage) message.body();
            String dxh = (String) eventMessage.getObject();

            jdbcReader.rxGetConnection().flatMap(sqlConnection -> {

               Single<JsonArray> single =  sqlConnection.rxQuerySingleWithParams("SELECT\n" +
                        "\t\t\tbookmark_content.id AS id,\n" +
                        "\t\t\tbookmark_content.grade AS grade,\n" +
                        "\t\t\tbookmark_content.subject AS subject,\n" +
                        "\t\t\tbookmark_content.number AS dxh,\n" +
                        "\t\t\tbookmark_content.page_num AS pageNum,\n" +
                        "\t\t\tbookmark_content.question_num AS questionNum,\n" +
                        "\t\t\tbookmark_content.sid AS sid,\n" +
                        "\t\t\tbookmark_content.pid AS pid,\n" +
                        "\t\t\tbookmark_content.title AS title,\n" +
                        "\t\t\tbookmark_content.image AS image,\n" +
                        "\t\t\tbookmark_content.sort AS 'order',\n" +
                        "\t\t\tbookmark_content.bookmark_id AS bookId,\n" +
                        "\t\t\tbookmark.name AS bookName,\n" +
                        "\t\t\tbookmark.isshow_jhpassword AS isshow_jhpassword,\n" +
                        "\t\t\tbookmark_content.sign AS sign,\n" +
                        "\t\t\tbookmark_content.type AS type\n" +
                        "\t\tFROM\n" +
                        "\t\t\tlftquestdb.bookmark_content,lftquestdb.bookmark\n" +
                        "\t\tWHERE\n" +
                        "\t\t\tlftquestdb.bookmark_content.bookmark_id=lftquestdb.bookmark.id\n" +
                        "\t\t\tAND bookmark_content.number = ?\n" +
                        "\t\t\tAND bookmark_content.sid IS NOT NULL",new JsonArray().add(dxh));

               return single.doAfterTerminate(sqlConnection::close);

            }).subscribe(jsonArray -> {

                eventMessage.setObject(jsonArray).setSuccess(true);
                message.reply(eventMessage);
            },throwable -> {
                LOGGER.error(throwable.getMessage());
                eventMessage.setSuccess(false).setMessage("db get error");
                message.reply(eventMessage);
            });



        });


    }
}
