package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.NewRoundData;

public class NewRoundIndication extends Indication {

    private NewRoundData data;

    public NewRoundIndication(NewRoundData data) {
        super("__new_round");
        this.data = data;
    }

    public NewRoundData getData() {
        return data;
    }

    public void setData(NewRoundData data) {
        this.data = data;
    }
}
