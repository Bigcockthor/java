package vip.dummy.texasholdem.indication.data;

import vip.dummy.texasholdem.bean.Game;
import vip.dummy.texasholdem.bean.Self;

public class ActionData {

    private String tableNumber;
    private Self self;
    private Game game;

    public ActionData(String tableNumber, Self self, Game game) {
        this.tableNumber = tableNumber;
        this.self = self;
        this.game = game;
    }

    public ActionData() {

    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
