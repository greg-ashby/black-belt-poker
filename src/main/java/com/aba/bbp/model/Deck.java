package com.aba.bbp.model;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

  private final ArrayList<Card> cards = new ArrayList<>();
  private final ArrayList<Card> usedCards = new ArrayList<>();

  public Deck() {
	for (Suite suite : Suite.values()) {
	  for (CardRank rank : CardRank.values()) {
		Card card = new Card(rank, suite);
		cards.add(card);
	  }
	}
  }

  public void shuffle() {
	Collections.shuffle(cards);
  }

  @Override
  public String toString() {
	return cards.toString();
  }

  public Card drawCard() {
	Card card = cards.remove(0);
	usedCards.add(card);
	return card;
  }

  public Card burnCard() {
	return drawCard();
  }

  public void reset() {
	cards.addAll(usedCards);
	usedCards.clear();
	shuffle();
  }
}
