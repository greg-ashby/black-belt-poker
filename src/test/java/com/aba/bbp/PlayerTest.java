package com.aba.bbp;

import static com.aba.bbp.enums.CardRank.ACE;
import static com.aba.bbp.enums.CardRank.EIGHT;
import static com.aba.bbp.enums.CardRank.FOUR;
import static com.aba.bbp.enums.CardRank.QUEEN;
import static com.aba.bbp.enums.CardRank.SIX;
import static com.aba.bbp.enums.CardRank.TEN;
import static com.aba.bbp.enums.CardRank.THREE;
import static com.aba.bbp.enums.CardRank.TWO;
import static com.aba.bbp.enums.HandRank.FOUR_OF_A_KIND;
import static com.aba.bbp.enums.HandRank.FULL_HOUSE;
import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
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
		  }
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
