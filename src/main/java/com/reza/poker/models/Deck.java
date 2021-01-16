package com.reza.poker.models;

import java.util.ArrayList;
import java.util.List;

/*
* @author Mohammadreza Nikzad
* {@code
* Singleton class for creating a unique deck
* (this class is going to be used in complete Poker web application)
* }
*
* */

public class Deck {
    private static Deck deck;
    private List<Card> deckCards;

    private Deck() {
        deckCards = new ArrayList<>(52);
        for (Suits s : Suits.values()
        ) {
            for (CardNumber c: CardNumber.values()
            ) {
                deckCards.add(new Card(c,s));
            }
        }
    }

    public static Deck getInstance(){
        if(deck == null){
            deck = new Deck();
            return deck;
        }
        return deck;
    }

    public boolean pop(Card c){
        return deckCards.remove(c);
    }
}
