package com.ericsantanna.blackjack.models

class Player {
    enum Type {
        HOUSE,
        GUEST
    }

    Type type
    List<Card> cards
    long games
    long wins
    long losses
    long draws

    Player(Type type, List<Card> cards) {
        this.type = type
        this.cards = cards
    }

    int sum() {
        def sum = cards.sum {
            switch (it.type) {
                case Card.Type.TWO: return 2
                case Card.Type.THREE: return 3
                case Card.Type.FOUR: return 4
                case Card.Type.FIVE: return 5
                case Card.Type.SIX: return 6
                case Card.Type.SEVEN: return 7
                case Card.Type.EIGHT: return 8
                case Card.Type.NINE: return 9
                case Card.Type.TEN: return 10
                case Card.Type.JACK: return 10
                case Card.Type.QUEEN: return 10
                case Card.Type.KING: return 10
                default: 0
            }
        } as int
        def aces = cards.findAll { it.type == Card.Type.ACE }
        aces.each {
            sum += sum <= (11 - aces.size()) ? 11 : 1
        }
        return sum
    }

    void won() {
        games++
        wins++
    }

    void lost() {
        games++
        losses++
    }

    void draw() {
        games++
        draws++
    }
}
