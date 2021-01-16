package com.reza.poker.models;

/*
* {@code
* each card has a card number and a suit
* Cards number starts from 2 to ACE which has 14 value
*
* }
* */
public enum CardNumber {
    ACE(14),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    private int number;

    CardNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


}
