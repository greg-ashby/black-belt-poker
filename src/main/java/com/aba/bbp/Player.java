package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static com.aba.bbp.enums.HandRank.TWO_PAIR;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.NotNull;

public class Player {

  private final List<Card> cards = new ArrayList<>();

  public void takeCard(Card card) {
	cards.add(card);
  }

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
	  case PAIR -> {
		CardRank pairRank = cardSets.get(0).rank;
		kickers.add(pairRank);
		cards.stream().filter(card -> !card.rank.equals(pairRank)).limit(3)
			.forEach(card -> kickers.add(card.rank));
	  }
	  case TWO_PAIR -> {
		CardRank firstPair = cardSets.get(0).rank;
		CardRank secondPair = cardSets.get(1).rank;
		kickers.add(firstPair);
		kickers.add(secondPair);
		cards.stream()
			.filter(card -> !card.rank.equals(firstPair))
			.filter(card -> !card.rank.equals(secondPair))
			.limit(1)
			.forEach(card -> kickers.add(card.rank));
	  }
	  case HIGH_CARD -> cards.stream().limit(5).forEach(card -> kickers.add(card.rank));
	}

	return kickers;
  }

  @NotNull
  private HandRank getBestHandRank(@NotNull List<CardSet> cardSets) {
	if (!cardSets.isEmpty()) {
	  if (cardSets.size() == 1 && cardSets.get(0).size == 2) {
		return PAIR;
	  } else if (cardSets.size() == 2 && cardSets.get(0).size == 2) {
		return TWO_PAIR;
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

