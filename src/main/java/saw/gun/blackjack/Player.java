package saw.gun.blackjack;

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

    public void setPlayerLocation(int playerLocation) {
        this.playerLocation = playerLocation;
    }

    public Stack<Card> getCardinHand() {
        return cardinHand;
    }

    public void setCardinHand(Stack<Card> cardinHand) {
        this.cardinHand = cardinHand;
    }
}
