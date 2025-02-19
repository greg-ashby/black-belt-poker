package com.aba.bbp.model;

import java.util.ArrayList;
import java.util.List;

public class Table {

  private final List<Player> players = new ArrayList<>();
  private final Deck deck = new Deck();

  public void seatPlayer(Player player) {
	players.add(player);
  }

  public void seatPlayers(int playerCount) {
	players.clear();
	for (int i = 0; i < playerCount; i++) {
	  seatPlayer(new Player());
	}
  }

  public void reset() {
	players.forEach(Player::reset);
	deck.reset();
  }

  public List<Hand> dealGame() {
	reset();

	deck.burnCard();
	dealRound(deck);
	dealRound(deck);

	for (int i = 0; i < 5; i++) {
	  deck.burnCard();
	  dealCommunityCard(deck.drawCard());
	}

	return players.stream().map(Player::calculateBestHand).toList();
  }

  private void dealCommunityCard(Card card) {
	players.forEach(player -> player.takeCard(card));
  }

  private void dealRound(Deck deck) {
	players.forEach(player -> player.takeCard(deck.drawCard()));
  }
}
