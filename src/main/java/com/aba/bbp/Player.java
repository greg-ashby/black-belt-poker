package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Player {

  private final List<Card> cards = new ArrayList<>();

  public void takeCard(Card card) {
    cards.add(card);
  }

  public Hand calculateBestHand() {
    int[] setsIndex = new int[CardRank.values().length];
    boolean[] runsIndex = new boolean[CardRank.values().length];
    int[] suitesIndex = new int[Suite.values().length];

    for (Card card : cards) {
      setsIndex[card.rank.ordinal()] += 1;
      runsIndex[card.rank.ordinal()] = true;
      suitesIndex[card.suite.ordinal()] += 1;
    }

    cards.sort(Comparator.reverseOrder());
    Hand hand = new Hand(HIGH_CARD, new ArrayList<>());

    int maxSetSize = Arrays.stream(setsIndex).max().orElse(-1);
    if (maxSetSize == 2) {
      hand.handRank = PAIR;
      int pairOrdinal = IntStream.range(0, setsIndex.length)
          .filter(i -> setsIndex[i] == maxSetSize).findFirst()
          .orElse(-1);
      CardRank pairRank = CardRank.values()[pairOrdinal];
      hand.kickers.add(pairRank);
      cards.stream().filter(card -> !card.rank.equals(pairRank)).limit(3)
          .forEach(card -> hand.kickers.add(card.rank));
    } else if (maxSetSize < 2) {
      cards.stream().limit(5).forEach(card -> hand.kickers.add(card.rank));
    }
    return hand;
  }
}
