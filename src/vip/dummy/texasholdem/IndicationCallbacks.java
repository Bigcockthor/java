package vip.dummy.texasholdem;

import vip.dummy.texasholdem.indication.*;

public interface IndicationCallbacks {

    void onNewPeer(NewPeerIndication newPeerIndication);

    void onNewRound(NewRoundIndication newRoundIndication);

    void onStartReload(StartReloadIndication startReloadIndication);

    void onDeal(DealIndication dealIndication);

    void onAction(ActionIndication actionIndication);

    void onBet(BetIndication betIndication);

    void onShowAction(ShowActionIndication showActionIndication);

    void onRoundEnd(RoundEndIndication roundEndIndication);

    void onGameOver(GameOverIndication gameOverIndication);

}
