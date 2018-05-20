package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.BetData;

public class BetIndication extends Indication {

    private BetData data;

    public BetIndication(BetData data) {
        super("__bet");
        this.data = data;
    }

    public BetData getData() {
        return data;
    }

    public void setData(BetData data) {
        this.data = data;
    }
}
