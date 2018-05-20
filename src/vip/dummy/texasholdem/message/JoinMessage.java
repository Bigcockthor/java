package vip.dummy.texasholdem.message;

import vip.dummy.texasholdem.message.data.JoinData;

public class JoinMessage extends Message {

    private JoinData data;

    public JoinMessage(JoinData joinData) {
        super("__join");
        this.data = joinData;
    }

    public JoinData getData() {
        return data;
    }

    public void setData(JoinData data) {
        this.data = data;
    }
}
