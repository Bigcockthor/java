package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.StartReloadData;

public class StartReloadIndication extends Indication {

    private StartReloadData data;

    public StartReloadIndication(StartReloadData data) {
        super("__start_reload");
        this.data = data;
    }

    public StartReloadData getData() {
        return data;
    }

    public void setData(StartReloadData data) {
        this.data = data;
    }
}
