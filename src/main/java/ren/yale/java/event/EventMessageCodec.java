package ren.yale.java.event;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Yale
 *
 * 2018-01-29 17:08
 **/
public class EventMessageCodec implements MessageCodec<EventMessage,EventMessage> {

    @Override
    public void encodeToWire(Buffer buffer, EventMessage eventMessage) {

        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream ob = new ObjectOutputStream(b);
            ob.writeObject(eventMessage);
            buffer.appendBytes(b.toByteArray());
        }catch (Exception e){

        }


    }

    @Override
    public EventMessage decodeFromWire(int i, Buffer buffer) {
        EventMessage eventMessage = null;
        try {
            ByteArrayInputStream b = new ByteArrayInputStream(buffer.getBytes());
            ObjectInputStream oi = new ObjectInputStream(b);
            eventMessage = (EventMessage) oi.readObject();
        }catch (Exception e){

        }

        return eventMessage;
    }

    @Override
    public EventMessage transform(EventMessage eventMessage) {
        return eventMessage;
    }

    @Override
    public String name() {
        return "EventMessage";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
