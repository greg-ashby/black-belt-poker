package com.aba.bbp.enums;

public enum Suite {
  HEARTS, DIAMONDS, SPADES, CLUBS;

  @Override
  public String toString() {
	return switch (this) {
	  case HEARTS -> "♥";
	  case DIAMONDS -> "♦";
	  case CLUBS -> "♠";
	  case SPADES -> "♣";
	};
  }
}
