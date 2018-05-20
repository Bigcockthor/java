package vip.dummy.texasholdem.bean;

public class Player {

    private String playerName;
    private int chips;
    private boolean folded;
    private boolean allIn;
    private boolean isSurvive;
    private int reloadCount;
    private int roundBet;
    private int bet;
    private String []cards;

    public Player(String playerName, int chips, boolean folded, boolean allIn, boolean isSurvive,
                  int reloadCount, int roundBet, int bet, String[] cards) {
        this.playerName = playerName;
        this.chips = chips;
        this.folded = folded;
        this.allIn = allIn;
        this.isSurvive = isSurvive;
        this.reloadCount = reloadCount;
        this.roundBet = roundBet;
        this.bet = bet;
        this.cards = cards;
    }

    public Player() {

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

    public int getReloadCount() {
        return reloadCount;
    }

    public void setReloadCount(int reloadCount) {
        this.reloadCount = reloadCount;
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

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }
}
