package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.ActionData;

public class ActionIndication extends Indication {

    private ActionData data;

    public ActionIndication(ActionData data) {
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
