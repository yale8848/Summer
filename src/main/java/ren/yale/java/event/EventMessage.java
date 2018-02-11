package ren.yale.java.event;

import java.util.ArrayList;

/**
 * Yale
 *
 **/
public class EventMessage {
    private boolean success = false;
    private String message ="";
    private String key="";
    private String key1="";
    private String key2="";

    private ArrayList<String>  strings;
    private ArrayList<Integer>  ints;
    private ArrayList<Long> longs;
    private ArrayList<Float> floats;
    private ArrayList<Double> doubles;


    public EventMessage addStr(String v){
        if (strings==null){
            strings = new ArrayList<String>();
        }
        strings.add(v);
        return this;
    }

    public EventMessage addInt(Integer v){
        if (ints==null){
            ints = new ArrayList<Integer>();
        }
        ints.add(v);

        return this;
    }

    public EventMessage addLong(Long v){
        if (longs==null){
            longs = new ArrayList<Long>();
        }
        longs.add(v);
        return this;
    }

    public EventMessage addFloat(Float v){
        if (floats==null){
            floats = new ArrayList<Float>();
        }
        floats.add(v);
        return this;
    }

    public EventMessage addDouble(Double v){
        if (doubles==null){
            doubles = new ArrayList<Double>();
        }
        doubles.add(v);
        return this;
    }

    public Integer getInteger(){
        return getInteger(0);
    }

    public Integer getInteger(int pos){
        if (ints==null||ints.size()<=pos){
            return null;
        }
        return ints.get(pos);
    }
    public Long getLong(){

        return getLong(0);
    }
    public Long getLong(int pos){
        if (longs==null||longs.size()<=pos){
            return null;
        }
        return longs.get(pos);
    }
    public Float getFloat(){

        return getFloat(0);
    }
    public Float getFloat(int pos){
        if (floats==null||floats.size()<=pos){
            return null;
        }
        return floats.get(pos);
    }
    public Double getDouble(){
        return getDouble(0);
    }
    public Double getDouble(int pos){
        if (doubles==null||doubles.size()<=pos){
            return null;
        }
        return doubles.get(pos);
    }

    public String getKey1() {
        return key1;
    }

    public EventMessage setKey1(String key1) {
        this.key1 = key1;
        return this;
    }

    public String getKey2() {
        return key2;
    }

    public EventMessage setKey2(String key2) {
        this.key2 = key2;
        return this;
    }

    public String getKey() {
        return key;
    }

    public EventMessage setKey(String key) {
        this.key = key;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public EventMessage success(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EventMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    private Object object;

    public Object getObject() {
        return object;
    }

    public EventMessage object(Object object) {
        this.object = object;
        return this;
    }

    public static EventMessage message(Object o){
        EventMessage eventMessage = new EventMessage();
        if (o!=null){
            eventMessage.object(o);
        }
        return eventMessage;
    }
    public static EventMessage message(){
        return message(null);
    }
}
