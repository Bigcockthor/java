package vip.dummy.texasholdem.playerai;

import com.google.gson.Gson;
import vip.dummy.texasholdem.IndicationCallbacks;
import vip.dummy.texasholdem.WebSocketClient;
import vip.dummy.texasholdem.indication.*;
import vip.dummy.texasholdem.message.ActionMessage;
import vip.dummy.texasholdem.message.ReloadMessage;
import vip.dummy.texasholdem.message.data.ActionData;
import vip.dummy.texasholdem.message.data.ReloadData;
import vip.dummy.texasholdem.ya.MyMethod;
import vip.dummy.texasholdem.ya.Myselves;

public class PlayerAI implements IndicationCallbacks {

    public static PlayerAI playerAI;

    private WebSocketClient webSocketClient;

    public PlayerAI(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
        playerAI = this;
    }

    /**
     * This is the critical method would be implemented by player AI
     * @return
     */
    public void nextStep() {

    }

    /**
     *  TexasHoldem actions
     */
    public void reload() {
        ReloadMessage reloadMessage = new ReloadMessage(new ReloadData());
        webSocketClient.send(reloadMessage.toJson());
    }

    public void call() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("call", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    public void raise() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("raise", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    public void fold() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("fold", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    public void check() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("check", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    public void bet(int amount) {
        ActionMessage actionMessage = new ActionMessage(new ActionData("bet", amount));
        webSocketClient.send(actionMessage.toJson());
    }

    public void allIn() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("allin", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    /**
     * TexasHoldem indications
     */
    @Override
    public void onNewPeer(NewPeerIndication newPeerIndication) {
        System.out.println("<< on new peer joined");
        new MyMethod();
        MyMethod.myMethod.myNewPeer(newPeerIndication);
    }

    @Override
    public void onNewRound(NewRoundIndication newRoundIndication) {//发两张牌
        System.out.println("<< on new round!!!!!!!!!!!!!!!!!!!!!!=====>>>");
        MyMethod.myMethod.myNewRound(newRoundIndication);
    }

    @Override
    public void onStartReload(StartReloadIndication startReloadIndication) {
        System.out.println("<< on reload indication");
    }

    @Override
    public void onDeal(DealIndication dealIndication) {
        System.out.println("<< on deal");
        MyMethod.myMethod.myDeal(dealIndication);
    }

    @Override
    public void onAction(ActionIndication actionIndication) {
        System.out.println("<< on action");
        MyMethod.myMethod.myAction(actionIndication);
    }

    @Override
    public void onBet(BetIndication betIndication) {
        System.out.println("<< on bet");
        MyMethod.myMethod.myBet(betIndication);
        //nextStep();
    }

    @Override
    public void onShowAction(ShowActionIndication showActionIndication) {
        System.out.println("");
        System.out.println("<< on show action");
        MyMethod.myMethod.myShowAction(showActionIndication);

    }

    @Override
    public void onRoundEnd(RoundEndIndication roundEndIndication) {
        System.out.println("<< on round end");
        System.out.println("==================ROUND_END================");
    }

    @Override
    public void onGameOver(GameOverIndication gameOverIndication) {
        System.out.println("<< on game over");
    }
}
