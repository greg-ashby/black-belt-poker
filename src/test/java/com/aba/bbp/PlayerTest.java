package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static com.aba.bbp.enums.HandRank.TWO_PAIR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;
import com.aba.bbp.enums.Suite;
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
                  new Card(CardRank.ACE, Suite.HEARTS),
                  new Card(CardRank.QUEEN, Suite.DIAMONDS),
                  new Card(CardRank.TWO, Suite.SPADES),
                  new Card(CardRank.TEN, Suite.SPADES),
                  new Card(CardRank.EIGHT, Suite.CLUBS),
                  new Card(CardRank.SIX, Suite.HEARTS),
                  new Card(CardRank.FOUR, Suite.DIAMONDS)
              ),
              HIGH_CARD,
              Arrays.asList(CardRank.ACE, CardRank.QUEEN, CardRank.TEN, CardRank.EIGHT,
                  CardRank.SIX)
          },
          new Object[]{
              Arrays.asList(
                  new Card(CardRank.ACE, Suite.HEARTS),
                  new Card(CardRank.QUEEN, Suite.DIAMONDS),
                  new Card(CardRank.TEN, Suite.SPADES),
                  new Card(CardRank.TEN, Suite.CLUBS),
                  new Card(CardRank.SIX, Suite.HEARTS),
                  new Card(CardRank.FOUR, Suite.DIAMONDS),
                  new Card(CardRank.TWO, Suite.SPADES)
              ),
              PAIR,
              Arrays.asList(CardRank.TEN, CardRank.ACE, CardRank.QUEEN, CardRank.SIX)
          },
          new Object[]{
              Arrays.asList(
                  new Card(CardRank.SIX, Suite.SPADES),
                  new Card(CardRank.QUEEN, Suite.DIAMONDS),
                  new Card(CardRank.TEN, Suite.SPADES),
                  new Card(CardRank.TEN, Suite.CLUBS),
                  new Card(CardRank.SIX, Suite.HEARTS),
                  new Card(CardRank.FOUR, Suite.DIAMONDS),
                  new Card(CardRank.TWO, Suite.SPADES)
              ),
              TWO_PAIR,
              Arrays.asList(CardRank.TEN, CardRank.SIX, CardRank.QUEEN)
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
