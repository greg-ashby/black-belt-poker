package com.aba.bbp;

import com.aba.bbp.enums.CardRank;

public class CardSet implements Comparable<CardSet> {

  public CardRank rank;
  public int size;

  public CardSet(CardRank rank, int size) {
	this.rank = rank;
	this.size = size;
  }

  @Override
  public int compareTo(CardSet otherCardSet) {
	if (size == otherCardSet.size) {
	  return rank.ordinal() - otherCardSet.rank.ordinal();
	}
	return size - otherCardSet.size;
  }
}
