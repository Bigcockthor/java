package vip.dummy.texasholdem.indication.data;

import vip.dummy.texasholdem.bean.*;

public class ShowActionData {

    private Action action;
    private Table table;
    private Player []players;

    public ShowActionData(Action action, Table table, Player[] players) {
        this.action = action;
        this.table = table;
        this.players = players;
    }

    public ShowActionData() {

    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
