package saw.gun.blackjack;

import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.LinkedHashMap;

public class Deck {

    private CategoricalVertex<Card, GenericTensor<Card>> deck;

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
    }
}
