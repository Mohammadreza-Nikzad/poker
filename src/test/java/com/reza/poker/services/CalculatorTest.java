package com.reza.poker.services;

import com.reza.poker.models.Card;
import com.reza.poker.models.CardNumber;
import com.reza.poker.models.Deck;
import com.reza.poker.models.Suits;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    Calculator calculator = new Calculator();
    Deck deck = Deck.getInstance() ;


    @Test
    void testCheckRoyalFlush() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.CLUB),
                new Card(CardNumber.JACK,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.ACE,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.JACK,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkRoyalFlush(input).toArray());
    }

    @Test
    @DisplayName("Test Cards Uniqueness")
    void testCheckCardsUniqueness() {
        List<Card> table = new ArrayList<>(List.of(new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.KING,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.DIAMOND),
                new Card(CardNumber.ACE,Suits.HEART),
                new Card(CardNumber.FOUR,Suits.CLUB)));

        List<Card> p1 = new ArrayList<>(List.of(
                new Card(CardNumber.THREE,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.DIAMOND)));

        List<Card> p2 = new ArrayList<>(List.of(
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB)));

        List<Card> p3 = new ArrayList<>(List.of(
                new Card(CardNumber.TEN,Suits.DIAMOND),
                new Card(CardNumber.QUEEN,Suits.CLUB)));


        assertEquals(true,calculator.checkCardsUniqueness(table,p1,p2));
        assertAll(() -> assertEquals(true,calculator.checkCardsUniqueness(table,p1,p2)),
                () -> assertEquals(false,calculator.checkCardsUniqueness(table,p1,p3)));
    }

    @Test
    @DisplayName(" Test unique cards by number")
    void testUniqueCardNumbers() {
        List<Card> input = new ArrayList<>(List.of(new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.ACE,Suits.HEART),
                new Card(CardNumber.ACE,Suits.CLUB),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        assertEquals(3,calculator.uniqueCardNumbers(input).size());
    }

    @Test
    @DisplayName(" Card orders & Straight check")
    void testStraight_CardsInOrder() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.KING,Suits.HEART),
                new Card(CardNumber.KING,Suits.DIAMOND),
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.SPADE),
                new Card(CardNumber.JACK,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.NINE,Suits.CLUB)));

        List<Card> output = new ArrayList<>(List.of(
                new Card(CardNumber.KING,Suits.HEART),
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.JACK,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.NINE,Suits.CLUB)));

        Assertions.assertArrayEquals(output.toArray(),calculator.checkStraight(input).toArray());
    }

    @Test
    void testCheckStraightFlush() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.SPADE),
                new Card(CardNumber.JACK,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.SPADE),
                new Card(CardNumber.KING,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.DIAMOND)));
        List<Card> expectedOutput = new ArrayList<Card>(List.of(
                new Card(CardNumber.KING,Suits.SPADE),
                new Card(CardNumber.QUEEN,Suits.SPADE),
                new Card(CardNumber.JACK,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.SPADE)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkStraightFlush(input).toArray());
    }

    @Test
    void testCheckFourOfaKind() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.NINE,Suits.HEART),
                new Card(CardNumber.ACE,Suits.CLUB),
                new Card(CardNumber.NINE,Suits.DIAMOND),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.NINE,Suits.HEART),
                new Card(CardNumber.NINE,Suits.DIAMOND),
                new Card(CardNumber.ACE,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),
                calculator.checkFourOfaKind(input).toArray());

    }

    @Test
    void testCheckFullHouse() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.DIAMOND),
                new Card(CardNumber.NINE,Suits.DIAMOND)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.TEN,Suits.DIAMOND),
                new Card(CardNumber.NINE,Suits.SPADE),
                new Card(CardNumber.NINE,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),
                calculator.checkFullHouse(input).toArray());
    }

    @Test
    void testCheckFlush() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.JACK,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.JACK,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.NINE,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkFlush(input).toArray());
    }

    @Test
    void testCheckThreeOfaKind() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.TEN,Suits.SPADE),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.TEN,Suits.SPADE),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.KING,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkThreeOfaKind(input).toArray());
    }

    @Test
    void testCheckTwoPairs() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.THREE,Suits.SPADE),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.QUEEN,Suits.DIAMOND)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.QUEEN,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.DIAMOND),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.DIAMOND)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkTwoPairs(input).toArray());
    }

    @Test
    void testCheckOnePair() {
        List<Card> input = new ArrayList<>(List.of(
                new Card(CardNumber.NINE,Suits.CLUB),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.THREE,Suits.SPADE),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        List<Card> expectedOutput = new ArrayList<>(List.of(
                new Card(CardNumber.TEN,Suits.CLUB),
                new Card(CardNumber.TEN,Suits.HEART),
                new Card(CardNumber.ACE,Suits.DIAMOND),
                new Card(CardNumber.KING,Suits.CLUB),
                new Card(CardNumber.QUEEN,Suits.CLUB)));
        Assertions.assertArrayEquals(expectedOutput.toArray(),calculator.checkOnePair(input).toArray());
    }
    
}