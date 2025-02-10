package com.aba.bbp;

import static com.aba.bbp.enums.CardRank.ACE;
import static com.aba.bbp.enums.CardRank.FIVE;
import static com.aba.bbp.enums.CardRank.JACK;
import static com.aba.bbp.enums.CardRank.KING;
import static com.aba.bbp.enums.CardRank.NINE;
import static com.aba.bbp.enums.CardRank.QUEEN;
import static com.aba.bbp.enums.CardRank.SIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CardSetTest {

  @Nested
  class CompareTo {

	private static Stream<Arguments> provideCardComparisons() {
	  return Stream.of(
		  Arguments.of(new CardSet(SIX), new CardSet(ACE), -1),
		  Arguments.of(new CardSet(QUEEN), new CardSet(QUEEN), 0),
		  Arguments.of(new CardSet(KING), new CardSet(JACK), 1),
		  Arguments.of(new CardSet(FIVE, 3), new CardSet(NINE), 1));
	}

	@ParameterizedTest
	@MethodSource("provideCardComparisons")
	void shouldCompareCardSetsCorrectly(CardSet cardSet1, CardSet cardSet2, int expectedResult) {

	  // Act
	  int result = cardSet1.compareTo(cardSet2);

	  // Assert
	  assertEquals(expectedResult, Integer.signum(result));
	}
  }

}
