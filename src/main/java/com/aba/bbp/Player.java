package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.FLUSH;
import static com.aba.bbp.enums.HandRank.FOUR_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.FULL_HOUSE;
import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
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
	Hand hand = new Hand(HIGH_CARD, new ArrayList<>());

	List<CardSet> cardSets = getCardSets(cards);
	Optional<Suite> flushSuite = findFlushSuite(cards);

	hand.handRank = getBestHandRank(cardSets, flushSuite);
	hand.kickers = getKickers(hand.handRank, cardSets, flushSuite);

	return hand;
  }

  private Optional<Suite> findFlushSuite(List<Card> cards) {
	int[] suites = new int[Suite.values().length];
	for (Card card : cards) {
	  int index = card.suite.ordinal();
	  suites[index]++;
	  if (suites[index] == 5) {
		return Optional.of(card.suite);
	  }
	}
	return Optional.empty();
  }

  @NotNull
  private List<CardRank> getKickers(@NotNull HandRank handRank, @NotNull List<CardSet> cardSets,
	  Optional<Suite> flushSuite) {
	List<CardRank> kickers = new ArrayList<>();

	switch (handRank) {
	  case PAIR -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 3);
	  case TWO_PAIR -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).rank, cardSets.get(1).rank), 1);
	  case THREE_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 2);
	  case FULL_HOUSE -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).rank, cardSets.get(1).rank), 0);
	  case FOUR_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 1);
	  case FLUSH -> cards.stream().filter(card -> card.suite == flushSuite.get()).limit(5)
		  .map(Card::getRank).forEach(kickers::add);
	  case HIGH_CARD -> setKickersInOrder(kickers, List.of(), 5);
	}

	return kickers;
  }

  private void setKickersInOrder(List<CardRank> kickers, List<CardRank> firstRanks,
	  int additionalRanksToAdd) {
	kickers.addAll(firstRanks);
	cards.stream().filter(card -> !firstRanks.contains(card.rank)).limit(additionalRanksToAdd)
		.forEach(card -> kickers.add(card.rank));
  }

  @NotNull
  private HandRank getBestHandRank(@NotNull List<CardSet> cardSets, Optional<Suite> flushSuite) {
	boolean hasSets = !cardSets.isEmpty();
	boolean hasTwoSets = cardSets.size() == 2;
	boolean hasFlush = flushSuite.isPresent();

	if (hasSets && cardSets.get(0).size == 4) {
	  return FOUR_OF_A_KIND;
	}

	if (hasTwoSets && cardSets.get(0).size == 3) {
	  return FULL_HOUSE;
	}

	if (hasFlush) {
	  return FLUSH;
	}

	if (hasSets && cardSets.get(0).size == 3) {
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
  private List<CardSet> getCardSets(@NotNull List<Card> cards) {
	Card previousCard = new Card();
	CardSet currentSet = null;
	List<CardSet> cardSets = new ArrayList<>();

	for (Card currentCard : cards) {
	  if (previousCard.rank == currentCard.rank) {
		if (currentSet == null) {
		  currentSet = new CardSet(currentCard.rank, 2);
		  cardSets.add(currentSet);
		} else {
		  currentSet.size++;
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

