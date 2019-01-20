package saw.gun.blackjack;

import java.util.ArrayList;
import java.util.HashSet;

public class BlackjackController {
    private Deck mDeck;
    private ArrayList<Player> players;
    private int totalPlayerCount = 2;
    private int currentPlayerNumber = (int) (Math.random() * totalPlayerCount);
    private BlackjackUI mUI;
    private Dealer dealer = new Dealer();
    static HashSet<Card> dealtCards = new HashSet<>();

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
        dealer.clearDealerCards();
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
        handNewCards();
//        for (Integer i : getCurrentPlayerPoints()) {
//            System.out.println(i);
//        }
    }

    void handNewCards() {
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                Card c = drawCard();
                p.addCard(c);
                dealtCards.add(c);

                mUI.paintCard(p.getPlayerLocation(), c, i);
            }
            Card c = drawCard();
            dealer.addDealerCard(c);
            if (i == 0) {
                mUI.paintDealerCard(i, c, true);
                dealtCards.add(c);
            }
            else mUI.paintDealerCard(i, c, false);
        }
    }

    void handCardToCurrentPlayer() {
        Card thisCard = drawCard();
        dealtCards.add(thisCard);
        players.get(currentPlayerNumber).addCard(thisCard);
        mUI.paintCard(currentPlayerNumber, thisCard, players.get(currentPlayerNumber).getCardinHand().size());

//        for (Integer i : getCurrentPlayerPoints()) {
//            System.out.println(i);
//        }
    }

//    HashSet<Integer> getCurrentPlayerPoints() {
//        return players.get(currentPlayerNumber).cardPoints();
//    }
//
//    boolean currentPlayerPointsInLimit() {
//        for (int i : getCurrentPlayerPoints()) {
//            if (i <= 21) return true;
//        }
//
//        return false;
//    }
}
