
/*
0o * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

import java.util.Arrays;
import java.util.stream.*;

import com.sun.tools.javac.code.Attribute.Array;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	/* Java main method to ensure that this program starts correctly */
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	/* Instance Variables */
	private int nPlayers;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/* arrays */
	private String[] playerNames;
	private int[] dieResults = new int[N_DICE]; // stores die results
	private int categorySelected; // stores currently selected category
	private int[][] categoriesSelected; // stores categories selected before
	private int[][] scoreCard; // score card

	
	private int[] resultsInOrder;
	private int[] dieMatches = new int[N_DICE + 1];
	private int[] totalScores = new int[nPlayers];

	public void run() {	
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");

		if (nPlayers > MAX_PLAYERS) {
			nPlayers = dialog.readInt("Invalid. Maximum # of players is " + MAX_PLAYERS);
		} else if (nPlayers == 1) {
			nPlayers = dialog.readInt("You can't play by yourself. Try again.");
		}

		playerNames = new String[nPlayers];
		scoreCard = new int[N_CATEGORIES + 1][nPlayers + 1];
		categoriesSelected = new int[N_CATEGORIES + 1][nPlayers + 1];

		for (int a = 0; a <= nPlayers; a++) {
			for (int b = 0; b < N_CATEGORIES; b++) {
				categoriesSelected[b][a] = 0;
			}
		}

		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}

		display = new YahtzeeDisplay(getGCanvas(), playerNames);

		playGame();

		//scores
		for (int i = 1; i <= nPlayers; i++) {
			display.updateScorecard(7, i, calcUpperScore(i));
			display.updateScorecard(16, i, calcLowerScore(i));
			display.updateScorecard(TOTAL, i, scoreCard[TOTAL][i]);
		}
		
		//winner
		findWinner();

	}

	public void playGame() {
		for (int roundNum = 0; roundNum < N_SCORING_CATEGORIES; roundNum++) {
			//display.printMessage(String.valueOf(roundNum));
			// turns
			for (int playerTurn = 0; playerTurn < nPlayers; playerTurn++) {
				display.printMessage("Player " + (playerTurn + 1) + "'s turn.");
				// rolls
				for (int pRollNum = 0; pRollNum < 3; pRollNum++) {
					display.waitForPlayerToClickRoll((playerTurn + 1));
					rollDice();
					display.printMessage("Select Die to Keep");
					if (pRollNum + 1 != 3) {
						display.waitForPlayerToSelectDice();
					}
				}
				display.printMessage("Select A Category");
				categorySelected = display.waitForPlayerToSelectCategory();
				checkCategory(playerTurn + 1);
			}
		}
		
	}
	
	public void rollDice() {
		for (int i = 0; i < dieResults.length; i++) {
			if (display.isDieSelected(i) == false) {
				dieResults[i] = rgen.nextInt(1, 6);
			}
		}
		display.displayDice(dieResults);
	}

	public void checkCategory(int playerNum) {
		sortFromLeast();
		if (categoriesSelected[categorySelected][playerNum] == 0) {
			int sum = 0;

			// looking for matches
			for (int i = 0; i < N_DICE + 1; i++) {
				dieMatches[i] = findMatchesInArray(i + 1, dieResults);
			}

			// scoring
			if (categorySelected < 7) {
				for (int i = 0; i < N_DICE; i++) {
					if (dieResults[i] == categorySelected) {
						sum += categorySelected;
					}
				}
			} else if (categorySelected == 9) {
				if ((findMatchesInArray(3, dieMatches) == 1)) {
					sum = IntStream.of(dieResults).sum();
				} else if (findMatchesInArray(4, dieMatches) == 1) {
					sum = IntStream.of(dieResults).sum();
				} else if (findMatchesInArray(5, dieMatches) == 1) {
					sum = IntStream.of(dieResults).sum();
				}
			} else if (categorySelected == 10) {
				if (findMatchesInArray(4, dieMatches) == 1) {
					sum = IntStream.of(dieResults).sum();
				} else if (findMatchesInArray(5, dieMatches) == 1) {
					sum = IntStream.of(dieResults).sum();
				}
			} else if (categorySelected == 11) {
				if ((findMatchesInArray(3, dieMatches) == 1) && (findMatchesInArray(2, dieMatches) == 1)) {
					sum = 25;
				}
			} else if (categorySelected == 12) {
				if (isSmallStraight() /* && categorySelected == 12 */) {
					sum = 30;
				}
			} else if (categorySelected == 13) {
				if (isLargeStraight() /* && categorySelected == 13 */) {
					sum = 40;
				}
			} else if (categorySelected == 14) {
				if (findMatchesInArray(5, dieMatches) == 1) {
					sum = 50;
				}
			} else if (categorySelected == 15) {
				sum = IntStream.of(dieResults).sum();
			} else {
				display.printMessage("Pick Again. That category was invalid.");
				categorySelected = display.waitForPlayerToSelectCategory();
			}

			// displaying and updating scores
			categoriesSelected[categorySelected][playerNum] = 1;
			scoreCard[categorySelected][playerNum] = sum;
			display.updateScorecard(categorySelected, playerNum, sum);
		} else {
			display.printMessage("This category's already been selected. Pick again.");
			categorySelected = display.waitForPlayerToSelectCategory();
			checkCategory(playerNum);
		}
	}

	// sorting

	private int findMatchesInArray(int num, int[] array) {
		int timesNumShowed = 0;
		for (int i = 0; i < array.length; i++) {
			if (num == array[i]) {
				timesNumShowed++;
			}
		}
		return timesNumShowed;
	}

	private void sortFromLeast() {
		resultsInOrder = Arrays.copyOf(dieResults, dieResults.length);
		Arrays.sort(resultsInOrder);
	}

	// straights

	private boolean isLargeStraight() {
		// possible large straights
		int[] lsOne = { 1, 2, 3, 4, 5 };
		int[] lsTwo = { 2, 3, 4, 5, 6 };
		if (Arrays.equals(resultsInOrder, lsOne)) {
			return true;
		} else if (Arrays.equals(resultsInOrder, lsTwo)) {
			return true;
		} else
			return false;
	}

	private boolean isSmallStraight() {
		if (findMatchesInArray(1, dieResults) >= 1 && findMatchesInArray(2, dieResults) >= 1
				&& findMatchesInArray(3, dieResults) >= 1 && findMatchesInArray(4, dieResults) >= 1) {
			return true;
		} else if (findMatchesInArray(2, dieResults) >= 1 && findMatchesInArray(3, dieResults) >= 1
				&& findMatchesInArray(4, dieResults) >= 1 && findMatchesInArray(5, dieResults) >= 1) {
			return true;
		} else if (findMatchesInArray(3, dieResults) >= 1 && findMatchesInArray(4, dieResults) >= 1
				&& findMatchesInArray(5, dieResults) >= 1 && findMatchesInArray(6, dieResults) >= 1) {
			return true;
		} else
			return false;
	}

	// calculating scores
	private int calcUpperScore(int playerNum) {
		int upperScore = 0;
		for (int i = 0; i < UPPER_SCORE; i++) {
			if (categoriesSelected[i][playerNum] == 1) {
				upperScore += scoreCard[i][playerNum];
				scoreCard[TOTAL][playerNum] += upperScore;
				scoreCard[UPPER_SCORE][playerNum] = upperScore;
			}
		}
		if (upperScore >= 63) {
			display.updateScorecard(UPPER_BONUS, playerNum, 35);
			scoreCard[TOTAL][playerNum] += 35;
			scoreCard[UPPER_BONUS][playerNum] = 35;
		}
		return upperScore;
	}

	private int calcLowerScore(int playerNum) {
		int lowerScore = 0;
		for (int i = UPPER_BONUS; i < LOWER_SCORE; i++) {
			if (categoriesSelected[i][playerNum] == 1) {
				lowerScore += scoreCard[i][playerNum];
				scoreCard[TOTAL][playerNum] += lowerScore;
				scoreCard[LOWER_SCORE][playerNum] = lowerScore;
			}
		}
		return lowerScore;
	}

	private void findWinner() {
		int winner = 0;
		int winnersScore = 0;
		 for(int i = 1; i <= nPlayers; i++) {
	            int a = scoreCard[TOTAL][i];
	            if( a > winnersScore) {
	            	winner = i;
	                winnersScore = a;
	            }
		 }
	     display.printMessage("The winner is " + playerNames[winner-1] + "! Congratulations.");
	}
}