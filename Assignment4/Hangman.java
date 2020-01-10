/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;



public class Hangman extends ConsoleProgram {
	public void run() {
		println("Welcome to Hangman!");
		gameStart();
	}
	
	public void gameStart() {
		RandomGenerator rgen = RandomGenerator.getInstance();
		HangmanLexicon lexicon = new HangmanLexicon();
		word = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount()-1));
		lettersLeft = word.length();
		guessesLeft = 8;
		wordPrinted = concatNCopies(word.length(), "-");
		canvas.reset();
		canvas.displayWord(wordPrinted);
		gameLoop();
	}
	
	public String concatNCopies(int n, String str ) {
		String result = "";
		for (int i = 0; i < n; i++) {
			result += str;
		}
		return result;
	}
	
	public String lettersGuessed = "";
	
	public void gameLoop() {
		
		while (!((guessesLeft == 0) || (lettersLeft == 0))) {
			println("Your word looks like this " + wordPrinted);
			userInput = readLine("Guess A Letter: ");
			userInput = userInput.trim();
			userInput = userInput.toUpperCase();
			//validity
			if(userInput.length() > 1 || userInput.isEmpty() || userInput.isBlank() || Character.isDigit(userInput.charAt(0))) {
				println("You entered an invalid guess");
			}
			//correct answers
			
			else if (word.indexOf(userInput) >= 0 && lettersGuessed.indexOf(userInput) < 0) {
				for (int i = word.indexOf(userInput); i < word.length(); i++) {
					if (word.indexOf(userInput, i) >= 0) {
						wordPrinted = strReplace(word.indexOf(userInput,i), userInput, word.length());
					}
				}
				println("You guessed correctly!");
				println("You have " + guessesLeft + " guesses left.");
				canvas.displayWord(wordPrinted);
				lettersGuessed += userInput;
				canvas.noteCorrectGuess(userInput);
			}
			//wrong answers
			else {
				println("You Guessed Wrong.");
				guessesLeft--;
				println("You have " + guessesLeft + " guesses left.");
				canvas.noteIncorrectGuess(userInput);
			}
		}
		if (guessesLeft == 0) {
			println("You Lost The Game!");
			println("The word was " + word);
			askToPlay();
		}
		else if (lettersLeft == 0) {
			println("You Won The Game!");
			askToPlay();
		}
		
	}
	
	public String strReplace (int n, String str, int nStr) {
		String result = "";
		char tmp;
		lettersLeft = 0;
		for (int i = 0; i < nStr; i++) {
			tmp = wordPrinted.charAt(i);
			if (i == n) {
				result += str;
			}
			else if ( String.valueOf(tmp).compareTo("-") != 0){
				result += String.valueOf(tmp);
			}
			else {
				result += "-";
				lettersLeft++;
			}
		}
		return result;
	}
	
	public void askToPlay() {
		String playAgain = readLine("Would you like to play again? (Y/N) : ");
		playAgain = playAgain.toUpperCase();
		if (playAgain.compareTo("Y") == 0) {
			gameStart();
		}else if (playAgain.compareTo("N") == 0) {
			println("End of Game.");
		}else {
			askToPlay();
		}
	}
	
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
	private HangmanCanvas canvas;
	private String wordPrinted;
	private String word;
	private int lettersLeft;
	private int guessesLeft;
	private String userInput;
	
}