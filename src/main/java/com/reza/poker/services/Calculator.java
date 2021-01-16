package com.reza.poker.services;


import com.reza.poker.models.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/*
* @{code
* All the poker rules are applied here in separated methods
* two players start with 2 Cards and finally they will get 5 best cards
* players will be compared by 5 best cards
* compare method is used to compare to hands on finds the winner
* }
* */

public class Calculator implements ICalculator, BinaryOperator<Hand> {

    @Override
    public Hand compare(Table table, Hand hand1, Hand hand2) {

        if(checkCardsUniqueness(table.getDeskCards(), hand1.getCards(), hand2.getCards())){
            hand1.setRank(getHighestRank(table, hand1));
            hand2.setRank(getHighestRank(table, hand2));
            Hand winner = null;
            winner = apply(hand1, hand2);
            System.out.println(winner.getRank());
            System.out.println("With following cards: ");
//            System.out.println("Cards: "+ winner.getCards().size());
            if(winner!=null) {
                for (Card c : winner.getCards()
                ) {
                    System.out.println(c.getCardNumber().name() + " " + c.getSuits().name());
                }
            }return winner ;

        }else{
            System.out.println("It is supposed to enter Unique Cards");
            return null;
        }
    }

    public Rank getHighestRank(Table table, Hand hand){
        List<Card> tableAndHand = new ArrayList<Card>(7);
        tableAndHand.addAll(table.getDeskCards());
        tableAndHand.addAll(hand.getCards());
        //ARRAY[ RoyalFlush, StraightFlush, ....] foreach do winning
        //    if('check'+type(tableAndHand))
        if(checkRoyalFlush(tableAndHand)!=null){
            hand.setCards(checkRoyalFlush(tableAndHand));
            return Rank.ROYAL_FLUSH;

        }else if(checkStraightFlush(tableAndHand)!=null){
            hand.setCards(checkStraightFlush(tableAndHand));
            return Rank.STRAIGHT_FLUSH;

        }else if(checkFourOfaKind(tableAndHand)!=null){
            hand.setCards(checkFourOfaKind(tableAndHand));
            return Rank.FOUR_OF_A_KIND;

        }else if(checkFullHouse(tableAndHand)!=null){
            hand.setCards(checkFullHouse(tableAndHand));
            return Rank.FULL_HOUSE;

        }else if(checkFlush(tableAndHand)!=null){
            hand.setCards(checkFlush(tableAndHand));
            return Rank.FLUSH;

        }else if(checkStraight(tableAndHand)!=null){
            hand.setCards(checkStraight(tableAndHand));
            return Rank.STRAIGHT;

        }else if(checkThreeOfaKind(tableAndHand)!=null){
            hand.setCards(checkThreeOfaKind(tableAndHand));
            return Rank.THREE_OF_A_KIND;

        }else if(checkTwoPairs(tableAndHand)!=null){
            hand.setCards(checkTwoPairs(tableAndHand));
            return Rank.TWO_PAIR;

        }else if(checkOnePair(tableAndHand)!=null){
            hand.setCards(checkOnePair(tableAndHand));
            return Rank.PAIR;
        }
        hand.setCards(checkHighCards(tableAndHand));
        return Rank.HIGH_CARD;
    }

    //tested
    //possibility = 10 - A-K-Q-J-10 same suit
    @Override
    public List<Card> checkRoyalFlush(List<Card> tableAndHand) {

        if (checkFlush(tableAndHand)!=null){
            List<Card> sameSuitCards = checkFlush(tableAndHand) ;
            List<Card> list ;
            Collections.sort(sameSuitCards,Card.cardComparatorNumber);
            if(sameSuitCards.get(0).getCardNumber().getNumber()==14 &&
                    sameSuitCards.get(1).getCardNumber().getNumber()==13 &&
                    sameSuitCards.get(2).getCardNumber().getNumber()==12 &&
                    sameSuitCards.get(3).getCardNumber().getNumber()==11 &&
                    sameSuitCards.get(4).getCardNumber().getNumber()==10
            ){
                list = List.of(sameSuitCards.get(0),
                        sameSuitCards.get(1),
                        sameSuitCards.get(2),
                        sameSuitCards.get(3),
                        sameSuitCards.get(4));
                return list;
            } else return null;
        }else return null;
    }

    //tested
    //possibility = 9 - same suit - in order
    @Override
    public List<Card> checkStraightFlush(List<Card> tableAndHand){
        List<Card> list = new ArrayList<Card>(5);
        if (checkFlush(tableAndHand)!=null) {
            Collections.sort(tableAndHand,Card.cardComparatorNumber);
            for (int i = 0; i < tableAndHand.size() ; i++) {
                if(checkFlush(tableAndHand).get(0).getSuits().
                        equals(tableAndHand.get(i).getSuits()) && list.size()<5){
                    list.add(tableAndHand.get(i));
                }
            }
            return list;
        }
        return null;
    }

