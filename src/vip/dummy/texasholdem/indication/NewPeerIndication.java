package vip.dummy.texasholdem.indication;

public class NewPeerIndication extends Indication {

    private String []data;

    public NewPeerIndication(String []data) {
        super("__new_peer");
        this.data = data;
    }

    public String [] getData() {
        return data;
    }

    public void setData(String []data) {
        this.data = data;
    }
}
