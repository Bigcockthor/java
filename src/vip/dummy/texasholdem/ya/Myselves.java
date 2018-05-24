package vip.dummy.texasholdem.ya;

import vip.dummy.texasholdem.bean.Player;
import vip.dummy.texasholdem.indication.NewRoundIndication;

public class Myselves {

    private String[] mycards; //我自己的手牌
    private  final String myname = "ya"; //我自己的name
    private Player myplayer; //我自己的Player
    private String position = "MP";  //我自己的位置，默认MP; btn BB SB UTG MP CO

    public Myselves(NewRoundIndication newRoundIndication){
        Player[] players = newRoundIndication.getData().getPlayers();
        for(int i = 0; i < newRoundIndication.getData().getPlayers().length; i++){
            if(players[i].getPlayerName().equals(this.myname)){
                this.myplayer = players[i];
                break;
            }
        }
        this.mycards = myplayer.getCards();

    }

    //判断自己手牌要不要玩，level表示等级，暂定5为最高吧；0为果断弃牌
    public int level(){
        return isMax(this.mycards);
    }

    //判断手里两张牌是不是AA KK QQ JJ TT
    public int isMax(String[] mycards){
        if(mycards[0].charAt(0) == mycards[1].charAt(0)){
            if(mycards[0].charAt(0) == 'A' ||
                    mycards[0].charAt(0) == 'K'||
                    mycards[0].charAt(0) == 'Q'||
                    mycards[0].charAt(0) == 'J'){
                return 5; //5 为AA KK QQ JJ
            }else{
                return 1; //1 为 对子
            }
        }else if(mycards[0].matches("[AKQJ].") &&mycards[1].matches("[AKQJ].")){
            return 5;     //这里为 AKQJ 四种组合
        }else{
            return 0;
        }
    }
}
