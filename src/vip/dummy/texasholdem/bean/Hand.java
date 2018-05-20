package vip.dummy.texasholdem.bean;

public class Hand {

    private String []cards;
    private float rank;
    private String message;

    public Hand(String[] cards, float rank, String message) {
        this.cards = cards;
        this.rank = rank;
        this.message = message;
    }

    public Hand() {

    }

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
