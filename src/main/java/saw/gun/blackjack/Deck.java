package saw.gun.blackjack;

import java.util.HashMap;

public class Deck {

    private HashMap<Card, Double> deck;

    public Deck(){
        deck = new HashMap<>();
        double prob = 1/52;
        int i = 0;
        while(i<52){
            //each card has uniform dist: 1/52
            Card c = Card.generateRandomCard();
            if (!deck.containsKey(c)){
                deck.put(c, prob);
                i++;
            }
        }
    }

}
