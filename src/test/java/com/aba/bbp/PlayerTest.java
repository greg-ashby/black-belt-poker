package com.aba.bbp;

import static com.aba.bbp.enums.CardRank.ACE;
import static com.aba.bbp.enums.CardRank.EIGHT;
import static com.aba.bbp.enums.CardRank.FIVE;
import static com.aba.bbp.enums.CardRank.FOUR;
import static com.aba.bbp.enums.CardRank.JACK;
import static com.aba.bbp.enums.CardRank.KING;
import static com.aba.bbp.enums.CardRank.NINE;
import static com.aba.bbp.enums.CardRank.QUEEN;
import static com.aba.bbp.enums.CardRank.SEVEN;
import static com.aba.bbp.enums.CardRank.SIX;
import static com.aba.bbp.enums.CardRank.TEN;
import static com.aba.bbp.enums.CardRank.THREE;
import static com.aba.bbp.enums.CardRank.TWO;
import static com.aba.bbp.enums.HandRank.FLUSH;
import static com.aba.bbp.enums.HandRank.FOUR_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.FULL_HOUSE;
import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static com.aba.bbp.enums.HandRank.STRAIGHT;
import static com.aba.bbp.enums.HandRank.THREE_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.TWO_PAIR;
import static com.aba.bbp.enums.Suite.CLUBS;
import static com.aba.bbp.enums.Suite.DIAMONDS;
import static com.aba.bbp.enums.Suite.HEARTS;
import static com.aba.bbp.enums.Suite.SPADES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class PlayerTest {

  private Player player = new Player();

  @Nested
  class CalculateBestHand {

	private static Stream<Object[]> provideCalculateBestHandData() {
	  return Stream.of(
		  new Object[]{
			  Arrays.asList(
				  new Card(ACE, HEARTS),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TWO, SPADES),
				  new Card(TEN, SPADES),
				  new Card(EIGHT, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(FOUR, DIAMONDS)
			  ),
			  HIGH_CARD,
			  Arrays.asList(ACE, QUEEN, TEN, EIGHT, SIX)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(ACE, HEARTS),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(TEN, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, SPADES)
			  ),
			  PAIR,
			  Arrays.asList(TEN, ACE, QUEEN, SIX)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(TEN, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, SPADES)
			  ),
			  TWO_PAIR,
			  Arrays.asList(TEN, SIX, QUEEN)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(SIX, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, SPADES)
			  ),
			  THREE_OF_A_KIND,
			  Arrays.asList(SIX, QUEEN, TEN)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(KING, SPADES),
				  new Card(QUEEN, CLUBS),
				  new Card(NINE, HEARTS),
				  new Card(FIVE, DIAMONDS),
				  new Card(JACK, DIAMONDS),
				  new Card(TEN, CLUBS),
				  new Card(EIGHT, CLUBS)
			  ),
			  STRAIGHT,
			  Arrays.asList(KING)
		  }, new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, CLUBS),
				  new Card(THREE, HEARTS),
				  new Card(FIVE, DIAMONDS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, CLUBS),
				  new Card(EIGHT, CLUBS)
			  ),
			  STRAIGHT,
			  Arrays.asList(SIX)
		  }, new Object[]{
			  Arrays.asList(
				  new Card(NINE, SPADES),
				  new Card(ACE, CLUBS),
				  new Card(THREE, HEARTS),
				  new Card(FIVE, DIAMONDS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, CLUBS),
				  new Card(EIGHT, CLUBS)
			  ),
			  STRAIGHT,
			  Arrays.asList(FIVE)
		  }, new Object[]{
			  Arrays.asList(
				  new Card(ACE, SPADES),
				  new Card(TEN, CLUBS),
				  new Card(JACK, HEARTS),
				  new Card(QUEEN, DIAMONDS),
				  new Card(FOUR, DIAMONDS),
				  new Card(TWO, CLUBS),
				  new Card(KING, HEARTS)
			  ),
			  STRAIGHT,
			  Arrays.asList(ACE)
		  }, new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(TEN, CLUBS),
				  new Card(NINE, HEARTS),
				  new Card(FIVE, DIAMONDS),
				  new Card(SEVEN, DIAMONDS),
				  new Card(FOUR, CLUBS),
				  new Card(EIGHT, CLUBS)
			  ),
			  STRAIGHT,
			  Arrays.asList(TEN)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(KING, SPADES),
				  new Card(JACK, CLUBS),
				  new Card(NINE, CLUBS),
				  new Card(SEVEN, CLUBS),
				  new Card(FIVE, CLUBS),
				  new Card(THREE, CLUBS),
				  new Card(EIGHT, CLUBS)
			  ),
			  FLUSH,
			  Arrays.asList(JACK, NINE, EIGHT, SEVEN, FIVE)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(SIX, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(TEN, DIAMONDS),
				  new Card(THREE, DIAMONDS)
			  ),
			  FULL_HOUSE,
			  Arrays.asList(SIX, TEN)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(SIX, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(TEN, DIAMONDS),
				  new Card(TEN, DIAMONDS)
			  ),
			  FULL_HOUSE,
			  Arrays.asList(TEN, SIX)
		  },
		  new Object[]{
			  Arrays.asList(
				  new Card(SIX, SPADES),
				  new Card(QUEEN, DIAMONDS),
				  new Card(TEN, SPADES),
				  new Card(SIX, CLUBS),
				  new Card(SIX, HEARTS),
				  new Card(FOUR, DIAMONDS),
				  new Card(SIX, DIAMONDS)
			  ),
			  FOUR_OF_A_KIND,
			  Arrays.asList(SIX, QUEEN)
		  }
		  // TODO straight flush, two straights with one flush
	  );
	}

	@ParameterizedTest
	@MethodSource("provideCalculateBestHandData")
	public void shouldCalculateBestHand(List<Card> testCards, HandRank expectedHandRank,
		List<CardRank> expectedKickers) {
	  // Arrange
	  testCards.forEach(player::takeCard);

	  // Act
	  Hand actualHand = player.calculateBestHand();

	  // Assert
	  assertEquals(expectedHandRank, actualHand.handRank);
	  assertIterableEquals(expectedKickers, actualHand.kickers);
	}
  }

}