    //tested
    //possibility = 8 - 4 same suit
    @Override
    public List<Card> checkFourOfaKind(List<Card> tableAndHand){//returns other max card
        List<CardNumber> cardNumbers = takePairedCardsNumbers(tableAndHand);
        Collections.sort(tableAndHand,Card.cardComparatorNumber);
        List<Card> list = new ArrayList<Card>();
        if(cardNumbers.get(0)!=null){
            list.addAll(tableAndHand.stream().
                    filter(c -> c.getCardNumber().equals(cardNumbers.get(0))).
                    collect(Collectors.toList()));
            for (int i = 0; i < tableAndHand.size() ; i++) {
                if(tableAndHand.get(i).getCardNumber().getNumber()!=cardNumbers.get(0).getNumber())
                {
                    list.add(4, tableAndHand.get(i));
                    break;
                }
            }
            return list;
        }
        return null;
    }

    //tested
    //possibility = 7 -  3 of a same card number and a paired
    @Override
    public List<Card> checkFullHouse(List<Card> tableAndHand){
        List<CardNumber> cardNumbers = takePairedCardsNumbers(tableAndHand);
        List<Card> list = new ArrayList<>();
        if(cardNumbers.get(1)!=null &&
                cardNumbers.get(2)!=null){
            list.addAll(tableAndHand.stream().
                    filter(cr -> cr.getCardNumber().equals(cardNumbers.get(1))).
                    collect(Collectors.toList()));
            list.addAll(tableAndHand.stream().
                    filter(cr -> cr.getCardNumber().equals(cardNumbers.get(2))).
                    collect(Collectors.toList()));
            return list.subList(0,5);
        }
        return null;
    }

    //tested
    //possibility = 6 - checkFlush returns a list of 5 top similar cards or null
    @Override
    public List<Card> checkFlush(List<Card> tableAndHand){
        int numberOfSameSuits=0;
        List<Card> list = new LinkedList<>();
        Collections.sort(tableAndHand,Card.cardComparatorNumber);
        for (Suits s : Suits.values()) {
            numberOfSameSuits= (int) tableAndHand.stream()
                    .filter(c -> c.getSuits().equals(s))
                    .count();
            if(numberOfSameSuits>=5){
                for (int i = 0; i < tableAndHand.size() && list.size()<=5 ; i++) {
                    if(tableAndHand.get(i).getSuits().equals(s)){
                        list.add(tableAndHand.get(i));
                    }

                }
                return list;
            }
        }
        return  null;
    }

    //tested
    //possibility = 5 - Straight
    @Override
    public List<Card> checkStraight(List<Card> tableAndHand){
        return cardsInOrder(tableAndHand);
    }


    //possibility = 4 - Three of a kind
    @Override
    public List<Card> checkThreeOfaKind(List<Card> tableAndHand){
        List<CardNumber> cardNumbers = takePairedCardsNumbers(tableAndHand);
        List<Card> list = new ArrayList<Card>();
        int count =0;
        Collections.sort(tableAndHand, Card.cardComparatorNumber);
        if(cardNumbers.get(1)!=null &&
                cardNumbers.get(0)==null &&
                cardNumbers.get(2)==null){
            list.addAll(tableAndHand.stream().
                    filter(cr -> cr.getCardNumber().equals(cardNumbers.get(1))).
                    collect(Collectors.toList()));
            for (int i =0 ; i<tableAndHand.size() && count<2; i++
            ) {
                if(!tableAndHand.get(i).getCardNumber().equals(cardNumbers.get(1))){
                    list.add(tableAndHand.get(i));
                    count ++;
                }
            }
            return list;
        }
        return null;
    }

    //possibility = 3 - 2 pair
    @Override
    public List<Card> checkTwoPairs(List<Card> tableAndHand){
        List<CardNumber> cardNumbers = takePairedCardsNumbers(tableAndHand);
        Collections.sort(tableAndHand,Card.cardComparatorNumber);
        List<Card> list = new ArrayList<Card>();
        int count =0;
        if(cardNumbers.get(2)!=null && cardNumbers.get(3)!=null){
            list.addAll(tableAndHand.stream().
                    filter(c -> c.getCardNumber().getNumber()==cardNumbers.get(2).getNumber()).
                    collect(Collectors.toList()));
            list.addAll(tableAndHand.stream().
                    filter(c -> c.getCardNumber().getNumber()==cardNumbers.get(3).getNumber()).
                    collect(Collectors.toList()));
            for (int i = 0; i < tableAndHand.size() ; i++) {
                if(count <1 &&
                        tableAndHand.get(i).getCardNumber().getNumber()!=cardNumbers.get(2).getNumber() &&
                        tableAndHand.get(i).getCardNumber().getNumber()!=cardNumbers.get(3).getNumber()){
                    list.add(4, tableAndHand.get(i));
                    count ++;
                }
            }
            return list;
        }
        return null;
    }

