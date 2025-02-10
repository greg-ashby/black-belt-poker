package com.aba.bbp;

import static com.aba.bbp.enums.CardRank.KING;
import static com.aba.bbp.enums.Suite.HEARTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aba.bbp.enums.CardRank;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CardTest {

  @Nested
  class ToString {

	@Test
	void shouldPrintKingOfHearts() {
	  Card card = new Card(KING, HEARTS);
	  assertEquals("K♥", card.toString(), "Card toString should format rank and suit correctly");
	}
  }

  @Nested
  class CompareTo {

	private static Stream<Arguments> provideCardComparisons() {
	  return Stream.of(Arguments.of(CardRank.SIX, CardRank.ACE, -1),
		  Arguments.of(CardRank.QUEEN, CardRank.QUEEN, 0), Arguments.of(KING, CardRank.JACK, 1));
	}

	@ParameterizedTest
	@MethodSource("provideCardComparisons")
	void shouldCompareCardsCorrectly(CardRank rank1, CardRank rank2, int expectedResult) {
	  Card testCard = new Card(rank1, null);
	  Card compareToCard = new Card(rank2, null);

	  int result = testCard.compareTo(compareToCard);

	  assertEquals(expectedResult, Integer.signum(result));
	}
  }
}
