package com.aba.bbp.model;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;
import javax.validation.constraints.NotNull;

public record Card(@NotNull CardRank rank, @NotNull Suite suite) implements Comparable<Card> {

  @Override
  public String toString() {
	return rank.toString() + suite.toString();
  }

  @Override
  public int compareTo(@NotNull Card otherCard) {
	return rank.ordinal() - otherCard.rank.ordinal();
  }
}
