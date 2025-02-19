package com.aba.bbp.model;

import com.aba.bbp.enums.CardRank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CardSet implements Comparable<CardSet> {

  private final CardRank rank;
  private int size = 2;

  public void incrementSize() {
	this.size++;
  }

  @Override
  public int compareTo(@NotNull CardSet otherCardSet) {
	if (size == otherCardSet.size) {
	  return rank.ordinal() - otherCardSet.rank.ordinal();
	}
	return size - otherCardSet.size;
  }
}
