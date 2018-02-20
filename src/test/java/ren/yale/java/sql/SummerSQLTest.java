package ren.yale.java.sql;

import junit.framework.TestCase;

/**
 * Created by yale on 2018/2/17.
 */
public class SummerSQLTest extends TestCase {


    public void test(){

       String sql = new SummerSQL() {{
            DELETE_FROM("PERSON");
            WHERE("ID = #{id}");
        }}.toString();

        SummerSQL summerSQL = new SummerSQL().SELECT("catalog AS pageLabel")
                .FROM("lftquestdb.bookmark_content")
                .WHERE("bookmark_id = ?")
                .AND()
                .WHERE("number IS NOT NULL")
                .AND()
                .WHERE("sign = 0")
                .GROUP_BY("catalog")
                .ORDER_BY("sort DESC");

       System.out.println(summerSQL.toString());
    }
}