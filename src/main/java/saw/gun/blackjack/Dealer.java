package saw.gun.blackjack;

import java.util.HashSet;
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

    HashSet<Integer> cardPoints() {
        HashSet<Integer> out = new HashSet<>();
        for (Card c : dealerCards) {
            if (c.getFace() == Card.Face.ACE) {
                if (out.isEmpty()) {
                    out.add(1);
                    out.add(11);
                }
                else {
                    HashSet<Integer> newOut = new HashSet<>();
                    for (Integer i : out) {
                        newOut.add(i+1);
                        newOut.add(i+11);
                    }
                    out = newOut;
                }
            } else {
                int cardFaceValue = c.getFace().asInt();
                if (out.isEmpty()) out.add(cardFaceValue);
                else {
                    HashSet<Integer> newOut = new HashSet<>();
                    for (Integer i : out) {
                        newOut.add(i + cardFaceValue);
                    }
                    out = newOut;
                }
            }
        }

        return out;
    }

    boolean pointsInLimit() {
        for (int i : cardPoints()) {
            if (i < 17) return true;
        }

        return false;
    }
}
