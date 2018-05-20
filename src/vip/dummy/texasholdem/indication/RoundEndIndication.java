package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.RoundEndData;

public class RoundEndIndication extends Indication {

    private RoundEndData data;

    public RoundEndIndication(RoundEndData data) {
        super("__round_end");
        this.data = data;
    }

    public RoundEndData getData() {
        return data;
    }

    public void setData(RoundEndData data) {
        this.data = data;
    }
}
