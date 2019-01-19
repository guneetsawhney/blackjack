package saw.gun.blackjack;

import io.improbable.keanu.vertices.intgr.probabilistic.UniformIntVertex;

import java.util.Random;

public class Card {
    private Face aFace;
    private Suit aSuit;
    private UniformIntVertex deckSize;

    public Card(Face face, Suit suit, int noOfCards) {
        aFace = face;
        aSuit = suit;
        deckSize = new UniformIntVertex (0, noOfCards);
    }

    public static Card generateRandomCard(int deckSize) {
        Random rnd = new Random();
        Face[] faces = Face.values();
        Suit[] suits = Suit.values();
        return new Card(faces[rnd.nextInt(faces.length)], suits[rnd.nextInt(suits.length)], deckSize);
    }

    public int getPoints() {
        return aFace.asInt();
    }

    public Face getFace() {
        return aFace;
    }

    public UniformIntVertex getDeckSize() {
        return deckSize;
    }

    @Override
    public String toString() {
        return String.format("%c, %c", aFace.asChar(), aSuit.asChar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card &&  aFace == ((Card) obj).aFace && aSuit == ((Card) obj).aSuit;
    }

    public enum Face {
        ACE, TWO, THREE, FOUR, FIVE,
        SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING;

        public int asInt() {
            switch (this) {
                case ACE:
                    return 1; // one or 11 depending on player
                case TWO:
                    return 2;
                case THREE:
                    return 3;
                case FOUR:
                    return 4;
                case FIVE:
                    return 5;
                case SIX:
                    return 6;
                case SEVEN:
                    return 7;
                case EIGHT:
                    return 8;
                case NINE:
                    return 9;
                case TEN:
                    return 10;
                case JACK:
                    return 10;
                case QUEEN:
                    return 10;
                case KING:
                    return 10;
                default:
                    return 0;
            }
        }

        public char asChar() {
            switch (this) {
                case ACE:
                    return 'A';
                case TWO:
                    return '2';
                case THREE:
                    return '3';
                case FOUR:
                    return '4';
                case FIVE:
                    return '5';
                case SIX:
                    return '6';
                case SEVEN:
                    return '7';
                case EIGHT:
                    return '8';
                case NINE:
                    return '9';
                case TEN:
                    return 'T';
                case JACK:
                    return 'J';
                case QUEEN:
                    return 'Q';
                case KING:
                    return 'K';
                default:
                    return '0';
            }
        }
    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;

        public char asChar() {
            return name().charAt(0);
        }
    }

}
