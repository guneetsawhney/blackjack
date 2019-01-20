package saw.gun.blackjack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Player {
    private String name;
    private int playerLocation;

    private Stack<Card> cardinHand;

    public Player(String name, int playerLocation) {
        this.name = name;
        this.cardinHand = new Stack<>();
        this.playerLocation = playerLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card c) {
        cardinHand.add(c);
    }

    public int getPlayerLocation() {
        return playerLocation;
    }

    public Stack<Card> getCardinHand() {
        return cardinHand;
    }

    HashSet<Integer> cardPoints() {
        HashSet<Integer> out = new HashSet<>();
        for (Card c : cardinHand) {
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

//    public boolean cardDrawnWithinLimit(Card card) {
//        if (Card.Face == Card.Face.ACE) {
//
//        }
//    }
}