    //possibility = 2 - one Pair
    @Override
    public List<Card> checkOnePair(List<Card> tableAndHand){
        List<CardNumber> cardNumbers = takePairedCardsNumbers(tableAndHand);
        Collections.sort(tableAndHand,Card.cardComparatorNumber);
        List<Card> list = new ArrayList<Card>();
        int count =0;
        if(cardNumbers.get(2)!=null ) {
            list.addAll(tableAndHand.stream().
                    filter(c -> c.getCardNumber().getNumber() == cardNumbers.get(2).getNumber()).
                    collect(Collectors.toList()));

            for (int i = 0; i < tableAndHand.size(); i++) {
                if (count < 3 &&
                        tableAndHand.get(i).getCardNumber().getNumber() != cardNumbers.get(2).getNumber()) {
                    list.add(tableAndHand.get(i));
                    count++;
                }
            }
            return list;
        }
        return null;
    }

    //possibility = 1 - High Card
    public List<Card> checkHighCards(List<Card> tableAndHand){
        Collections.sort(tableAndHand, Card.cardComparatorNumber);
        List<Card> list = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            list.add(tableAndHand.get(i));
        }
        return list;
    }


    //tested
    //list of top 5 ordered card
    public List<Card> cardsInOrder(List<Card> tableAndHand){

        List<Card> card = uniqueCardNumbers(tableAndHand) ;
        List<Card> orderedCard = new ArrayList<Card>() ;
        int count;
        if (card.size()>=5) {
            Collections.sort(card, Card.cardComparatorNumber);
            for (int i = 0 ; card.size()>=5 && i < card.size()-4 ; i++) {
                count =0;
                orderedCard.clear();
                for (int j = i; j < i+4 ; j++) {
                    if(card.get(j).getCardNumber().getNumber()-
                            card.get(j+1).getCardNumber().getNumber()==1){
                        orderedCard.add(card.get(j));
                        count++;
                    }
                    if(count == 4){
                        orderedCard.add(card.get(j+1));
                        return orderedCard;
                    }
                }
            }
            if(card.get(0).getCardNumber().getNumber()==14 &&
                    card.get(card.size()-1).getCardNumber().getNumber()==2 &&
                    card.get(card.size()-2).getCardNumber().getNumber()==3 &&
                    card.get(card.size()-3).getCardNumber().getNumber()==4 &&
                    card.get(card.size()-4).getCardNumber().getNumber()==5){
                orderedCard.clear();
                orderedCard.add(card.get(card.size()-4));
                orderedCard.add(card.get(card.size()-3));
                orderedCard.add(card.get(card.size()-2));
                orderedCard.add(card.get(card.size()-1));
                orderedCard.add(card.get(0));
                return orderedCard;
            }
        }
        return null;
    }

    //tested
    //list of non-repeated cards number
    public List<Card> uniqueCardNumbers(List<Card> tableAndHand){
        List<Card> uniqueCardN = new ArrayList<Card>();
        uniqueCardN = tableAndHand.stream()
                .filter(distinctByKey(p -> p.getCardNumber().getNumber()))
                .collect(Collectors.toList());
        return uniqueCardN;
    }

    public <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //returns a list of cardNumbers
    // first = 4 cards of same number,
    // second = 3 cards of same number,
    // third =  paired(maximum),
    // forth =  paired(minimum).
    public List<CardNumber> takePairedCardsNumbers(List<Card> tableAndHand){
        Map<CardNumber, Integer> map = similarCardsNumberIter(tableAndHand);
        Collections.sort(tableAndHand, Card.cardComparatorNumber);
        CardNumber sameCardN_4 = null;
        CardNumber sameCardN_3 = null;
        CardNumber sameCardN_2a = null;
        CardNumber sameCardN_2b = null;
        CardNumber tempCard;
        for (CardNumber cardNumber:map.keySet()
        ) {
            if(map.get(cardNumber)==4) {
                sameCardN_4=cardNumber;
            }

            if(map.get(cardNumber)==3) {
                if(sameCardN_3==null){
                    sameCardN_3 = cardNumber;
                }else if(sameCardN_3!=null){
                    if(sameCardN_2a == null){
                        if(sameCardN_3.getNumber()>=cardNumber.getNumber()){
                            sameCardN_2a = cardNumber;
                        }else if(sameCardN_3.getNumber()<cardNumber.getNumber()){
                            tempCard = sameCardN_3;
                            sameCardN_3 = cardNumber;
                            sameCardN_2a = tempCard;
                        }
                    }else if(sameCardN_2a != null){//definitely sameCardN_2b == null

                        if(sameCardN_3.getNumber()>=cardNumber.getNumber()){
                            if(sameCardN_2a.getNumber()>=cardNumber.getNumber()){
                                sameCardN_2b = cardNumber;

                            }else if(sameCardN_2a.getNumber()<cardNumber.getNumber()){
                                tempCard = sameCardN_2a;
                                sameCardN_2a = cardNumber;
                                sameCardN_2b = tempCard;
                            }

                        }else if(sameCardN_3.getNumber()<cardNumber.getNumber()){
                            tempCard = sameCardN_3;
                            sameCardN_3 = cardNumber;
                            cardNumber = tempCard;

                            if(sameCardN_2a.getNumber()>=cardNumber.getNumber()){
                                sameCardN_2b = cardNumber;
                            }else if(sameCardN_2a.getNumber()<cardNumber.getNumber()){
                                sameCardN_2b =  sameCardN_2a;
                                sameCardN_2a = cardNumber;
                            }
                        }
                    }
                }

            }










            if(map.get(cardNumber)==2){
                if(sameCardN_2a == null ){
                    sameCardN_2a = cardNumber;
                }else if(sameCardN_2a != null){
                    if(sameCardN_2b==null){
                        if(cardNumber.getNumber()>=sameCardN_2a.getNumber()){
                            tempCard = sameCardN_2a;
                            sameCardN_2a = cardNumber;
                            sameCardN_2b = tempCard;
                        }else if(cardNumber.getNumber()<sameCardN_2a.getNumber()){
                            sameCardN_2b = cardNumber;
                        }
                    }else if(sameCardN_2b!= null){
                        if(cardNumber.getNumber()>sameCardN_2a.getNumber()){
                            tempCard = sameCardN_2a;
                            sameCardN_2a = cardNumber;
                            sameCardN_2b = tempCard;
                        }else if(sameCardN_2b.getNumber()<cardNumber.getNumber() &&
                                cardNumber.getNumber()<sameCardN_2a.getNumber()){
                            sameCardN_2b = cardNumber;
                        }
                    }
                }
            }
        }
        List<CardNumber> list = new LinkedList<>();
        list.add(sameCardN_4);
        list.add(sameCardN_3);
        list.add(sameCardN_2a);
        list.add(sameCardN_2b);

        return list;
    }

    // it returns a map CardNumber and the number of iteration in the hand
    public Map<CardNumber,Integer> similarCardsNumberIter(List<Card> tableAndHand){
        Map<CardNumber, Integer> map = new HashMap<>();
        for (Card c: tableAndHand
        ) {
            if(map.containsKey(c.getCardNumber())){//add to the number of cards
                map.replace(c.getCardNumber(),
                        map.get(c.getCardNumber()),
                        map.get(c.getCardNumber())+1);
            }else{
                map.put(c.getCardNumber(),1);
            }
        }
        return map;
    }

    @Override
    public Hand apply(Hand hand, Hand hand2) {
        int comp = hand.getRank().getPossibilities()
                - hand2.getRank().getPossibilities();
        if(comp>0){
            System.out.println("Player 1 is the Winner");
            return hand;
        }else if(comp<0){
            System.out.println("Player 2 is the Winner");
            return hand2;
        }else {
            for (int i = 0; i < 5; i++) {
                if (hand.getCards().get(i).getCardNumber().getNumber() -
                        hand2.getCards().get(i).getCardNumber().getNumber() > 0) {
                    System.out.println("Player 1 is the Winner");
                    return hand;
                } else if (hand.getCards().get(i).getCardNumber().getNumber() -
                        hand2.getCards().get(i).getCardNumber().getNumber() < 0) {
                    System.out.println("Player 2 is the Winner");
                    return hand2;
                }

            }
            System.out.println("Split money between player 1 and 2");
            return null;
        }
    }

    //tested
    public boolean checkCardsUniqueness(List<Card> table,List<Card> person1, List<Card> person2){
        List<Card> allCards =new ArrayList<>();
        allCards.addAll(table);
        allCards.addAll(person1);
        allCards.addAll(person2);
        for (int i = 0; i<allCards.size(); i++) {
            for (int j =i+1; j< allCards.size(); j++) {
                //allCards.get(j).getCardNumber().getNumber()== allCards.get(i).getCardNumber().getNumber() &&
                //                        allCards.get(j).getSuits().name().equals(allCards.get(i).getSuits().name())
                //if()
                if(allCards.get(j).equals(allCards.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
}
