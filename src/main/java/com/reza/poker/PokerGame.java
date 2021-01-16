package com.reza.poker;

import com.reza.poker.models.*;
import com.reza.poker.services.Calculator;

import java.util.List;
import java.util.Scanner;

public class PokerGame {
    public static void main(String[] args) {
        System.out.println("Insert Cards: ");
        System.out.println("Cards number: Ace, two, three, four, five, six,seven, eight, nine, ten, jack, queen, king");
        System.out.println("Cards Suits: Diamond, Spade, Club, Hearts");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1 hand: ");
        System.out.println("Card 1: ");
        System.out.print("Enter Number: ");
        CardNumber player1CardN1 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits player1CardS1 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 2: ");
        System.out.print("Enter Number: ");
        CardNumber player1CardN2 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits player1CardS2 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Player 2 hand: ");
        System.out.println("Card 1: ");
        System.out.print("Enter Number: ");
        CardNumber player2CardN1 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits player2CardS1 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 2: ");
        System.out.print("Enter Number: ");
        CardNumber player2CardN2 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits player2CardS2 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        Card player1Card1 = new Card(player1CardN1,player1CardS1);
        Card player1Card2 = new Card(player1CardN2,player1CardS2);
        Card player2Card1 = new Card(player2CardN1,player2CardS1);
        Card player2Card2 = new Card(player2CardN1,player2CardS2);
        List<Card> player1Cards = List.of(player1Card1,player1Card2);
        List<Card> player2Cards = List.of(player2Card1,player2Card2);
        Hand hand1 = new Hand(player1Cards);
        Hand hand2 = new Hand(player2Cards);

        System.out.println("Enter Table Cards: ");
        System.out.println("Card 1: ");
        System.out.print("Enter Number: ");
        CardNumber tableCardN1 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits tableCardS1 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 2: ");
        System.out.print("Enter Number: ");
        CardNumber tableCardN2 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits tableCardS2 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 3: ");
        System.out.print("Enter Number: ");
        CardNumber tableCardN3 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits tableCardS3 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 4: ");
        System.out.print("Enter Number: ");
        CardNumber tableCardN4 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits tableCardS4 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        System.out.println("Card 5: ");
        System.out.print("Enter Number: ");
        CardNumber tableCardN5 = CardNumber.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Suit: ");
        Suits tableCardS5 = Suits.valueOf(scanner.nextLine().toUpperCase()) ;

        Card tableCard1 = new Card(tableCardN1,tableCardS1);
        Card tableCard2 = new Card(tableCardN2,tableCardS2);
        Card tableCard3 = new Card(tableCardN3,tableCardS3);
        Card tableCard4 = new Card(tableCardN4,tableCardS4);
        Card tableCard5 = new Card(tableCardN5,tableCardS5);
        List<Card> tableCards = List.of(tableCard1,tableCard2,tableCard3,tableCard4,tableCard5);
        Table table = new Table(tableCards);
        Calculator calculator = new Calculator();
        calculator.compare(table, hand1, hand2);
    }

}



