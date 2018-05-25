package vip.dummy.texasholdem.ya;

import com.sun.xml.internal.ws.client.dispatch.DataSourceDispatch;
import vip.dummy.texasholdem.bean.Player;
import vip.dummy.texasholdem.indication.*;
import vip.dummy.texasholdem.playerai.PlayerAI;

import java.util.ArrayList;

public class MyMethod {
    final String myName = "ya";
    int my_index;           //自己在playList中的 index;
    String[] mycards;       //自己的手牌
    String myStatus = "跟";        //自己的处境（根据手牌和桌牌判断）
    String[] table_cards;   //桌上的牌
    public int roundCount = 1;
    String roundName = "Deal";       //第几圈，Deal,Flop,Turn,River
    int minBet;

    String table_same_color; //桌牌有 >= 3 的同花，要小心对方同花
    String myPosition = "MP";


    ArrayList<PropertyData> playerList; //玩家列表  包含属性PropertyData
    public static MyMethod myMethod;

    public MyMethod(){
        this.myMethod = this;
    }

    /**
    第一轮开始前，每当接收NewPeer消息，更细 玩家列表playList,更新自己的my_index
     */
    public void myNewPeer(NewPeerIndication newPeerIndication){
        ArrayList<PropertyData> temp = new ArrayList<>();
        for(int i = 0; i < newPeerIndication.getData().length; i++){     //更新newPeer
            if(newPeerIndication.getData()[i].equals( myName)){
                this.my_index = i;
            }
            temp.add(new PropertyData(newPeerIndication.getData()[i]));
        }
        playerList = temp;
        //打印内容
        for(int i = 0; i < playerList.size(); i++){
            System.out.println(playerList.get(i).name);
        }
    }

    /**
    newRound只接收一次
    包含table 和 player
    方法里 更新自己的手牌
          更新所有玩家position （Button,SB,BB,UTG,Cutoff,MP）

     */
    public void myNewRound(NewRoundIndication newRoundIndication){
        //更细所有玩家位置positon
        /*setSmallBlind(newRoundIndication.getData().getTable().getSmallBlind().getPlayerName());//更新小盲
        setBigBlind(newRoundIndication.getData().getTable().getBigBlind().getPlayerName());//更新大盲
        for(int i = 0; i < playerList.size(); i++){  //更新UTG和Cutoff,Button
            if(playerList.get(i).name .equals( newRoundIndication.getData().getTable().getBigBlind().getPlayerName())){
                playerList.get((i+1)%playerList.size()).setPosition("UTG");
                playerList.get((i-1)%playerList.size()).setPosition("SB");
                playerList.get((i-2)%playerList.size()).setPosition("Button");
                playerList.get((i-3)%playerList.size()).setPosition("Cutoff");
            }
        }*/
        //更新自己的手牌
        this.mycards = updataMycards(newRoundIndication);
        System.out.println("newRound -> 更新mycards: "+mycards[0]+" "+mycards[1]);
        /*//更细自己的位置 myPosition
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name.equals( myName)){
                myPosition = playerList.get(i).getPosition();
                break;
            }
        }
        //更新roundCount*/
        this.roundCount = newRoundIndication.getData().getTable().getRoundCount();
        System.out.println("newRound -> 更新roundCount: " + roundCount);

        //根据发的牌，判断自身处境   TODO 干，跟，观望，溜了
        this.myStatus = MyTactic.firstTactic(this.mycards);
        System.out.println("newRound -> 更新myStatus: " + myStatus);
    }

    /**
     Deal为发牌 ，包括Flop,Turn,River
     */
    public void myDeal(DealIndication dealIndication){
        //更新 roundName
        this.roundName = dealIndication.getData().getTable().getRoundName();
        System.out.println("myDeal -> 更新roundName: "+roundName);
        //更新桌上的牌
        this.table_cards = dealIndication.getData().getTable().getBoard();
        System.out.println("myDeal -> 更新table_cards: ");
        for(int i = 0; i < table_cards.length; i++){
            System.out.print(table_cards[i]+" ");
        }
        System.out.println();
        //根据发的牌，判断自身处境   TODO 干，跟，观望，溜了
        this.myStatus = MyTactic.nextTactic(this.mycards,this.table_cards);
        System.out.println("myDeal -> 更新myStatus: "+ myStatus);

    }

    /**
     * 根据上一手玩家行动 “call”, “check”, “fold”, “allin”, “raise”, “bet”
     */
    public void myAction(ActionIndication actionIndication){
        this.minBet = actionIndication.getData().getSelf().getMinBet();  //更新最小下注金额
        System.out.println("myAction -> minBet: "+minBet);
        switch(roundName){
            case "Deal":
                if(myStatus.equals("干")){
                    int randomNum = (int)(Math.random()*10);
                    if(randomNum >= 7){
                        PlayerAI.playerAI.allIn();
                    }else if(randomNum >= 3){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.raise();
                    }
                }else if(myStatus.equals("跟") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("观望")){
                    int randomNum = (int)(Math.random()*10);
                    if(randomNum >= 5){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }else{
                    PlayerAI.playerAI.fold();
                }
                break;

            case "Flop":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.raise();
                }else if(myStatus.equals("跟") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("观望") ){
                    PlayerAI.playerAI.fold();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }else{
                    PlayerAI.playerAI.fold();
                }
                break;

            case "Turn":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("跟") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("观望") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }else{
                    PlayerAI.playerAI.fold();
                }
                break;

            case "River":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.allIn();
                }else if(myStatus.equals("跟") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("观望") ){
                    PlayerAI.playerAI.call();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }else{
                    PlayerAI.playerAI.fold();
                }
                break;
            default:
                PlayerAI.playerAI.fold();
                break;
        }
    }

    /**
     * 押注 先手, 建议是“bet”, 也可以是 “check”, “fold”, “allin” 中的其中一种
     */
    public void myBet(BetIndication betIndication){   //第几圈，Deal,Flop,Turn,River
        this.minBet = betIndication.getData().getSelf().getMinBet();  //更新最小下注金额
        System.out.println("myBet -> minBet: "+minBet);
        switch(roundName){
            case "Deal":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("跟")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("观望")){
                    PlayerAI.playerAI.check();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.check();
                }

                break;
            case "Flop":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("跟")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("观望")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }
                break;
            case "Turn":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("跟")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("观望")){
                    PlayerAI.playerAI.bet(this.minBet);
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }
                break;
            case "River":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.allIn();
                }else if(myStatus.equals("跟")){
                    PlayerAI.playerAI.bet(20);
                }else if(myStatus.equals("观望")){
                    PlayerAI.playerAI.bet(20);
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }
                break;
                default:
                    PlayerAI.playerAI.check();
                    break;
        }
    }

    /**
    记录每个玩家的行为，属性保存在PropertyData
     */
    public void myShowAction(ShowActionIndication showActionIndication){
        String the_name = showActionIndication.getData().getAction().getPlayerName();
        String the_action = showActionIndication.getData().getAction().getAction();
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name.equals(the_name)){
                if(the_action.equals("fold")){
                    playerList.get(i).first_fold_count++;
                }else if(the_action.equals("allIn")){
                    playerList.get(i).first_allIn_count++;
                }
            }
        }



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
            if(playerList.get(i).name .equals(sb_name)){
                playerList.get(i).setPosition("SB");
                return;
            }
        }
    }
    //设置大盲
    public void setBigBlind(String bb_name){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name.equals(bb_name)){
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
