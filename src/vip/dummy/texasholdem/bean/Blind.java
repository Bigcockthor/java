package vip.dummy.texasholdem.bean;

public class Blind {

    private String playerName;
    private int amount;

    public Blind(String playerName, int amount) {
        this.playerName = playerName;
        this.amount = amount;
    }

    public Blind() {

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
}
