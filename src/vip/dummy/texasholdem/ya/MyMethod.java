package vip.dummy.texasholdem.ya;

import com.sun.xml.internal.ws.client.dispatch.DataSourceDispatch;
import vip.dummy.texasholdem.indication.DealIndication;
import vip.dummy.texasholdem.indication.NewPeerIndication;
import vip.dummy.texasholdem.indication.NewRoundIndication;

import java.util.ArrayList;

public class MyMethod {
    final String myName = "ya";
    int my_index;      //自己在playList中的 index;
    String[] mycards;  //自己的手牌
    String[] table_cards; //桌上的牌
    String myStatus; //自己的处境（根据手牌和桌牌判断）

    String roundName;  //第几圈，Deal,Flop,Turn,River

    ArrayList<PropertyData> playerList;
    public static MyMethod myMethod;

    public MyMethod(){}

    /**
    第一轮开始前，每当接收NewPeer消息，更细 玩家列表playList,更新自己的my_index
     */
    public void myNewPeer(NewPeerIndication newPeerIndication){
        ArrayList<PropertyData> temp = new ArrayList<>();
        for(int i = 0; i < newPeerIndication.getData().length; i++){     //更新newPeer
            if(newPeerIndication.getData()[i] == myName){
                this.my_index = i;
            }
            temp.add(new PropertyData(newPeerIndication.getData()[i]));
        }
        playerList = temp;
    }

    /**
    newRound只接收一次
    包含table 和 player
    方法里 更新自己的手牌
          更新所有玩家position （Button,SB,BB,UTG,Cutoff,MP）

     */
    public void myNewRound(NewRoundIndication newRoundIndication){
        //更细所有玩家位置positon
        setSmallBlind(newRoundIndication.getData().getTable().getSmallBlind().getPlayerName());//更新小盲
        setBigBlind(newRoundIndication.getData().getTable().getBigBlind().getPlayerName());//更新大盲
        for(int i = 0; i < playerList.size(); i++){  //更新UTG和Cutoff,Button
            if(playerList.get(i).name == newRoundIndication.getData().getTable().getBigBlind().getPlayerName()){
                playerList.get((i+1)%playerList.size()).setPosition("UTG");
                playerList.get((i-1)%playerList.size()).setPosition("SB");
                playerList.get((i-2)%playerList.size()).setPosition("Button");
                playerList.get((i-3)%playerList.size()).setPosition("Cutoff");
            }
        }
        //更新自己的手牌
        this.mycards = updataMycards(newRoundIndication);
    }

    /**
     Deal为发牌 ，包括Flop,Turn,River
     */
    public void myDeal(DealIndication dealIndication){
        //更新第几圈 roundName
        this.roundName = dealIndication.getData().getTable().getRoundName();
        //更新桌上的牌
        this.table_cards = dealIndication.getData().getTable().getBoard();
        //根据发的牌，判断自身处境


    }

    public void myAction(){

    }


    public void myBet(){

    }

    /**
    记录每个玩家的行为，属性保存在PropertyData
     */
    public void myShowAction(){

    }

    /**
     一局结束，可以看到所有玩家的牌，可以分析玩家的策略
     */
    public void myRoundEnd(){

    }

    /**
     game over
     */
    public void myGameOver(){

    }


    /**
    以下为一些方法
     */
    //设置小盲
    public void setSmallBlind(String sb_name){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name == sb_name){
                playerList.get(i).setPosition("SB");
                return;
            }
        }
    }
    //设置大盲
    public void setBigBlind(String bb_name){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name == bb_name){
                playerList.get(i).setPosition("BB");
                return;
            }
        }
    }
    //更新自己的手牌
    public String[] updataMycards(NewRoundIndication newRoundIndication){
        return newRoundIndication.getData().getPlayers()[my_index].getCards();
    }
    //更新自己当前处境
    public void updataMyStatus(){

    }
}
