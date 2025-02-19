package com.aba.bbp.runners;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.model.Hand;
import com.aba.bbp.model.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will run a game, get the results, and store them for analysis of: - what the best hand
 * is for players of 2-9 - what hole cards won the game for players of 2-9
 */
public class StatRunner {

  public static final int GAME_COUNT = 100000;

  public static void main(String[] args) {

	Table table = new Table();
	table.seatPlayers(9);

	Map<Integer, Map<String, Integer>> handWinsPerPlayerCount = new HashMap<>();
	Map<Integer, Map<String, Integer>> handAppearancesPerPlayerCount = new HashMap<>();

	for (int i = 0; i < 9; i++) {
	  handWinsPerPlayerCount.put(i, new HashMap<>());
	  handAppearancesPerPlayerCount.put(i, new HashMap<>());

	}

	for (int games = 0; games < GAME_COUNT; games++) {

	  List<Hand> hands = table.dealGame();

	  for (int i = 0; i < hands.size(); i++) {
		String holeKey = hands.get(i).getHoleKey();

		for (int playerCount = i; playerCount < 9; playerCount++) {
		  Map<String, Integer> appearanceCounts = handAppearancesPerPlayerCount.get(playerCount);
		  appearanceCounts.put(
			  holeKey,
			  appearanceCounts.getOrDefault(holeKey, 0) + 1
		  );
		}
	  }

	  Hand currentBestHand = hands.get(0);

	  for (int i = 1; i < hands.size(); i++) {
		Map<String, Integer> winCounts = handWinsPerPlayerCount.get(i);

		Hand nextPlayersHand = hands.get(i);

		currentBestHand =
			nextPlayersHand.compareTo(currentBestHand) > 0 ? nextPlayersHand : currentBestHand;
		winCounts.put(currentBestHand.getHoleKey(),
			winCounts.getOrDefault(currentBestHand.getHoleKey(), 0) + 1);

	  }
	}

	handWinsPerPlayerCount.forEach((playerCount, winCounts) -> {
	  System.out.println("===== " + playerCount + " Players =====");
	  Map<String, Integer> appearanceCountsForPlayersX = handAppearancesPerPlayerCount.get(
		  playerCount);

	  renderPokerTable(winCounts, appearanceCountsForPlayersX);
//	  winCounts.entrySet().stream()
//		  .sorted(Map.Entry.comparingByKey())
//		  .forEach(entry -> {
//			String holeKey = entry.getKey();
//			int wins = entry.getValue();
//			int appearances = appearanceCountsForPlayersX.getOrDefault(holeKey, 0);
//
//			double winRate = appearances > 0 ? (double) wins / appearances : 0.0;
//			System.out.printf("%s - Wins: %d, Appearances: %d, Win Rate: %.2f%%%n", holeKey, wins,
//				appearances, winRate * 100);
//
//		  });
//
//	  System.out.println("");
	});
  }

  private static void renderPokerTable(Map<String, Integer> winCounts,
	  Map<String, Integer> appearanceCounts) {
	// Get all card ranks in descending order
	CardRank[] ranks = CardRank.values();
	int n = ranks.length;

	// Print table header (column labels)
	System.out.print("   "); // Empty space for row headers
	for (int col = n - 1; col >= 0; col--) {
	  System.out.printf("%4s", ranks[col]);
	}
	System.out.println();

	// Fill table rows
	for (int i = n - 1; i >= 0; i--) {
	  // Print row label (row header)
	  System.out.printf("%4s", ranks[i]);

	  for (int j = n - 1; j >= 0; j--) {
		String holeKey;
		if (i == j) {
		  // Pairs
		  holeKey = "O" + ranks[i].toString() + ranks[j].toString();
		} else if (i < j) {
		  // Suited cards
		  holeKey = "O" + ranks[j].toString() + ranks[i].toString();
		} else {
		  // Offsuite cards
		  holeKey = "S" + ranks[i].toString() + ranks[j].toString();
		}

		// Calculate win rate
		int wins = winCounts.getOrDefault(holeKey, 0);
		int appearances = appearanceCounts.getOrDefault(holeKey, 0);
		double winRate = appearances > 0 ? (double) wins / appearances : -1;

		// Render the cell
		if (winRate == -1) {
		  System.out.printf("%4s", "--");
		} else {
		  System.out.printf("%4.0f", winRate * 100); // Render as a percentage without decimals
		}
	  }

	  System.out.println(); // Newline after each row
	}
  }
}
