package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.FOUR_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.FULL_HOUSE;
import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static com.aba.bbp.enums.HandRank.THREE_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.TWO_PAIR;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

	hand.handRank = getBestHandRank(cardSets);
	hand.kickers = getKickers(hand.handRank, cardSets);

	return hand;
  }

  @NotNull
  private List<CardRank> getKickers(@NotNull HandRank handRank, @NotNull List<CardSet> cardSets) {
	List<CardRank> kickers = new ArrayList<>();

	switch (handRank) {
	  case PAIR -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 3);
	  case TWO_PAIR -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).rank, cardSets.get(1).rank), 1);
	  case THREE_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 2);
	  case FULL_HOUSE -> setKickersInOrder(kickers,
		  List.of(cardSets.get(0).rank, cardSets.get(1).rank), 0);
	  case FOUR_OF_A_KIND -> setKickersInOrder(kickers, List.of(cardSets.get(0).rank), 1);
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
  private HandRank getBestHandRank(@NotNull List<CardSet> cardSets) {
	if (!cardSets.isEmpty()) {
	  if (cardSets.size() == 1) {
		if (cardSets.get(0).size == 2) {
		  return PAIR;
		} else if (cardSets.get(0).size == 3) {
		  return THREE_OF_A_KIND;
		} else if (cardSets.get(0).size == 4) {
		  return FOUR_OF_A_KIND;
		} else {
		  throw new IllegalStateException(
			  "Hand has a single set but it is an illegal size of " + cardSets.get(0).size);
		}
	  } else if (cardSets.size() == 2) {
		if (cardSets.get(0).size == 2) {
		  return TWO_PAIR;
		} else if (cardSets.get(0).size == 3) {
		  return FULL_HOUSE;
		} else {
		  throw new IllegalStateException(
			  "Hand has two sets but the first one is an illegal size of " + cardSets.get(0).size);
		}
	  }
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

