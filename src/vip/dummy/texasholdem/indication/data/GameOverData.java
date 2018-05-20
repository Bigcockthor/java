package vip.dummy.texasholdem.indication.data;

import vip.dummy.texasholdem.bean.Player;
import vip.dummy.texasholdem.bean.Table;
import vip.dummy.texasholdem.bean.Winner;

public class GameOverData {

    private Table table;
    private Player []players;
    private Winner[]winners;

    public GameOverData(Table table, Player[] players, Winner[] winners) {
        this.table = table;
        this.players = players;
        this.winners = winners;
    }

    public GameOverData() {

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

    public Winner[] getWinners() {
        return winners;
    }

    public void setWinners(Winner[] winners) {
        this.winners = winners;
    }
}
