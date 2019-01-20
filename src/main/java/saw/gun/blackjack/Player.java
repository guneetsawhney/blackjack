package saw.gun.blackjack;

import java.util.Stack;

public class Player {
    private String name;
    private int playerLocation;
    private int prob;
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

    public void setPlayerLocation(int playerLocation) {
        this.playerLocation = playerLocation;
    }

    public Stack<Card> getCardinHand() {
        return cardinHand;
    }

    public void setCardinHand(Stack<Card> cardinHand) {
        this.cardinHand = cardinHand;
    }

    public int calcprob(Stack<Card> cardinHand) {
        // if no other cards are visible
        total = 0;
        for (Card c : cardinHand) {
            int x = c.getFace().asInt();
            total += x;

        }
        if (total < 17) {
            prob = 0;
        }
        else {
            prob = ((14- (21-total))*4) / (52- cardinHand.size());
        }

        return prob;
        //if probability is less than 0.5 then draw another card
    }
}
