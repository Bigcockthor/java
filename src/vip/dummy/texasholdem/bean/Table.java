package vip.dummy.texasholdem.bean;

public class Table {

    private String tableName;
    private String roundName;
    private String []board;
    private int roundCount;
    private int raiseCount;
    private int betCount;
    private int totalBet;
    private Blind smallBlind;
    private Blind bigBlind;

    public Table(String tableName, String roundName, String []board, int roundCount,
                 int raiseCount, int betCount, int totalBet, Blind smallBlind, Blind bigBlind) {
        this.tableName = tableName;
        this.roundName = roundName;
        this.board = board;
        this.roundCount = roundCount;
        this.raiseCount = raiseCount;
        this.betCount = betCount;
        this.totalBet = totalBet;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
    }

    public Table() {

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
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

    public int getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(int totalBet) {
        this.totalBet = totalBet;
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
}
