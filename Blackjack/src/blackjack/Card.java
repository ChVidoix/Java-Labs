package blackjack;

public class Card {
    private Suit cardSuit;
    private Value cardValue;

    Card(Suit suit, Value value){
        cardSuit = suit;
        cardValue = value;
    }

    public Value getCardValue() {
        return cardValue;
    }

    public String toString(){
        return cardValue + " of " + cardSuit;
    }
}
