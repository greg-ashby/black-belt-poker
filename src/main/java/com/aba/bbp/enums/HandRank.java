package com.aba.bbp.enums;

public enum HandRank {
  HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

  @Override
  public String toString() {
	return ordinal() + 1 + ". " + name();
  }
}
