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
    int BigBlind;

    String table_same_color; // TODO 桌牌有 >= 3 的同花，要小心对方同花   （未处理）
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
        }*/

        //更新roundCount
        this.roundCount = newRoundIndication.getData().getTable().getRoundCount();
        System.out.println("newRound -> 更新roundCount: " + roundCount);

        //更新roundName
        this.roundName = "Deal";
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
        this.BigBlind = actionIndication.getData().getGame().getBigBlind().getAmount();//更新大盲金额
        System.out.println("myAction -> BigBlind: "+BigBlind);
        switch(roundName){
            case "Deal":
                if(myStatus.equals("干")){
                    System.out.println("执行 Deal 干");
                    int randomNum = (int)(Math.random()*10);
                    if(randomNum >= 7){
                        PlayerAI.playerAI.allIn();
                        System.out.println("随机 执行 allin 。rendom:"+randomNum);
                    }else if(randomNum >= 3){
                        System.out.println("随机 执行 call 。rendom:"+randomNum);
                        PlayerAI.playerAI.call();
                    }else{
                        System.out.println("随机 执行 raise 。rendom:"+randomNum);
                        PlayerAI.playerAI.raise();
                    }

                }else if(myStatus.equals("跟") ){
                    System.out.println("执行 Deal 跟");
                    if(this.minBet <= 2 * BigBlind ){
                        System.out.println("执行 minBet <= 2 * BigBlind ，跟");
                        PlayerAI.playerAI.call();
                    }else{
                        System.out.println("执行 minBet !<= 2 * BigBlind， 放弃跟，弃牌");
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("观望")){
                    System.out.println("执行 Deal 观望");
                    //TODO 这个bug 得好好看看 大盲位置竟然弃牌?! 小盲也弃牌？？ OK BUG已修复，忘记重新赋值roundName了
                    if(minBet <= BigBlind){
                        System.out.println("执行 Deal 观望 minBet <= BigBlind");
                        PlayerAI.playerAI.call();
                    }else{
                        System.out.println("执行 Deal 观望 min !<= BigBlind");
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("溜了")){
                    System.out.println("执行 Deal 溜了");
                    if(minBet < BigBlind ){    //判断位于 大盲 或者 小盲位置 押注小于大盲 0或一半
                        System.out.println("执行 minBet < BigBlind");
                        PlayerAI.playerAI.call();
                    }else{
                        int randomNum = (int)(Math.random()*10);
                        if(randomNum <= 3 && minBet <= BigBlind){
                            System.out.println("randomNum: " + roundName);
                            PlayerAI.playerAI.call();
                        }else{
                            System.out.println("执行 minBet !< BigBlind");
                            PlayerAI.playerAI.fold();
                        }
                    }

                }else{
                    System.out.println("<错误报告>有其他情况？--myAction -Deal - fold"+ "myStatus: "+myStatus);
                    PlayerAI.playerAI.fold();
                }


                break;

            case "Flop":
                System.out.println("执行 Flop:");
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
                    if(minBet <= 2* BigBlind){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("观望") ){
                    if(minBet <= BigBlind ){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("溜了")){
                    if(minBet == 0){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else{
                    PlayerAI.playerAI.fold();
                }
                break;

            case "Turn":
                System.out.println("执行 Turn:");
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
                    if(minBet <= 2* BigBlind){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("观望") ){
                    if(minBet <= BigBlind){
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

            case "River":
                System.out.println("执行 River:");
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.allIn();
                }else if(myStatus.equals("跟") ){
                    if(minBet <= 2* BigBlind){
                        PlayerAI.playerAI.call();
                    }else{
                        PlayerAI.playerAI.fold();
                    }
                }else if(myStatus.equals("观望") ){
                    PlayerAI.playerAI.fold();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }else{
                    PlayerAI.playerAI.fold();
                }
                break;
            default:
                System.out.println("<错误报告>--输出了一次默认 《myAction》！！！！！！！！！！！！！！");
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
        this.BigBlind = betIndication.getData().getGame().getBigBlind().getAmount();//更新大盲金额
        System.out.println("myBction -> BigBlind: "+BigBlind);

        switch(roundName){
            case "Deal":
                if(myStatus.equals("干")){
                    int randomNum = (int)(Math.random()*10);
                    if(randomNum >= 5){
                        PlayerAI.playerAI.bet(2*BigBlind);
                    }else{
                        PlayerAI.playerAI.bet(BigBlind);
                    }

                }else if(myStatus.equals("跟")){
                    if(minBet <= 2* BigBlind){
                        PlayerAI.playerAI.bet(BigBlind);
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("观望")){
                    System.out.println("是执行了这个 观望 ？？？？？");  //找bug！！！！！！
                    if(minBet <= BigBlind){
                        PlayerAI.playerAI.check();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("溜了")){
                    if(minBet <= BigBlind ){
                        PlayerAI.playerAI.check();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }
                break;

            case "Flop":
                if(myStatus.equals("干")){
                    int randomNum = (int)(Math.random()*10);
                    if(randomNum >= 5){
                        PlayerAI.playerAI.bet(2*BigBlind);
                    }else{
                        PlayerAI.playerAI.bet(BigBlind);
                    }
                }else if(myStatus.equals("跟")){
                    if(minBet <= 2* BigBlind){
                        PlayerAI.playerAI.bet(BigBlind);
                    }else{
                        PlayerAI.playerAI.fold();
                    }
                }else if(myStatus.equals("观望")){
                    if(minBet <= BigBlind){
                        PlayerAI.playerAI.check();
                    }else{
                        PlayerAI.playerAI.fold();
                    }
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
                    if(minBet <= BigBlind){
                        PlayerAI.playerAI.check();
                    }else{
                        PlayerAI.playerAI.fold();
                    }

                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }
                break;

            case "River":
                if(myStatus.equals("干")){
                    PlayerAI.playerAI.allIn();
                }else if(myStatus.equals("跟")){
                    if(minBet <= 2*BigBlind){
                        PlayerAI.playerAI.check();
                    }else{
                        PlayerAI.playerAI.fold();
                    }
                }else if(myStatus.equals("观望")){
                    PlayerAI.playerAI.fold();
                }else if(myStatus.equals("溜了")){
                    PlayerAI.playerAI.fold();
                }
                break;

                default:
                    System.out.println("输出了一次默认<myBet>！！！！！！！！！！！！！！");
                    PlayerAI.playerAI.check();
                    break;
        }
    }

    /**
    记录每个玩家的行为，属性保存在PropertyData
     */
    public void myShowAction(ShowActionIndication showActionIndication){
        String the_name = showActionIndication.getData().getAction().getPlayerName();
        int index = 0;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name.equals(the_name)){
                index = i;
            }
        }
        String action = showActionIndication.getData().getAction().getAction();
        int amount = showActionIndication.getData().getAction().getAmount();
        System.out.println("--index: "+index+"  name: "+the_name);
        System.out.println("--action: "+action+"  amount: "+amount);
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
