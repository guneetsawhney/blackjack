package saw.gun.blackjack;

public class BlackjackController {
    private Deck mDeck;

    public BlackjackController() {
        setNewDeck();
    }

    public Deck getmDeck() {
        return mDeck;
    }

    void setmDeck(Deck mDeck) {
        this.mDeck = mDeck;
    }

    void setNewDeck() {
        setmDeck(new Deck());
    }
}
