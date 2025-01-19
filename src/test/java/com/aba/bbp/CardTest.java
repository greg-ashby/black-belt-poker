package com.aba.bbp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    @Test
    void shouldPrintKingOfHearts() {
        Card card = new Card();
        card.rank = CardRanks.KING;
        card.suite = Suites.HEARTS;
        assertEquals("K♥", card.toString(), "Card toString should format rank and suit correctly");
    }
}
