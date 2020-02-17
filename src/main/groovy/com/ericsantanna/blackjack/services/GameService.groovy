package com.ericsantanna.blackjack.services

import com.ericsantanna.blackjack.models.Deck
import com.ericsantanna.blackjack.models.Player

class GameService {
//    final Deck deck = new Deck(8, (52 * 4))
    final Deck deck = new Deck(8, 30)
    Player house
    Player guest

    GameService() {
        guest = new Player(Player.Type.GUEST, deck.getHand())
        house = new Player(Player.Type.HOUSE, deck.getHand())
    }

    private void newGame() {
        guest.cards = deck.getHand()
        house.cards = deck.getHand()
    }

    void hit(Player player) {
        player.cards << deck.get()
    }

    void stand(Player player) {
//        println player.cards
    }

    void play() {
        newGame()
        println "Game started:"
        show()
//        println()
//        println "Guest turn:"
//        println ">>> Guest hand: ${guest.getCards()} (${guest.sum()})"
//        println ">>> Probability of good card: " + deck.probabilityOfGoodCard(guest)
        while (guest.sum() < 21) {
            def probabilityOfGoodCard = deck.probabilityOfGoodCard(guest)
            if(probabilityOfGoodCard >= 0.51) {
                hit(guest)
            } else {
                stand(guest)
                break
            }
            show()
        }
//        println()
//        println "House turn:"
        while (house.sum() < 21) {
            if(house.sum() < 17) {
                hit(house)
            } else {
                stand(house)
                break
            }
//            show()
        }
//        println()
        println "Finished:"
        show()
        showFinalResult()
    }

    void show() {
        println "-------------------------------------------------------------------------------"
        println "Deck remaining: ${deck.cards.size()}"
        println "House hand: ${house.getCards()} (${house.sum()})"
        println "Guest hand: ${guest.getCards()} (${guest.sum()})"
        println "Probability of good card: " + deck.probabilityOfGoodCard(guest)
        println "-------------------------------------------------------------------------------"
    }

    void showFinalResult() {
        def houseScore = house.sum()
        def guestScore = guest.sum()

        if(guestScore > 21) {
            house.won()
            guest.lost()
            println "> Guest busted"
            println "-------------------------------------------------------------------------------"
            println "**House wins**"
        } else if(houseScore > 21) {
            guest.won()
            house.lost()
            println "> House busted"
            println "-------------------------------------------------------------------------------"
            println "**Guest wins**"
        } else if(houseScore > guestScore) {
            house.won()
            guest.lost()
            println "-------------------------------------------------------------------------------"
            println "**House wins**"
        } else if(guestScore > houseScore) {
            guest.won()
            house.lost()
            println "-------------------------------------------------------------------------------"
            println "**Guest wins**"
        } else {
            house.draw()
            guest.draw()
            println "-------------------------------------------------------------------------------"
            println "**Draw**"
        }
        println "-------------------------------------------------------------------------------"
    }
}
