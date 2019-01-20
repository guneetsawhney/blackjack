package saw.gun.blackjack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class BlackjackController {
    private int currentPlayerLocation = 0;
    private Deck mDeck;
    private ArrayList<Player> players;
    private int totalPlayerCount = 4;
    private int controlledPlayer = (int) (Math.random() * totalPlayerCount);
    private BlackjackUI mUI;
    private Dealer dealer = new Dealer();
    static HashSet<Card> dealtCards = new HashSet<>();
    boolean endOfGame = false;

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
            players.add(new Player(Integer.toString(i + 1), i));
        }
    }

    void setNewDeck() {
        setmDeck(new Deck());
    }

    Card drawCard() {
        return mDeck.drawCard();
    }

    void prepareNewGame() {
        setNewDeck();
        setNewPlayers();
        handNewCards();
        for (Integer i : getCurrentPlayerPoints()) {
            System.out.println(i);
        }
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
            } else mUI.paintDealerCard(i, c, false);
        }
    }

    void handCardToCurrentPlayer() {
        Player currentPlayer = players.get(currentPlayerLocation);
        Card thisCard = drawCard();
        dealtCards.add(thisCard);
        currentPlayer.addCard(thisCard);
        mUI.paintCard(currentPlayerLocation, thisCard, currentPlayer.getCardinHand().size());

        for (Integer i : getCurrentPlayerPoints()) {
            System.out.println(i);
        }

        if (!currentPlayer.pointsInLimit()) toNextPlayer();
    }

    HashSet<Integer> getCurrentPlayerPoints() {
        return players.get(currentPlayerLocation).cardPoints();
    }

    double currentUserDealtProb() {
        return players.get(currentPlayerLocation).calcprob();
    }

    void progress() {
        if (currentPlayerLocation >= players.size()) {
            mUI.disableAllButton();
            endOfGame = true;
        }
        if (currentPlayerLocation != controlledPlayer) {
            Player currentPlayer = this.players.get(currentPlayerLocation);
            double pickUpProb = currentPlayer.calcprob();
            if (pickUpProb > currentPlayer.getThredhold()) {
                // Continue to next player
                toNextPlayer();
            } else {
                // Pick up a card and draw
                handCardToCurrentPlayer();
            }
        } else toNextPlayer();
    }

    boolean controlledPlayerPointsInLimit() {
        return players.get(controlledPlayer).pointsInLimit();
    }

    void toNextPlayer() {
        if (currentPlayerLocation + 1 < this.players.size()) currentPlayerLocation++;
        else {
            endOfGame = true;
            mUI.disableAllButton();
        }
    }

    boolean currentPlayerIsControlled() {
        return this.controlledPlayer == this.currentPlayerLocation;
    }

    int getCurrentPlayerLocation() {
        return currentPlayerLocation;
    }

    boolean endOfList() {
        boolean out = currentPlayerLocation + 1 >= this.players.size();
        endOfGame = out;
        return out;
    }

    int getControlledPlayer() {
        return controlledPlayer;
    }

    void checkPlayerWon() {
        if (players.get(currentPlayerLocation).playerWon()) {
            toNextPlayer();
        }
    }
}