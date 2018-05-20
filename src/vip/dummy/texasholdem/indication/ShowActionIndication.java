package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.ShowActionData;

public class ShowActionIndication extends Indication {

    private ShowActionData data;

    public ShowActionIndication(ShowActionData data) {
        super("__show_action");
        this.data = data;
    }

    public ShowActionData getData() {
        return data;
    }

    public void setData(ShowActionData data) {
        this.data = data;
    }
}
