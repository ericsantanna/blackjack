package com.ericsantanna.blackjack.services

import com.ericsantanna.blackjack.models.Card
import com.ericsantanna.blackjack.models.Player
import spock.lang.Specification

class GameServiceTest extends Specification {
    def "hit()"() {
        given:
        def gameService = new GameService()
        def initialDeckSize = gameService.deck.cards.size()
        println gameService.deck
        (1..10).each {
            gameService.hit(gameService.guest)
        }
        assert gameService.deck.cards.size() == (initialDeckSize - 10)
    }

    def "sum()"() {
        expect:
        new Player(Player.Type.GUEST, a).sum() == b
        where:
        a << [
                [new Card(Card.Type.ACE, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.ACE, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.NINE, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.NINE, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS)],
                [new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.ACE, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS), new Card(Card.Type.TEN, Card.Suit.CLUBS)]
        ]
        b << [11, 12, 21, 20, 21, 12, 21, 22]
    }

    def "play()"() {
        given:
        def gameService = new GameService()
        (1..1000).each {
            gameService.play()
        }
        println "-------------------------------------------------------------------------------"
        println "House: ${gameService.house.games} games, ${gameService.house.wins} wins, ${gameService.house.losses} losses, ${gameService.house.draws} draws"
        println "Guest: ${gameService.guest.games} games, ${gameService.guest.wins} wins, ${gameService.guest.losses} losses, ${gameService.guest.draws} draws"
        println "Ratio: ${winRatio(gameService.guest)}"
    }

    float winRatio(Player player) {
        player.wins / (player.games - player.draws)
    }
}
