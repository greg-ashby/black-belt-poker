package com.aba.bbp;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        for(Suites suite : Suites.values()) {
            for(CardRanks rank : CardRanks.values()) {
                Card card = new Card();
                card.rank = rank;
                card.suite = suite;
                cards.add(card);
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
