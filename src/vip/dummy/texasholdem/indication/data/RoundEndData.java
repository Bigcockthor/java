package vip.dummy.texasholdem.indication.data;

import vip.dummy.texasholdem.bean.Player;
import vip.dummy.texasholdem.bean.Table;

public class RoundEndData {

    private Table table;
    private Player []players;

    public RoundEndData(Table table, Player[] players) {
        this.table = table;
        this.players = players;
    }

    public RoundEndData() {

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
