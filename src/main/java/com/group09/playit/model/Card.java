package com.group09.playit.model;

/**
 * The type Card.
 */
public record Card(Suit suit, Rank rank) {

    /**
     * The possible suits of the cards.
     */
    public enum Suit {
        /**
         * Clubs suit.
         */
        CLUBS,
        /**
         * Diamonds suit.
         */
        DIAMONDS,
        /**
         * Spades suit.
         */
        SPADES,
        /**
         * Hearts suit.
         */
        HEARTS
    }

    /**
     * The possible ranks of the cards.
     */
    public enum Rank {
        /**
         * Ace rank.
         */
        ACE(14),
        /**
         * King rank.
         */
        KING(13),
        /**
         * Queen rank.
         */
        QUEEN(12),
        /**
         * Jack rank.
         */
        JACK(11),
        /**
         * Ten rank.
         */
        TEN(10),
        /**
         * Nine rank.
         */
        NINE(9),
        /**
         * Eight rank.
         */
        EIGHT(8),
        /**
         * Seven rank.
         */
        SEVEN(7),
        /**
         * Six rank.
         */
        SIX(6),
        /**
         * Five rank.
         */
        FIVE(5),
        /**
         * Four rank.
         */
        FOUR(4),
        /**
         * Three rank.
         */
        THREE(3),
        /**
         * Two rank.
         */
        TWO(2);

        /**
         * The Points.
         */
        final int points;

        Rank(int points) {
            this.points = points;
        }

        /**
         * Gets points.
         *
         * @return the points
         */
        public int getPoints() {
            return points;
        }
    }

    /**
     * Get the value of the card.
     * The value of a heart is 1 point.
     * The value of the queen of spades is 13 points.
     * All other cards are worth 0 points.
     *
     * @return value of the card
     */
    public int getValue() {
        if (suit == Suit.HEARTS) return 1;
        if (suit == Suit.SPADES && rank == Rank.QUEEN) return 13;
        return 0;
    }

    /**
     * Gets suit.
     *
     * @return the suit
     */
    @Override
    public Suit suit() {
        return suit;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    @Override
    public Rank rank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Check if two cards are equal.
     * Two cards are equal if they have the same suit and rank.
     *
     * @param o other card
     * @return true if the cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

}
