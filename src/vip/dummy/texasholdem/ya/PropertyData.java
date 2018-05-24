package vip.dummy.texasholdem.ya;

public class PropertyData {
    public String name;
    private String position = "MP";

    public int first_fold_count;
    public int last_fold_count;
    public int first_allIn_count;
    public int last_allIn_count;
    public int win_count;

    public PropertyData(){}
    public PropertyData(String name){
        this.name = name;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
