package com.aba.bbp;

import static com.aba.bbp.enums.HandRank.HIGH_CARD;
import static com.aba.bbp.enums.HandRank.PAIR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.Suite;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class PlayerTest {

  private Player player;

  @BeforeEach
  public void setUp() {
    player = new Player();
  }

  @Nested
  class CalculateBestHand {

    @Test
    public void shouldCalculateHighCard() {
      // Arrange
      List<Card> cards =
          Arrays.asList(new Card(CardRank.ACE, Suite.HEARTS),
              new Card(CardRank.QUEEN, Suite.DIAMONDS),
              new Card(CardRank.TWO, Suite.SPADES),
              new Card(CardRank.TEN, Suite.SPADES),
              new Card(CardRank.EIGHT, Suite.CLUBS),
              new Card(CardRank.SIX, Suite.HEARTS),
              new Card(CardRank.FOUR, Suite.DIAMONDS));
      cards.forEach(player::takeCard);
      List<CardRank> expectedKickers =
          Arrays.asList(CardRank.ACE, CardRank.QUEEN, CardRank.TEN, CardRank.EIGHT,
              CardRank.SIX);

      // Act
      Hand actualHand = player.calculateBestHand();

      // Assert
      assertEquals(HIGH_CARD, actualHand.handRank);
      assertIterableEquals(expectedKickers, actualHand.kickers);
    }

    @Test
    void shouldCalculateOnePair() {
      // Arrange
      List<Card> cards =
          Arrays.asList(new Card(CardRank.ACE, Suite.HEARTS),
              new Card(CardRank.QUEEN, Suite.DIAMONDS),
              new Card(CardRank.TEN, Suite.SPADES),
              new Card(CardRank.TEN, Suite.CLUBS),
              new Card(CardRank.SIX, Suite.HEARTS),
              new Card(CardRank.FOUR, Suite.DIAMONDS),
              new Card(CardRank.TWO, Suite.SPADES));
      cards.forEach(player::takeCard);
      List<CardRank> expectedKickers =
          Arrays.asList(CardRank.TEN, CardRank.ACE, CardRank.QUEEN,
              CardRank.SIX);

      // Act
      Hand actualHand = player.calculateBestHand();

      // Assert
      assertEquals(PAIR, actualHand.handRank);
      assertIterableEquals(expectedKickers, actualHand.kickers);
    }

    // TODO parameterize this, and then work up the card values
    // two pair, three of a kind, flush, straight, fullhouse, four of a kind, straight-flush
  }

}
