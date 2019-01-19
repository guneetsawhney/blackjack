package saw.gun.blackjack;

import java.util.Stack;

public class Deck {

    private Stack<Card> deck;

    public Deck(){
        deck = new Stack<>();
        int i = 0;
        while(i<52){
            //each card has uniform dist: 1/52
            Card c = Card.generateRandomCard(52);
            if (deck.search(c) == -1){
                deck.push(c);
                i++;
            }
        }
    }


    public Card draw() {
        return deck.pop();
    }
}
