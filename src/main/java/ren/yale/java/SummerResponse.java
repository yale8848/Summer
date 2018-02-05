package ren.yale.java;

import io.vertx.core.json.JsonObject;

import javax.ws.rs.core.Response;

/**
 * Yale
 * create at: 2018-02-05 16:07
 **/
public class SummerResponse  {

    private int code = 0;
    private String message;
    private boolean success;
    private Object result;

    public int getCode() {
        return code;
    }

    public SummerResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SummerResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public SummerResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public SummerResponse setResult(Object result) {
        this.result = result;
        return this;
    }
    public String json(){
        return JsonObject.mapFrom(this).encode();
    }
    public String jsonPretty(){
        return JsonObject.mapFrom(this).encodePrettily();
    }

    public static SummerResponse ok(){

        SummerResponse summerResponse = new SummerResponse();
        summerResponse.setSuccess(true);
        summerResponse.setResult(null);
        return summerResponse;
    }
    public static SummerResponse ok(Object result){

        SummerResponse summerResponse = new SummerResponse();

        summerResponse.setSuccess(true);
        if (result!=null){
            summerResponse.setResult(result);
        }
        return summerResponse;
    }
    public static SummerResponse fail(String message){

        SummerResponse summerResponse = new SummerResponse();

        summerResponse.setSuccess(false);
        if (message!=null){
            summerResponse.setMessage(message);
        }
        return summerResponse;
    }
    public static SummerResponse fail(){

        SummerResponse summerResponse = new SummerResponse();
        summerResponse.setSuccess(false);
        summerResponse.setMessage(null);
        return summerResponse;
    }
}
