package com.ericsantanna.blackjack.models

import spock.lang.Specification

class DeckTest extends Specification {
    def "Initial deck is good"() {
        given:
        (1..8).each { size ->
            def deck = new Deck(size)
            println deck
            assert deck.cards.size() == (size * 52)
        }
    }

    def "calculateChance()"() {
        given:
        def deck = new Deck(8, (52 * 4))
        def percentage = deck.calculateChance(Card.Type.ACE)
        println Card.Type.ACE.name() + " ratio = $percentage"
    }

    def "probabilityOfGoodCard()"() {
        given:
        def deck = new Deck(8, (52 * 4))
        List<Card> hand = [new Card(Card.Type.TWO, Card.Suit.CLUBS)]
        def player = new Player(Player.Type.GUEST, hand)
        def probabilityOfGoodCard = deck.probabilityOfGoodCard(player)
        println "Guest hand: ${player.getCards()} (${player.sum()})"
        println "Probability of a good card: $probabilityOfGoodCard"
    }
}
