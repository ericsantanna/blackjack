package com.ericsantanna.blackjack.models

class Card {
    enum Type {
        ACE("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K")

        private String value;

        Type(String value) {
            this.value = value
        }
    }

    enum Suit {
        SPADES("♠"),
        HEARTS("♥"),
        DIAMONDS("♦"),
        CLUBS("♣")

        private String value;

        Suit(String value) {
            this.value = value
        }
    }

    Type type
    Suit suit

    Card(Type type, Suit suit) {
        this.type = type
        this.suit = suit
    }

    @Override
    public String toString() {
        return String.format("%s%s", type.value, suit.value);
    }
}
