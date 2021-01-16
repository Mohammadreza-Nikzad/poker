package com.reza.poker.models;

import java.util.List;
/*
* {@code
* each hand consists of 5 best cards of player and table cards (5 of 7)
*  }
* */

public class Hand {
    private List<Card> cards;
    private Rank rank;

    public Hand(CardNumber cardNumber, Suits suits) {

    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

}
