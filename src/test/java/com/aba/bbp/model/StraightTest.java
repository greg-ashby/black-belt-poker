package com.aba.bbp.model;

import static com.aba.bbp.enums.CardRank.ACE;
import static com.aba.bbp.enums.CardRank.JACK;
import static com.aba.bbp.enums.CardRank.KING;
import static com.aba.bbp.enums.CardRank.QUEEN;
import static com.aba.bbp.enums.CardRank.TEN;
import static com.aba.bbp.enums.CardRank.THREE;
import static com.aba.bbp.enums.CardRank.TWO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StraightTest {

  @Nested
  class CompareTo {

	// Provide test data for parameterized test
	static Stream<Arguments> provideCardRunsForComparison() {
	  return Stream.of(
		  // Case 1: straight1 is flush, straight2 is not (Flush wins)
		  Arguments.of(
			  new Straight(ACE, true),
			  new Straight(KING, false),
			  1
		  ),

		  // Case 2: straight1 is not flush, straight2 is flush (Flush wins)
		  Arguments.of(
			  new Straight(KING, false),
			  new Straight(ACE, true),
			  -1
		  ),

		  // Case 3: Both are flush, compare by rank
		  Arguments.of(
			  new Straight(TWO, true),
			  new Straight(THREE, true),
			  -1
		  ),

		  // Case 4: Both are not flush, compare by rank
		  Arguments.of(
			  new Straight(QUEEN, false),
			  new Straight(JACK, false),
			  1
		  ),

		  // Case 5: Both are flush and equal rank
		  Arguments.of(
			  new Straight(KING, true),
			  new Straight(KING, true),
			  0
		  ),

		  // Case 6: Both are not flush and equal rank
		  Arguments.of(
			  new Straight(TEN, false),
			  new Straight(TEN, false),
			  0
		  )
	  );
	}

	@ParameterizedTest
	@MethodSource("provideCardRunsForComparison")
	void shouldSortByIsFlushAndRank(Straight straight1, Straight straight2,
		int expectedResult) {
	  // Act
	  int actualResult = straight1.compareTo(straight2);

	  // Assert
	  assertEquals(expectedResult, Integer.signum(actualResult));
	}
  }
}