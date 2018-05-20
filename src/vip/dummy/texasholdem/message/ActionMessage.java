package vip.dummy.texasholdem.message;

import vip.dummy.texasholdem.message.data.ActionData;

public class ActionMessage extends Message {

    private ActionData data;

    public ActionMessage(ActionData data) {
        super("__action");
        this.data = data;
    }

    public ActionData getData() {
        return data;
    }

    public void setData(ActionData data) {
        this.data = data;
    }
}
