package com.reza.poker.services;

import com.reza.poker.models.Card;
import com.reza.poker.models.Hand;
import com.reza.poker.models.Table;

import java.util.List;

public interface ICalculator {
    Hand compare(Table table, Hand hand, Hand hand2);

    List<Card> checkRoyalFlush(List<Card> list);
    List<Card> checkStraightFlush(List<Card> list);
    List<Card> checkFourOfaKind(List<Card> list);
    List<Card> checkFullHouse(List<Card> list);
    List<Card> checkFlush(List<Card> list);
    List<Card> checkStraight(List<Card> list);
    List<Card> checkThreeOfaKind(List<Card> list);
    List<Card> checkTwoPairs(List<Card> list);
    List<Card> checkOnePair(List<Card> list);
}
