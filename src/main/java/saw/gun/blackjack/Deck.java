package saw.gun.blackjack;

import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Deck {

    private CategoricalVertex<Card, GenericTensor<Card>> deck;
    private Iterator<Map.Entry<Card, DoubleVertex>> deckIterator;
    private int iteratorCount = -1;

    public Deck(){
        LinkedHashMap<Card, DoubleVertex> unshuffledDeck = new LinkedHashMap<>();
        double deckSize = (Card.Suit.values().length * Card.Face.values().length);

        while (unshuffledDeck.size() < deckSize) {
            Card c = Card.generateRandomCard();
            if (!unshuffledDeck.containsKey(c)) {
                unshuffledDeck.put(c, new ConstantDoubleVertex(1.0 / deckSize));
            }
        }
        deck = new CategoricalVertex<>(unshuffledDeck);
        deckIterator = deck.getSelectableValues().entrySet().iterator();
    }

    public Card drawCard() {
        Card nextCard = deckIterator.next().getKey();
        deck.getSelectableValues().put(nextCard, new ConstantDoubleVertex(0));
        Iterator<Map.Entry<Card, DoubleVertex>> followingIterator = deckIterator;
        iteratorCount++;

        return nextCard;
    }
}
