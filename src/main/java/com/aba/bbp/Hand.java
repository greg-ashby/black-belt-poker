package com.aba.bbp;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.List;

public class Hand implements Comparable<Hand> {

  public HandRank handRank;
  public List<CardRank> kickers;

  public Hand(HandRank handRank, List<CardRank> kickers) {
	this.handRank = handRank;
	this.kickers = kickers;
  }

  @Override
  public String toString() {
	return handRank.toString() + " " + kickers.toString();
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
