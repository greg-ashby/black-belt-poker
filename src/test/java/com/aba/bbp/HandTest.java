package com.aba.bbp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HandTest {

  @Nested
  class CompareTo {

	static Stream<Arguments> provideHandsForComparison() {
	  return Stream.of(
		  // Comparison by HandRank
		  Arguments.of(
			  new Hand(HandRank.FLUSH,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.TEN)),
			  new Hand(HandRank.STRAIGHT, List.of(CardRank.KING)),
			  // Straight is defined by its high card
			  1 // Flush > Straight
		  ),
		  Arguments.of(
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.TEN)),
			  new Hand(HandRank.PAIR,
				  List.of(CardRank.TWO, CardRank.KING, CardRank.QUEEN, CardRank.JACK)),
			  // Pair uses 4 kickers: pair rank + 3 high cards
			  -1 // High Card < Pair
		  ),
		  // High Card comparison using 5 kickers
		  Arguments.of(
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.TEN)),
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.NINE)),
			  1 // Ten kicker > Nine kicker
		  ),
		  // Pair comparison using 4 kickers
		  Arguments.of(
			  new Hand(HandRank.PAIR,
				  List.of(CardRank.KING, CardRank.ACE, CardRank.QUEEN, CardRank.JACK)),
			  new Hand(HandRank.PAIR,
				  List.of(CardRank.KING, CardRank.ACE, CardRank.TEN, CardRank.NINE)),
			  1 // Queen kicker > Ten kicker
		  ),
		  // Two Pair comparison using 3 kickers
		  Arguments.of(
			  new Hand(HandRank.TWO_PAIR, List.of(CardRank.KING, CardRank.TEN, CardRank.JACK)),
			  new Hand(HandRank.TWO_PAIR, List.of(CardRank.KING, CardRank.TEN, CardRank.NINE)),
			  1 // Jack kicker > Nine kicker
		  ),
		  // Trips comparison (only 1 kicker needed since ties are impossible)
		  Arguments.of(
			  new Hand(HandRank.THREE_OF_A_KIND, List.of(CardRank.KING)),
			  new Hand(HandRank.THREE_OF_A_KIND, List.of(CardRank.QUEEN)),
			  1 // King trips > Queen trips
		  ),
		  // Straight comparison using 1 kicker
		  Arguments.of(
			  new Hand(HandRank.STRAIGHT, List.of(CardRank.ACE)), // Ace-high straight
			  new Hand(HandRank.STRAIGHT, List.of(CardRank.KING)), // King-high straight
			  1 // Ace-high > King-high straight
		  ),
		  // Full House comparison using 2 kickers
		  Arguments.of(
			  new Hand(HandRank.FULL_HOUSE, List.of(CardRank.KING, CardRank.TEN)),
			  // King-full-of-Tens
			  new Hand(HandRank.FULL_HOUSE, List.of(CardRank.KING, CardRank.NINE)),
			  // King-full-of-Nines
			  1 // Tens kicker > Nines kicker
		  ),
		  // Quads comparison (only 1 kicker needed since ties are impossible)
		  Arguments.of(
			  new Hand(HandRank.FOUR_OF_A_KIND, List.of(CardRank.ACE)), // Four Aces
			  new Hand(HandRank.FOUR_OF_A_KIND, List.of(CardRank.KING)), // Four Kings
			  1 // Four Aces > Four Kings
		  ),
		  // Straight Flush comparison (only 1 kicker needed since the rest are implied)
		  Arguments.of(
			  new Hand(HandRank.STRAIGHT_FLUSH, List.of(CardRank.ACE)), // Ace-high straight flush
			  new Hand(HandRank.STRAIGHT_FLUSH, List.of(CardRank.KING)), // King-high straight flush
			  1 // Ace-high > King-high straight flush
		  ),
		  // Identical hands
		  Arguments.of(
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.TEN)),
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.JACK,
					  CardRank.TEN)),
			  0 // Both hands are equal
		  ),
		  // Kicker comparison JACK < QUEEN at index 2
		  Arguments.of(
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.JACK, CardRank.TEN, CardRank.NINE)),
			  new Hand(HandRank.HIGH_CARD,
				  List.of(CardRank.ACE, CardRank.KING, CardRank.QUEEN, CardRank.TEN,
					  CardRank.NINE)),
			  -1
		  )
	  );
	}

	@ParameterizedTest
	@MethodSource("provideHandsForComparison")
	void shouldCompareHandsCorrectly(Hand hand1, Hand hand2, int expected) {
	  int actual = hand1.compareTo(hand2);
	  assertEquals(expected, actual);
	}
  }

}