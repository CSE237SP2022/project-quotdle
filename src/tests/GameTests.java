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
		String[] guesses = {"afoul", "owner", "truly", "twice", "zowie"};
		Game currGame = new Game();
		
		for (String guess: guesses) {
			assertTrue(currGame.handleInput(guess));
		}
		
//		assumes guess limit is 5
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
	void stringifyWordleTest() {
		Game wordle = new Game("after");
		String[] guessBlank = wordle.stringifyWordle();
		String[] expected = new String[6];
		for (int i = 0; i < expected.length; i++) {
			expected[i] = "░ ░ ░ ░ ░";
		}

		assertArrayEquals(expected, guessBlank);
		
		wordle.submitGuess("imply");
		String[] guessVeryWrong = wordle.stringifyWordle();
		expected[0] = "i m p l y";
		assertArrayEquals(expected, guessVeryWrong);
		
		wordle.submitGuess("angry");
		String[] guessWrong = wordle.stringifyWordle();
		expected[1] = ANSI_GREEN_BACKGROUND + "a"  + ANSI_RESET
				+ " " + "n" 
				+ " " + "g" 
				+ " " + ANSI_YELLOW_BACKGROUND + "r" + ANSI_RESET
				+ " " + "y" ;
		assertArrayEquals(expected, guessWrong);
		
		wordle.submitGuess("after");
		String[] guessRight = wordle.stringifyWordle();
		expected[2] = ANSI_GREEN_BACKGROUND + "a" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "f" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "t" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "e" + ANSI_RESET 
				+ " " + ANSI_GREEN_BACKGROUND + "r" + ANSI_RESET ;		
		assertArrayEquals(expected, guessRight);
	}
	
	@Test
	void colorKeyboardLetterTest() {
		Game wordle = new Game("after");
		char[] inputs = { 'a', 'b', 'c', 'x', 'y', 'z', ' ' , '\n' };
		
		String[] expected = { "a", "b", "c", "x", "y", "z", " ", "\n" };
		String[] actual = new String[expected.length];
		for (int i = 0; i < expected.length; i++) {
			actual[i] = wordle.colorKeyboardLetter(inputs[i]);
		}
		assertArrayEquals(expected, actual);
		
		wordle.submitGuess("agree");
		expected[0] = ANSI_GREEN_BACKGROUND + "a" + ANSI_RESET ;
		
		for (int i = 0; i < expected.length; i++) {
			actual[i] = wordle.colorKeyboardLetter(inputs[i]);
		}
		assertArrayEquals(expected, actual);
	}
	
	
}
