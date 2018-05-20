package vip.dummy.texasholdem.message;

import com.google.gson.Gson;

public class Message {

    private String eventName;

    public Message(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
