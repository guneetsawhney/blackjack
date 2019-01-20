package saw.gun.blackjack;

import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;
import java.util.*;

public class Deck {
    private CategoricalVertex<Card, GenericTensor<Card>> deck;
    private int deckSize = (Card.Suit.values().length * Card.Face.values().length);
    private Set drawnCards = new HashSet<Card>();

    public Deck(){
        LinkedHashMap<Card, DoubleVertex> unshuffledDeck = new LinkedHashMap<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Face face : Card.Face.values()) {
                Card c = new Card(face, suit);
                unshuffledDeck.put(c, new ConstantDoubleVertex(1.0 / deckSize));
            }
        }
        deck = new CategoricalVertex<>(unshuffledDeck);
    }

    public Card drawCard() {
        int index = new Random().nextInt(deckSize);
        Card card = (Card) deck.getSelectableValues().keySet().toArray()[index];
        deck.getSelectableValues().put(card, new ConstantDoubleVertex(0));
        deckSize--;
        drawnCards.add(card);
        for (Card c : deck.getSelectableValues().keySet()){
            if (!drawnCards.contains(c)){ //doing stuff
                deck.getSelectableValues().replace(c, new ConstantDoubleVertex(1.0/deckSize));
            }
        }
        return card;
    }

    public Map<Integer, Double> calculateValueProbs(){
        Map<Card, DoubleVertex> deckMap = deck.getSelectableValues();
        Map<Integer, Double> valueProbs = new HashMap<>();
        for (Card c : deckMap.keySet()){
            int cardValue = c.getPoints();
            double cardProb = deckMap.get(c).getValue().getValue(0);
            valueProbs.put(cardValue, valueProbs.getOrDefault(cardValue, 0.0) + cardProb);
        }
        return valueProbs;
    }
}

