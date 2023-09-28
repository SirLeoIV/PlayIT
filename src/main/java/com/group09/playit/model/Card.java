package com.group09.playit.model;

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public enum Suit {
        CLUBS,
        DIAMONDS,
        SPADES,
        HEARTS
    }
    
    public enum Rank {
        ACE(14),
        KING(13),
        QUEEN(12),
        JACK(11),
        TEN(10),
        NINE(9),
        EIGHT(8),
        SEVEN(7),
        SIX(6),
        FIVE(5),
        FOUR(4),
        THREE(3),
        TWO(2);

        final int points;
        
        Rank(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }
    }

    public int getValue() {
        if (suit == Suit.HEARTS) return 1;
        if (suit == Suit.SPADES && rank == Rank.QUEEN) return 13;
        return 0;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    // to String method
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

}
