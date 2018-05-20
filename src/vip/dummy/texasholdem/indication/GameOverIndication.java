package vip.dummy.texasholdem.indication;

import vip.dummy.texasholdem.indication.data.GameOverData;

public class GameOverIndication extends Indication {

    private GameOverData data;

    public GameOverIndication(GameOverData data) {
        super("__game_over");
        this.data = data;
    }

    public GameOverData getData() {
        return data;
    }

    public void setData(GameOverData data) {
        this.data = data;
    }
}
