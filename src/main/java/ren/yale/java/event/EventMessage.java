package ren.yale.java.event;

/**
 * Yale
 *
 **/
public class EventMessage {
    private boolean success = false;
    private String message ="";
    private String key="";

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

    public EventMessage setSuccess(boolean success) {
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

    public EventMessage setObject(Object object) {
        this.object = object;
        return this;
    }

    public static EventMessage message(Object o){
        EventMessage eventMessage = new EventMessage();
        eventMessage.setObject(o);
        return eventMessage;
    }
}
