package vip.dummy.texasholdem.indication.data;

import vip.dummy.texasholdem.bean.Player;

public class StartReloadData {

    private String tableNumber;
    private Player []players;

    public StartReloadData(String tableNumber, Player[] players) {
        this.tableNumber = tableNumber;
        this.players = players;
    }

    public StartReloadData() {

    }

    public StartReloadData(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
