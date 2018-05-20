package vip.dummy.texasholdem.message.data;

public class JoinData {
    private String phoneNumber;
    private String password;
    private String ticket;
    private String gameName;
    private String port;

    public JoinData(String phoneNumber, String password, String ticket, String port) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.ticket = ticket;
        this.gameName = "texas_holdem";
        this.port = port;
    }

    public JoinData() {
        this.gameName = "texas_holdem";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
