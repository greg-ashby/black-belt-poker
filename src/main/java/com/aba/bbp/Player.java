package com.aba.bbp;

import static com.aba.bbp.enums.CardRank.ACE;
import static com.aba.bbp.enums.CardRank.FIVE;
import static com.aba.bbp.enums.CardRank.SIX;
import static com.aba.bbp.enums.CardRank.values;
import static com.aba.bbp.enums.HandRank.FLUSH;
import static com.aba.bbp.enums.HandRank.FOUR_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.FULL_HOUSE;
import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static com.aba.bbp.enums.HandRank.STRAIGHT;
import static com.aba.bbp.enums.HandRank.STRAIGHT_FLUSH;
import static com.aba.bbp.enums.HandRank.THREE_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.TWO_PAIR;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import com.aba.bbp.enums.Suite;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public class Player {

  private final List<Card> cards = new ArrayList<>();

  public void takeCard(@NotNull Card card) {
	cards.add(card);
  }

  @NotNull
  public Hand calculateBestHand() {
	cards.sort(Comparator.reverseOrder());

	List<CardSet> cardSets = getCardSets(cards);
	Optional<Suite> flushSuite = findFlushSuite(cards);
	List<Straight> straights = getCardRuns(cards, flushSuite);

	HandRank handRank = getBestHandRank(cardSets, straights, flushSuite);
	List<CardRank> kickers = getKickers(handRank, cardSets, straights, flushSuite);

	return new Hand(handRank, kickers);
  }

  @NotNull
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private List<Straight> getCardRuns(List<Card> cards, Optional<Suite> flushSuite) {
	List<Straight> straights = getTopRankOfEachStraight(cards).stream().map(Straight::new).toList();
	if (flushSuite.isPresent()) {
	  Suite flushSuiteValue = flushSuite.get();
	  for (Straight straight : straights) {
		if (isStraightFlush(straight.getRank(), flushSuiteValue)) {
		  straight.setFlush(true);
		}
	  }
	}
	return straights;
  }

  private List<CardRank> getTopRankOfEachStraight(List<Card> cards) {
	List<CardRank> cardRuns = new ArrayList<>();

	long rankIndexMask = 0;
	for (Card card : cards) {
	  rankIndexMask |= (1L << card.rank().ordinal());
	}

	for (int x = ACE.ordinal(); x >= SIX.ordinal(); x--) {
	  long straightMask = (0x1FL << x - 4);
	  if ((rankIndexMask & straightMask) == straightMask) {
		cardRuns.add(values()[x]);
	  }
	}

	long aceLowMask = 0b1000000001111L;
	if ((rankIndexMask & aceLowMask) == aceLowMask) {
	  cardRuns.add(FIVE);
	}

	return cardRuns;
  }

  @NotNull
  private Optional<Suite> findFlushSuite(List<Card> cards) {
	int[] suites = new int[Suite.values().length];
	for (Card card : cards) {
	  int index = card.suite().ordinal();
	  suites[index]++;
	  if (suites[index] == 5) {
		return Optional.of(card.suite());
	  }
	}
	return Optional.empty();
  }

  @NotNull
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private List<CardRank> getKickers(@NotNull HandRank handRank, @NotNull List<CardSet> cardSets,
	  List<Straight> straights, Optional<Suite> flushSuite) {
	List<CardRank> kickers = new ArrayList<>();

	switch (handRank) {
	  case HIGH_CARD -> setKickersInOrder(kickers, List.of(), 5);
	  case PAIR -> setKickersInOrder(kickers, List.of(cardSets.get(0).getRank()), 3);
	  case TWO_PAIR -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).getRank(), cardSets.get(1).getRank()), 1);
	  case THREE_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).getRank()), 2);
	  case STRAIGHT -> kickers.add(straights.get(0).getRank());
	  case FLUSH -> flushSuite.ifPresentOrElse(
		  suite ->
			  cards.stream().filter(card -> card.suite() == suite)
				  .limit(5)
				  .map(Card::rank)
				  .forEach(kickers::add),
		  () -> {
			throw new RuntimeException("hand is not a flush");
		  }
	  );
	  case FULL_HOUSE -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).getRank(), cardSets.get(1).getRank()), 0);
	  case FOUR_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).getRank()), 1);
	  case STRAIGHT_FLUSH -> straights.stream()
		  .filter(Straight::isFlush)
		  .findFirst()
		  .ifPresent(straight -> kickers.add(straight.getRank()));
	}

	return kickers;
  }

  private void setKickersInOrder(List<CardRank> kickers, List<CardRank> firstRanks,
	  int additionalRanksToAdd) {
	kickers.addAll(firstRanks);
	cards.stream().filter(card -> !firstRanks.contains(card.rank())).limit(additionalRanksToAdd)
		.forEach(card -> kickers.add(card.rank()));
  }

  @NotNull
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private HandRank getBestHandRank(@NotNull List<CardSet> cardSets,
	  List<Straight> straights, @NotNull Optional<Suite> flushSuite) {
	boolean hasSets = !cardSets.isEmpty();
	boolean hasTwoSets = cardSets.size() == 2;
	boolean hasFlush = flushSuite.isPresent();
	boolean hasStraight = !straights.isEmpty();

	if (hasStraight && hasFlush) {
	  if (straights.stream().anyMatch(Straight::isFlush)) {
		return STRAIGHT_FLUSH;
	  }
	}

	if (hasSets && cardSets.get(0).getSize() == 4) {
	  return FOUR_OF_A_KIND;
	}

	if (hasTwoSets && cardSets.get(0).getSize() == 3) {
	  return FULL_HOUSE;
	}

	if (hasFlush) {
	  return FLUSH;
	}

	if (hasStraight) {
	  return STRAIGHT;
	}

	if (hasSets && cardSets.get(0).getSize() == 3) {
	  return THREE_OF_A_KIND;
	}

	if (hasTwoSets) {
	  return TWO_PAIR;
	}

	if (hasSets) {
	  return PAIR;
	}

	return HIGH_CARD;
  }

  @NotNull
  private boolean isStraightFlush(CardRank topRankOfStraight, Suite suite) {
	int count = 0;
	int currentOrdinal = topRankOfStraight.ordinal();

	for (Card card : cards) {
	  if (card.rank().ordinal() == currentOrdinal && card.suite() == suite) {
		count++;
		currentOrdinal -= 1;
		if (count == 5) {
		  return true;
		}
	  }
	}

	return false;
  }

  @NotNull
  private List<CardSet> getCardSets(@NotNull List<Card> cards) {
	Card previousCard = cards.get(0);
	CardSet currentSet = null;
	List<CardSet> cardSets = new ArrayList<>();

	for (Card currentCard : cards.subList(1, cards.size())) {
	  if (previousCard.rank() == currentCard.rank()) {
		if (currentSet == null) {
		  currentSet = new CardSet(currentCard.rank());
		  cardSets.add(currentSet);
		} else {
		  currentSet.incrementSize();
		}
	  } else {
		currentSet = null;
	  }
	  previousCard = currentCard;
	}

	cardSets.sort(Comparator.reverseOrder());
	return cardSets;
  }
}

