package saw.gun.blackjack;

import java.util.ArrayList;
import java.util.Stack;

public class BlackjackController {
    private Deck mDeck;
    private ArrayList<Player> players;
    private int totalPlayerCount = 2;
    private int currentPlayerNumber = (int) (Math.random() * totalPlayerCount);
    private BlackjackUI mUI;
    private Stack<Card> dealerCards = new Stack<>();

    public BlackjackController(BlackjackUI ui) {

        this.players = new ArrayList<>();
        this.mUI = ui;

        setNewDeck();
        setNewPlayers();
    }

    public Deck getmDeck() {
        return mDeck;
    }

    void setmDeck(Deck mDeck) {
        this.mDeck = mDeck;
    }

    void setNewPlayers() {
        players.clear();
        dealerCards.clear();
        for (int i = 0; i < totalPlayerCount; i++) {
            players.add(new Player(Integer.toString(i+1), i));
        }
    }

    void setNewDeck() {
        setmDeck(new Deck());
    }

    Card drawCard() {return mDeck.drawCard();}

    void prepareNewGame() {
        setNewDeck();
        setNewPlayers();
        handAllCards();
    }

    void handAllCards() {
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                Card c = drawCard();
                p.addCard(c);

                mUI.paintCard(p.getPlayerLocation(), c, i);
            }
            Card c = drawCard();
            dealerCards.add(c);
        }
    }
}
