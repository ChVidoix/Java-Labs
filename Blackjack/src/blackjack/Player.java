package blackjack;

import java.util.ArrayList;

public class Player{
    ArrayList<Card> cards;
    boolean isCroupier;
    boolean cardShowed = false;

    Player(boolean isCroupier){
        cards = new ArrayList<>();
        this.isCroupier = isCroupier;
    }

    void showCards(){
        if(isCroupier) {
            if(cardShowed){
                System.out.println("Croupier's cards:");
                System.out.println(cards.get(0));
                System.out.println(cards.get(1));
            }
            System.out.println("Croupier's card:");
            System.out.println(cards.get(0));
            cardShowed = true;
        } else {
            System.out.println("Your cards:");
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }
}
