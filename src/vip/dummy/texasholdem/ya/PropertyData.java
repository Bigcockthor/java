package vip.dummy.texasholdem.ya;

public class PropertyData {
    String name;
    private String position = "MP";
    private int first_fold_count;
    private int last_fold_count;
    private int first_allIn_count;
    private int last_allIn_count;
    private int win_count;

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

    public int getFirst_fold_count() {
        return first_fold_count;
    }

    public void setFirst_fold_count(int first_fold_count) {
        this.first_fold_count = first_fold_count;
    }

    public int getLast_fold_count() {
        return last_fold_count;
    }

    public void setLast_fold_count(int last_fold_count) {
        this.last_fold_count = last_fold_count;
    }

    public int getFirst_allIn_count() {
        return first_allIn_count;
    }

    public void setFirst_allIn_count(int first_allIn_count) {
        this.first_allIn_count = first_allIn_count;
    }

    public int getLast_allIn_count() {
        return last_allIn_count;
    }

    public void setLast_allIn_count(int last_allIn_count) {
        this.last_allIn_count = last_allIn_count;
    }

    public int getWin_count() {
        return win_count;
    }

    public void setWin_count(int win_count) {
        this.win_count = win_count;
    }
}
