package ren.yale.java.sql;

import io.vertx.ext.jdbc.impl.actions.JDBCStatementHelper;
import junit.framework.TestCase;

/**
 * Created by yale on 2018/2/20.
 */
public class ResultSetMapperTest extends TestCase {


    public void test(){
        JDBCStatementHelper jdbcStatementHelper;
        String ret = ResultSetMapper.changeTBName("aaa");
        System.out.println(ret);
        ret = ResultSetMapper.changeTBName("aaBatBat");
        System.out.println(ret);
        ret = ResultSetMapper.changeTBName("bbDaaDyy");
        System.out.println(ret);

    }

}