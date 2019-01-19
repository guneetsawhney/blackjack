package saw.gun.blackjack;

import java.util.ArrayList;

public class BlackjackController {
    private Deck mDeck;
    private ArrayList<Player> players;
    private int playerNumber = 2;
    private BlackjackUI mUI;

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
        for (int i = 0; i < playerNumber; i++) {
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
        }
    }
}
