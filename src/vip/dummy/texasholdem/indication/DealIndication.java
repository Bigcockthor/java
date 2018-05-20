package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.DealData;

public class DealIndication extends Indication {

    private  DealData data;

    public DealIndication(DealData data) {
        super("__deal");
        this.data = data;
    }

    public DealData getData() {
        return data;
    }

    public void setData(DealData data) {
        this.data = data;
    }
}
