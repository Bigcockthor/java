package vip.dummy.texasholdem;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.Gson;
import vip.dummy.texasholdem.bean.Credential;
import vip.dummy.texasholdem.indication.*;
import vip.dummy.texasholdem.message.JoinMessage;
import vip.dummy.texasholdem.message.data.JoinData;
import vip.dummy.texasholdem.param.SampleConfigurator;
import vip.dummy.texasholdem.param.SampleDecoder;
import vip.dummy.texasholdem.param.SampleEncoder;
import vip.dummy.texasholdem.playerai.PlayerAI;

/**
 * Created by the-engine team
 * 2017-12-17
 *
 * Texas Hold'em AI client web socket interface
 */
@ClientEndpoint(
		configurator = SampleConfigurator.class,
		decoders={SampleDecoder.class},
		encoders={SampleEncoder.class},
		subprotocols={})
public class WebSocketClient {

	private Session session;
	private Credential credential;
	private String ticket;
	private String port;

    private PlayerAI playerAI;

	private static final String NEW_PEER = "__new_peer";
	private static final String NEW_ROUND = "__new_round";
    private static final String START_RELOAD = "__start_reload";
	private static final String DEAL = "__deal";
	private static final String ACTION = "__action";
	private static final String BET = "__bet";
	private static final String SHOW_ACTION = "__show_action";
	private static final String ROUND_END = "__round_end";
	private static final String GAME_OVER = "__game_over";

	WebSocketClient(Credential credential, String ticket, String port) {
	    this.credential = credential;
	    this.ticket = ticket;
	    this.port = port;
	    this.playerAI = new PlayerAI(this);
    }

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Client WebSocket is opening...");
		this.session = session;

		// send join message here with your name
		JoinData joinData = new JoinData(credential.getPhoneNumber(), credential.getPassword(), ticket, port);
		JoinMessage joinMessage = new JoinMessage(joinData);
		String joinString = new Gson().toJson(joinMessage);
		send(joinString);
	}

	@OnMessage
	public void onMessage(String message) {
		// parse message for player AI
        Indication indication = new Gson().fromJson(message, Indication.class);
        switch(indication.getEventName()) {
            case NEW_PEER:
                /*
                 * __new_peer
                 */
                NewPeerIndication newPeerIndication = new Gson().fromJson(message, NewPeerIndication.class);
                playerAI.onNewPeer(newPeerIndication);
                break;

            case NEW_ROUND:
                /*
                 * __new_round
                 */
                NewRoundIndication newRoundIndication = new Gson().fromJson(message, NewRoundIndication.class);
                playerAI.onNewRound(newRoundIndication);
                break;

            case START_RELOAD:
                /*
                 * __start_reload
                 */
                StartReloadIndication startReloadIndication = new Gson().fromJson(message, StartReloadIndication.class);
                playerAI.onStartReload(startReloadIndication);
                break;

            case DEAL:
                /*
                 * __deal
                 */
                DealIndication dealIndication = new Gson().fromJson(message, DealIndication.class);
                playerAI.onDeal(dealIndication);
                break;

            case ACTION:
                /*
                 * __action
                 */
                ActionIndication actionIndication = new Gson().fromJson(message, ActionIndication.class);
                playerAI.onAction(actionIndication);
                break;

            case BET:
                /*
                 * __bet
                 */
                BetIndication betIndication = new Gson().fromJson(message, BetIndication.class);
                playerAI.onBet(betIndication);
                break;

            case SHOW_ACTION:
                /*
                 * __show_action
                 */
                ShowActionIndication showActionIndication = new Gson().fromJson(message, ShowActionIndication.class);
                playerAI.onShowAction(showActionIndication);
                break;

            case ROUND_END:
                /*
                 * __round_end
                 */
                RoundEndIndication roundEndIndication = new Gson().fromJson(message, RoundEndIndication.class);
                playerAI.onRoundEnd(roundEndIndication);
                break;

            case GAME_OVER:
                /*
                 * __game_over
                 */
                GameOverIndication gameOverIndication = new Gson().fromJson(message, GameOverIndication.class);
                playerAI.onGameOver(gameOverIndication);
                break;

            default:
                break;
        }
    }

	@OnClose
	public void onClose() {
        System.out.println("Web socket closed");
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

	public void send(Object message) {
		this.session.getAsyncRemote().sendObject(message);
	}
	
	public void close() throws IOException {
		if (this.session.isOpen()) {
			this.session.close();
		}
	}
}
