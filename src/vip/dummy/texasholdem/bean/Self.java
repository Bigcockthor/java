package vip.dummy.texasholdem.bean;

public class Self {

    private String playerName;
    private int chips;
    private boolean folded;
    private boolean allIn;
    private boolean isSurvive;
    private String []cards;
    private int roundBet;
    private int bet;
    private int minBet;

    public Self(String playerName, int chips, boolean folded, boolean allIn, boolean isSurvive,
                String[] cards, int roundBet, int bet, int minBet) {
        this.playerName = playerName;
        this.chips = chips;
        this.folded = folded;
        this.allIn = allIn;
        this.isSurvive = isSurvive;
        this.cards = cards;
        this.roundBet = roundBet;
        this.bet = bet;
        this.minBet = minBet;
    }

    public Self() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public boolean isAllIn() {
        return allIn;
    }

    public void setAllIn(boolean allIn) {
        this.allIn = allIn;
    }

    public boolean isSurvive() {
        return isSurvive;
    }

    public void setSurvive(boolean survive) {
        isSurvive = survive;
    }

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }

    public int getRoundBet() {
        return roundBet;
    }

    public void setRoundBet(int roundBet) {
        this.roundBet = roundBet;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }
}
