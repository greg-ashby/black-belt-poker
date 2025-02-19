package com.aba.bbp.enums;

public enum CardRank {
  TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

  @Override
  public String toString() {
	return switch (this) {
	  case ACE -> "A";
	  case KING -> "K";
	  case QUEEN -> "Q";
	  case JACK -> "J";
	  case TEN -> "T";
	  default -> String.valueOf(ordinal() + 2);
	};
  }
}
