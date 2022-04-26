package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.Game;
import quotdle.LetterState;
import quotdle.LetterState.States;
import quotdle.Wordle;


public class GameTests {	
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	
	@Test
	void colorCharTest() {
		LetterState correctA   = new LetterState('a', States.correct);
		LetterState misplacedB = new LetterState('b', States.misplaced);
		LetterState wrongC     = new LetterState('c', States.wrong);
		LetterState blankD     = new LetterState('d', States.blank);
		
		String correctAActual = Game.colorLetter(correctA);
		String misplacedBActual = Game.colorLetter(misplacedB);
		String wrongCActual = Game.colorLetter(wrongC);
		String blankDActual = Game.colorLetter(blankD);
		
		String correctAExpected   = ANSI_GREEN_BACKGROUND + 'a' + ANSI_RESET;
		String misplacedBExpected = ANSI_YELLOW_BACKGROUND + 'b' + ANSI_RESET;
		String wrongCExpected     = "c";
		String blankDExpected     = "d";
		
		assertEquals(correctAExpected, correctAActual);
		assertEquals(misplacedBExpected, misplacedBActual);
		assertEquals(wrongCExpected, wrongCActual);
		assertEquals(blankDExpected, blankDActual);
	}
	
	@Test
	void stringifyGuessTest() {
		LetterState correctA   = new LetterState('a', States.correct);
		LetterState misplacedB = new LetterState('b', States.misplaced);
		LetterState wrongC     = new LetterState('c', States.wrong);

		LetterState[] guess = { correctA, misplacedB, wrongC };
		String guessActual = Game.stringifyGuess(guess);
		
		String guessExpected = ANSI_GREEN_BACKGROUND + 'a' + ANSI_RESET 
				+ ' ' + ANSI_YELLOW_BACKGROUND + 'b' + ANSI_RESET
				+ ' ' + 'c';
		
		assertEquals(guessExpected, guessActual);
	}
  
	@Test
	void testInvalidGuesses() {
		String[] guesses = {"salads", "explosions", "torandos", "", "ex"};
		Game currGame = new Game();
		
		for (String guess: guesses) {
			assertFalse(currGame.handleInput(guess));
		}
		
	}
	
	@Test
	void testValidGuesses() {		
//		does not change to next word after getting one right
		String[] guesses = {"owner", "truly", "twice", "barge", "bards"};
		Game currGame = new Game(new String[]{"angry"});
		
		for (String guess: guesses) {
			assertFalse(currGame.handleInput(guess));
		}
		
		assertTrue(currGame.handleInput("afoul"));
		
	}
	
	@Test
	void testTooManyGuesses() {
		String[] guesses = {"afoul", "owner", "truly", "twice", "zowie", "zowie", "barge", "bards", "bigly", "abaft"};
		String[] answer = {"owner", "truly", "afoul"};
		Game currGame = new Game(answer);
		
		for (String input : guesses) {
			currGame.handleInput(input);
		}
		
		String extraGuess = "abuse";
		assertFalse(currGame.handleInput(extraGuess));	
	}
	
	@Test
	void testChangingFocusRight() {
		String[] inputs = {".", ".", ".", ".", "."};
		String[] answer = {"owner", "truly", "afoul"};
		Game currGame = new Game(answer);
		
		for (String input : inputs) {
			currGame.handleInput(input);
		}
		
		assertTrue(currGame.currentQuotdleGame.getFocusIndex() == 2);
	}
	
	@Test
	void testChangingFocusLeft() {
		String[] inputs = {",", ",", ",", ",", ","};
		String[] answer = {"owner", "truly", "afoul"};
		Game currGame = new Game(answer);
		
		for (String input : inputs) {
			currGame.handleInput(input);
		}
		
		assertTrue(currGame.currentQuotdleGame.getFocusIndex() == 0);
	}
	
	@Test
	void testChangingLeftAndRight() {
		String[] inputs = {".", ".", "truly", ",", "salad"};
		String[] answer = {"owner", "truly", "afoul"};
		Game currGame = new Game(answer);
		
		for (String input : inputs) {
			currGame.handleInput(input);
		}
		
		assertTrue(currGame.currentQuotdleGame.getFocusIndex() == 1);
	}
	
	
	@Test
	void stringifyWordleTest() {
		Game quotdle = new Game(new String[]{"after"});
		String[] guessBlank = Game.stringifyWordle(quotdle.currentQuotdleGame.getWordleGuesses(0), 0, true);
		String[] expected = new String[10];
		for (int i = 0; i < expected.length; i++) {
			expected[i] = "░ ░ ░ ░ ░";
		}

		assertArrayEquals(expected, guessBlank);
		
		quotdle.handleInput("imply");
		String[] guessVeryWrong = Game.stringifyWordle(quotdle.currentQuotdleGame.getWordleGuesses(0), 1, true);
		expected[0] = "i m p l y";
		assertArrayEquals(expected, guessVeryWrong);
		
		quotdle.handleInput("angry");
		String[] guessWrong = Game.stringifyWordle(quotdle.currentQuotdleGame.getWordleGuesses(0), 2, true);
		expected[1] = ANSI_GREEN_BACKGROUND + "a"  + ANSI_RESET
				+ " " + "n" 
				+ " " + "g" 
				+ " " + ANSI_YELLOW_BACKGROUND + "r" + ANSI_RESET
				+ " " + "y" ;
		assertArrayEquals(expected, guessWrong);
		
		quotdle.handleInput("after");
		String[] guessRight = Game.stringifyWordle(quotdle.currentQuotdleGame.getWordleGuesses(0), 3, true);
		expected[2] = ANSI_GREEN_BACKGROUND + "a" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "f" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "t" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "e" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "r" + ANSI_RESET ;		
		assertArrayEquals(expected, guessRight);
	}
	
	@Test
	void colorKeyboardLetterTest() {
		Game wordle = new Game(new String[]{"after"});
		char[] inputs = { 'a', 'b', 'c', 'x', 'y', 'z', ' ' , '\n' };
		
		String[] expected = { "a", "b", "c", "x", "y", "z", " ", "\n" };
		String[] actual = new String[expected.length];
		for (int i = 0; i < expected.length; i++) {
			actual[i] = wordle.colorKeyboardLetter(inputs[i]);
		}
		assertArrayEquals(expected, actual);
		
		wordle.handleInput("agree");
		expected[0] = ANSI_GREEN_BACKGROUND + "a" + ANSI_RESET ;
		
		for (int i = 0; i < expected.length; i++) {
			actual[i] = wordle.colorKeyboardLetter(inputs[i]);
		}
		assertArrayEquals(expected, actual);
	}
	
	
}
