package com.aba.bbp;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;
import java.util.ArrayList;
import java.util.Collections;
import lombok.Getter;

@Getter
public class Deck {
  
  private final ArrayList<Card> cards = new ArrayList<>();

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
}
