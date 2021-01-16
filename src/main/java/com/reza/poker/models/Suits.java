package com.reza.poker.models;
/*
* @author Mohammadreza Nikzad
* {@code
* there are three suits for each card [SPADE, HEART, DIAMOND, CLUB]
* }
* */
public enum Suits {
    SPADE(1),
    HEART(2),
    DIAMOND(3),
    CLUB(4);

    private int type;

    Suits(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
