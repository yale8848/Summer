package ren.yale.java.sql;


import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.impl.actions.JDBCStatementHelper;
import io.vertx.ext.sql.ResultSet;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yale on 2018/2/19.
 */
public class ResultSetMapper {
    private MapperNameType mapperNameType = MapperNameType.DEFAULT;

    public static ResultSetMapper create(){
        return new ResultSetMapper();
    }

    public ResultSetMapper camelName(){
        mapperNameType = MapperNameType.CAMEL;
        return this;
    }

    private String camelName(String name){
        Pattern pattern = Pattern.compile("([A-Z])");

        Matcher matcher = pattern.matcher(name);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            matcher.appendReplacement(sb,"_"+matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private  String changeTBName(String name){
        if (mapperNameType == MapperNameType.CAMEL){
            return camelName(name);
        }
        return name;

    }


    private  <T> T mapperOne(List<String> columnNames,JsonArray jsonArray,Class<T> t){

        GetResultValue getResultValue = GetResultValue.create(columnNames,
                jsonArray);
        Field[] fields = t.getDeclaredFields();

        try {
            T clazz = t.newInstance();

            for (Field f:fields) {
                Class type = f.getType();
                f.setAccessible(true);
                String name = changeTBName(f.getName());
                if (type == String.class){
                    f.set(clazz,getResultValue.getStrValue(name));
                }else
                if (type == Integer.class){
                    f.set(clazz,getResultValue.getIntValue(name));
                }else
                if (type == Long.class){
                    f.set(clazz,getResultValue.getLongValue(name));
                }else
                if (type == Float.class){
                    f.set(clazz,getResultValue.getFloatValue(name));
                }else
                if (type == Double.class){
                    f.set(clazz,getResultValue.getDoubleValue(name));
                }else
                if (type == Time.class){
                    JDBCStatementHelper jdbcStatementHelper = new JDBCStatementHelper();
                    Object obj = jdbcStatementHelper.optimisticCast(getResultValue.getStrValue(name));
                    if (obj!=null&&obj.getClass() == Time.class){
                        f.set(clazz,obj);
                    }
                }else
                if (type == Date.class){
                    JDBCStatementHelper jdbcStatementHelper = new JDBCStatementHelper();
                    Object obj = jdbcStatementHelper.optimisticCast(getResultValue.getStrValue(name));
                    if (obj!=null&&obj.getClass() == Date.class){
                        f.set(clazz,obj);
                    }
                }else
                if (type == Timestamp.class){
                    JDBCStatementHelper jdbcStatementHelper = new JDBCStatementHelper();
                    Object obj = jdbcStatementHelper.optimisticCast(getResultValue.getStrValue(name));
                    if (obj!=null&&obj.getClass() == Timestamp.class){
                        f.set(clazz,obj);
                    }
                }
            }

            return clazz;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  <T> T mapper(ResultSet resultSet,Class<T> t){
        if (resultSet==null||resultSet.getResults()==null||
                resultSet.getResults().size()==0||resultSet.getColumnNames()==null
                ){
            return null;
        }
        return mapperOne(resultSet.getColumnNames(),resultSet.getResults().get(0),t);
    }

    public  <T> List<T> mapperList(ResultSet resultSet,Class<T> t){
        if (resultSet==null||resultSet.getResults()==null||
                resultSet.getResults().size()==0||resultSet.getColumnNames()==null
                ){
            return null;
        }
        List<T> list = new ArrayList<>();
        for (JsonArray ja:resultSet.getResults()) {
            T mt = mapperOne(resultSet.getColumnNames(),ja,t);
            list.add(mt);
        }

        return list;

    }
    private static enum MapperNameType{
        DEFAULT,CAMEL
    }
    private static class GetResultValue{
        List<String> columnNames;
        JsonArray jsonArray;

        public GetResultValue(List<String> columnNames,JsonArray jsonArray){
            this.columnNames = columnNames;
            this.jsonArray = jsonArray;

        }
        public static GetResultValue create(List<String> columnNames,JsonArray jsonArray){

            return new GetResultValue(columnNames,jsonArray);

        }
        private Float getFloatValue(String key){
            int pos =  columnNames.indexOf(key);
            if (pos!=-1){
                return jsonArray.getFloat(pos);
            }
            return null;
        }
        private Long getLongValue(String key){
            int pos =  columnNames.indexOf(key);
            if (pos!=-1){
                return jsonArray.getLong(pos);
            }
            return null;
        }
        private  String getStrValue(String key){
            int pos =  columnNames.indexOf(key);
            if (pos!=-1){
                return jsonArray.getString(pos);
            }
            return "";
        }
        private  Integer getIntValue(String key){
            int pos =  columnNames.indexOf(key);
            if (pos!=-1){
                return jsonArray.getInteger(pos);
            }
            return null;
        }

        private  Double getDoubleValue(String key){
            int pos =  columnNames.indexOf(key);
            if (pos!=-1){
                return jsonArray.getDouble(pos);
            }
            return null;
        }

    }
}
