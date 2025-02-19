package com.aba.bbp.model;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Hand implements Comparable<Hand> {

  private final HandRank handRank;
  private final List<CardRank> kickers;
  private final List<Card> holeCards;

  @Override
  public String toString() {
	return handRank.toString() + " " + kickers.toString();
  }

  public String getRankKey() {
	String kickerCategory =
		switch (kickers.get(0)) {
		  case TWO, THREE, FOUR, FIVE, SIX -> kickerCategory = "1L";
		  case SEVEN, EIGHT, NINE, TEN -> kickerCategory = "2M";
		  case JACK, QUEEN, KING, ACE -> kickerCategory = "3H";
		};
	return handRank.toString() + "-" + kickerCategory;
  }

  public String getHoleKey() {
	Card card1 = holeCards.get(0);
	Card card2 = holeCards.get(1);

	if (card1.compareTo(card2) < 0) {
	  Card temp = card1;
	  card1 = card2;
	  card2 = temp;
	}

	return (card1.suite() == card2.suite() ? "S"
		: "O") + card1.rank().toString() + card2.rank().toString();
  }

  @Override
  public int compareTo(Hand otherHand) {
	if (this.handRank.ordinal() > otherHand.handRank.ordinal()) {
	  return 1;
	} else if (this.handRank.ordinal() < otherHand.handRank.ordinal()) {
	  return -1;
	}

	for (int i = 0; i < this.kickers.size(); i++) {
	  if (this.kickers.get(i).ordinal() > otherHand.kickers.get(i).ordinal()) {
		return 1;
	  } else if (this.kickers.get(i).ordinal() < otherHand.kickers.get(i).ordinal()) {
		return -1;
	  }
	}

	return 0;
  }
}
