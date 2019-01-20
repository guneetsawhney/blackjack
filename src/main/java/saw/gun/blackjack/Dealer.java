package saw.gun.blackjack;

import java.util.Stack;

public class Dealer {
    private Stack<Card> dealerCards = new Stack<>();

    public Dealer() {}

    void clearDealerCards() {
        dealerCards.clear();
    }

    void addDealerCard(Card c) {
        dealerCards.add(c);
    }

    public Stack<Card> getDealerCards() {
        return dealerCards;
    }
}
