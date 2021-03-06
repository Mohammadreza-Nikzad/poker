package com.reza.poker.models;

/*
* @author Mohammadreza Nikzad
* {@code
* Each hand will be assigned a winning possibility in poker game
* in order to be compared with other hands.
* }
*
* */

public enum Rank {
    ROYAL_FLUSH(10),
    STRAIGHT_FLUSH(9),
    FOUR_OF_A_KIND(8),
    FULL_HOUSE(7),
    FLUSH(6),
    STRAIGHT(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    PAIR(2),
    HIGH_CARD(1);
    private int possibilities;

    public int getPossibilities() {
        return possibilities;
    }

    Rank(int possibilities) {
        this.possibilities = possibilities;
    }
}
