package vip.dummy.texasholdem.message;

import vip.dummy.texasholdem.message.data.ReloadData;

public class ReloadMessage extends Message {

    private ReloadData data;

    public ReloadMessage(ReloadData data) {
        super("__reload");
        this.data = data;
    }

    public ReloadMessage(String eventName) {
        super(eventName);
    }

    public ReloadData getData() {
        return data;
    }

    public void setData(ReloadData data) {
        this.data = data;
    }
}
