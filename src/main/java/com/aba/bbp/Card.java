package com.aba.bbp;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;

public class Card implements Comparable<Card> {
    public CardRank rank;
    public Suite suite;

    public Card() {
    }

    public Card(CardRank rank, Suite suite) {
        this.rank = rank;
        this.suite = suite;
    }

    public CardRank getRankOrdinal() {
        return this.rank;
    }

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
            default ->
                    throw new IllegalStateException("Unexpected suite: " + suite);
        }

        return rankSymbol + suiteSymbol;
    }

    @Override
    public int compareTo(Card card) {
        return rank.ordinal() - card.rank.ordinal();
    }
}
