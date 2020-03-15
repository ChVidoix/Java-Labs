package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    public ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();

        for(Suit suit : Suit.values()){
            for (Value value : Value.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card handOutCard(){
        return cards.remove(0);
    }
}
