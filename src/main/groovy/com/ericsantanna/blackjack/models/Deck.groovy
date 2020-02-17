package com.ericsantanna.blackjack.models

import java.util.concurrent.ArrayBlockingQueue

class Deck {
    int size
    int cardCut
    boolean cardCutReached
    ArrayBlockingQueue<Card> cards

    Deck(int size, int cardCut) {
        this.size = size
        this.cardCut = cardCut
        this.cards = newShoe()
    }

    ArrayBlockingQueue<Card> newShoe() {
        List<Card> cards = []
        (1..size).each {
            cards.addAll(newDeck())
        }
        Collections.shuffle(cards)
        return new ArrayBlockingQueue<Card>(cards.size(), true, cards)
    }

    List<Card> newDeck() {
        List<Card> cards = []
        Card.Type.values().each { type ->
            Card.Suit.values().each { suit ->
                cards.add(new Card(type, suit))
            }
        }
        return cards
    }

    private Card poll() {
        def card = cards.poll()
        cardCutReached = cards.size() <= cardCut
        return card
    }

    Card get() {
        return poll()
    }

    List<Card> getHand() {
        def hand = []
        if(cardCutReached) {
            cards = newShoe()
            cardCutReached = false
        }
        def card = poll()
        hand << card
        card = poll()
        hand << card
        return hand
    }

    float calculateChance(Card.Type type) {
        def total = size * 52
        def available = cards.count { it.type == type }
        return (available / total)
    }

    float probabilityOfGoodCard(Player player) {
        def currentScore = player.sum()
        def maxCardValue = 21 - currentScore
        List<Card.Type> acceptableCards = []
        switch (maxCardValue) {
            case 0: return 0
            case 1: acceptableCards.addAll([Card.Type.ACE])
                break
            case 2: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO
                ])
                break
            case 3: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE
                ])
                break
            case 4: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR
                ])
                break
            case 5: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR, Card.Type.FIVE
                ])
                break
            case 6: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR, Card.Type.FIVE,
                    Card.Type.SIX
                ])
                break
            case 7: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR, Card.Type.FIVE,
                    Card.Type.SIX, Card.Type.SEVEN
                ])
                break
            case 8: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR, Card.Type.FIVE,
                    Card.Type.SIX, Card.Type.SEVEN, Card.Type.EIGHT
                ])
                break
            case 9: acceptableCards.addAll([
                    Card.Type.ACE, Card.Type.TWO, Card.Type.THREE, Card.Type.FOUR, Card.Type.FIVE,
                    Card.Type.SIX, Card.Type.SEVEN, Card.Type.EIGHT, Card.Type.NINE
                ])
                break
            default:
                return 1.0
        }

        float percentage
        acceptableCards.each { card ->
            percentage += calculateChance(card)
        }

        return percentage
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
