package vip.dummy.texasholdem.playerai;

import com.google.gson.Gson;
import vip.dummy.texasholdem.IndicationCallbacks;
import vip.dummy.texasholdem.WebSocketClient;
import vip.dummy.texasholdem.indication.*;
import vip.dummy.texasholdem.message.ActionMessage;
import vip.dummy.texasholdem.message.ReloadMessage;
import vip.dummy.texasholdem.message.data.ActionData;
import vip.dummy.texasholdem.message.data.ReloadData;

public class PlayerAI implements IndicationCallbacks {

    private static PlayerAI playerAI;

    private WebSocketClient webSocketClient;

    public PlayerAI(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    /**
     * This is the critical method would be implemented by player AI
     * @return
     */
    public void nextStep() {
        allIn();
    }

    /**
     *  TexasHoldem actions
     */
    void reload() {
        ReloadMessage reloadMessage = new ReloadMessage(new ReloadData());
        webSocketClient.send(reloadMessage.toJson());
    }

    void call() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("call", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    void raise() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("raise", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    void fold() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("fold", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    void check() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("check", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    void bet(int amount) {
        ActionMessage actionMessage = new ActionMessage(new ActionData("bet", amount));
        webSocketClient.send(actionMessage.toJson());
    }

    void allIn() {
        ActionMessage actionMessage = new ActionMessage(new ActionData("allin", 0));
        webSocketClient.send(actionMessage.toJson());
    }

    /**
     * TexasHoldem indications
     */
    @Override
    public void onNewPeer(NewPeerIndication newPeerIndication) {
        System.out.println("<< on new peer joined");
    }

    @Override
    public void onNewRound(NewRoundIndication newRoundIndication) {
        System.out.println("<< on new round");
    }

    @Override
    public void onStartReload(StartReloadIndication startReloadIndication) {
        System.out.println("<< on reload indication");
    }

    @Override
    public void onDeal(DealIndication dealIndication) {
        System.out.println("<< on deal");
    }

    @Override
    public void onAction(ActionIndication actionIndication) {
        System.out.println("<< on action");
        nextStep();
    }

    @Override
    public void onBet(BetIndication betIndication) {
        System.out.println("<< on bet");
        nextStep();
    }

    @Override
    public void onShowAction(ShowActionIndication showActionIndication) {
        System.out.println("<< on deal");
        System.out.println("<< on show action");
    }

    @Override
    public void onRoundEnd(RoundEndIndication roundEndIndication) {
        System.out.println("<< on round end");
    }

    @Override
    public void onGameOver(GameOverIndication gameOverIndication) {
        System.out.println("<< on game over");
    }
}
