package com.reza.poker.models;

import java.util.Comparator;
import java.util.Objects;

/*
* {@code
* A card has a number and a suit
* two comparator to compare each card by their number and suits
* }
*
* */
public class Card {
    private CardNumber cardNumber;
    private Suits suits;

    public Card(CardNumber cardNumber, Suits suits) {
        this.cardNumber = cardNumber;
        this.suits = suits;
    }

    public Card(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Card(Suits suits) {
        this.suits = suits;
    }

    public Card() {

    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Suits getSuits() {
        return suits;
    }

    public void setSuits(Suits suits) {
        this.suits = suits;
    }


    /*Comparator for sorting the list of cards by cards suit*/
    public static Comparator<Card> cardComparatorSuit = new Comparator<Card>() {

        public int compare(Card c1, Card c2) {
            int cardSuit1 = c1.getSuits().getType();
            int cardSuit2 = c2.getSuits().getType();

            //ascending order
            return cardSuit1-cardSuit2;

        }
    };

    /*Comparator for sorting the list of cards by cards number*/
    public static Comparator<Card> cardComparatorNumber = new Comparator<Card>() {

        public int compare(Card c1, Card c2) {
            int cardNumber1 = c1.getCardNumber().getNumber();
            int cardNumber2 = c2.getCardNumber().getNumber();

            //ascending order
            return cardNumber2-cardNumber1;

        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNumber == card.cardNumber &&
                suits == card.suits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, suits);
    }
}
