package saw.gun.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Player {
    private String name;
    private int playerLocation;
    private double prob;
    private int total;

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

    public double calcprob() {
        // if no other cards are visible
//        total = 0;
//        for (Card c : cardinHand) {
//            int x = c.getFace().asInt();
//            total += x;
//
//        }
        HashMap<Integer, Double> probs = new HashMap<>();
        double probsMult = 1.0;
        for (int res : cardPoints()) {
            int count = 0;
            double currentProb = 0.0;
            if (res < 17) {
                currentProb = 0;
            }
            //player has lost
            else if (res > 21) {
                currentProb = 1;
            }
            else {
                for (Card c: BlackjackController.dealtCards) {
                    if (c.getFace().asInt() > (21-res)) {
                        count += 1;
                    }
                }
                currentProb = (double) (((14- (21-res))*4) - count) / (52- BlackjackController.dealtCards.size());
            }
            probs.put(res, currentProb);
            probsMult *= currentProb;
        }



        return probsMult;
        //if probability is less than 0.5 then draw another card
    }
}
