package com.reza.poker.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Card> deskCards = new ArrayList<Card>(5);

    public Table(List<Card> deskCards) {
        this.deskCards = deskCards;
    }

    public List<Card> getDeskCards() {
        return deskCards;
    }

    public void setDeskCards(List<Card> deskCards) {
        this.deskCards = deskCards;
    }
}
