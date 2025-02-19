package com.aba.bbp.model;


import com.aba.bbp.enums.CardRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Straight implements Comparable<Straight> {

  private final CardRank rank;

  @Setter
  private boolean isFlush;

  @Override
  public int compareTo(Straight otherStraight) {
	if (this.isFlush == otherStraight.isFlush) {
	  return this.rank.ordinal() - otherStraight.rank.ordinal();
	} else {
	  return this.isFlush ? 1 : -1;
	}
  }
}
