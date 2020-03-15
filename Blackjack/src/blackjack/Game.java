package blackjack;

import java.util.Scanner;
import static java.lang.Math.abs;

public class Game {
    Deck deck;
    Player croupier;
    Player player;
    int bet;

    public Game(int bet){
        deck = new Deck();
        deck.shuffle();
        croupier = new Player(true);
        addCard(croupier);
        addCard(croupier);
        player = new Player(false);
        addCard(player);
        addCard(player);
        this.bet = bet;

    }

    void decide(){
        Scanner scan = new Scanner(System.in);
        System.out.println("wyd, type number");
        System.out.println("1 - hit");
        System.out.println("2 - stand");
        System.out.println("3 - double down");
        System.out.println("4 - insurance");

        switch(scan.nextInt()){
            case 1:
                hit(player);
                break;
            case 2:
                stand();
                break;
            case 3:
                doubleDown(player);
                break;
            case 4:
                insurance();
                break;
            default:
                System.out.println("gimme 1-5");
                decide();
        }
    }

    void decideAfterHit() {
        Scanner scan = new Scanner(System.in);
        System.out.println("wyd, type number");
        System.out.println("1 - hit");
        System.out.println("2 - stand");
        switch(scan.nextInt()){
            case 1:
                hit(player);
                break;
            case 2:
                stand();
                break;
            default:
                System.out.println("gimme 1 or 2");
                decideAfterHit();
        }
    }

    void addCard(Player player){
        player.cards.add(deck.handOutCard());
    }

    void hit(Player player){
        addCard(player);
        if (checkIfMoreThan21(player)){
            System.out.println("u lost");
            player.showCards();
        } else {
            decideAfterHit();
        }
    }

    void stand(){
        if(checkIfMoreThan21(player)){
            player.showCards();
            System.out.println("u lost");
        }
        else{
            checkWin(player, croupier);
        }
    }

    void doubleDown(Player player){
        bet *= 2;
        addCard(player);
        stand();
    }

    void insurance(){
        if(croupier.cards.get(0).getCardValue().getValue() != 11){
            System.out.println("u can't take insurance");
            decide();
        } else if(croupier.cards.get(1).getCardValue().getValue() == 10){
            croupier.showCards();
            System.out.println("u lucky boi");
        } else {
            stand();
        }
    }

    boolean checkIfMoreThan21(Player player){
        int sum = 0;
        for (Card card : player.cards) {
            sum += card.getCardValue().getValue();
        }
        return sum > 21;
    }

    void checkWin(Player player, Player croupier){
        int sumPlayer = 0, sumCroupier = 0;

        for (Card card : player.cards) {
            sumPlayer += card.getCardValue().getValue();
        }

        for (Card card: croupier.cards) {
            sumCroupier += card.getCardValue().getValue();
        }

        croupier.cardShowed = true;

        if(abs(21-sumCroupier) <= 21-sumPlayer) {
            croupier.showCards();
            player.showCards();
            System.out.println("u lost");
        } else {
            croupier.showCards();
            player.showCards();
            System.out.println("u lucky boi");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bet:");
        int bet = scanner.nextInt();

        Game game = new Game(bet);
        game.croupier.showCards();
        game.player.showCards();
        game.decide();
    }
}