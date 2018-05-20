package vip.dummy.texasholdem.bean;

public class Winner {

    private String playerName;
    private Hand hand;
    private int chips;

    public Winner(String playerName, Hand hand, int chips) {
        this.playerName = playerName;
        this.hand = hand;
        this.chips = chips;
    }

    public Winner() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}
