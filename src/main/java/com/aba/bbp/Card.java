package com.aba.bbp;

public class Card {
    public CardRanks rank;
    public Suites suite;

    @Override
    public String toString() {
        String rankSymbol;
        switch (rank) {
            case ACE -> rankSymbol = "A";
            case KING -> rankSymbol = "K";
            case QUEEN -> rankSymbol = "Q";
            case JACK -> rankSymbol = "J";
            case TEN -> rankSymbol = "T";
            default -> rankSymbol = String.valueOf(rank.ordinal() + 2);
        }

        String suiteSymbol;
        switch (suite) {
            case HEARTS -> suiteSymbol = "♥";
            case DIAMONDS -> suiteSymbol = "♦";
            case CLUBS -> suiteSymbol = "♠";
            case SPADES -> suiteSymbol = "♣";
            default -> throw new IllegalStateException("Unexpected suite: " + suite);
        }

        return rankSymbol + suiteSymbol;
    }
}
