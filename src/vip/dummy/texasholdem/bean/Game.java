package vip.dummy.texasholdem.bean;

public class Game {

    private Blind smallBlind;
    private Blind bigBlind;
    private String []board;
    private int raiseCount;
    private int betCount;
    private String roundName;
    private Player []players;

    public Game(Blind smallBlind, Blind bigBlind, String[] board, int raiseCount,
                int betCount, String roundName, Player[] players) {
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.board = board;
        this.raiseCount = raiseCount;
        this.betCount = betCount;
        this.roundName = roundName;
        this.players = players;
    }

    public Game() {

    }

    public Blind getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(Blind smallBlind) {
        this.smallBlind = smallBlind;
    }

    public Blind getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(Blind bigBlind) {
        this.bigBlind = bigBlind;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public int getRaiseCount() {
        return raiseCount;
    }

    public void setRaiseCount(int raiseCount) {
        this.raiseCount = raiseCount;
    }

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
