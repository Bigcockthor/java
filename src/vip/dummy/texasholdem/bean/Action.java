package vip.dummy.texasholdem.bean;

public class Action {

    private String action;
    private String playerName;
    private int amount;
    private int chips;

    public Action(String action, String playerName, int amount, int chips) {
        this.action = action;
        this.playerName = playerName;
        this.amount = amount;
        this.chips = chips;
    }

    public Action() {

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}
