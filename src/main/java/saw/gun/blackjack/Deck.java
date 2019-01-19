package saw.gun.blackjack;

import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.LinkedHashMap;

public class Deck {

    private CategoricalVertex<Card, GenericTensor<Card>> deck;

    public Deck(){
        LinkedHashMap<Card, DoubleVertex> frequency = new LinkedHashMap<>();
        for (Card.Face f : Card.Face.values()) {
            for (Card.Suit c : Card.Suit.values()) {
                Card thisCard = new Card(f, c);
                frequency.put(thisCard, new ConstantDoubleVertex(1.0 / (Card.Suit.values().length * Card.Face.values().length)));
            }
        }

        deck = new CategoricalVertex<>(frequency);
    }
}
