package com.aba.bbp;


import com.aba.bbp.enums.CardRank;
import lombok.Getter;

public class CardRun {

  public CardRank rank;

  @Getter
  public boolean isFlush;

  public CardRun(CardRank rank) {
	this.rank = rank;
  }

}
