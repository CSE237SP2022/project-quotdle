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
		String wrongCExpected     = 'c' + ANSI_RESET;
		String blankDExpected     = 'd' + ANSI_RESET;
		
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
				+ ' ' + 'c' + ANSI_RESET;
		
		assertEquals(guessExpected, guessActual);
	}
  
  @Test
	void testInvalidGuesses() {
		String[] guesses = {"salads", "explosions", "torandos", "", "ex"};
		Game currGame = new Game();
		
		for (String guess: guesses) {
			assertFalse(currGame.submitGuess(guess));
		}
		
	}
	
	@Test
	void testValidGuesses() {
		String[] guesses = {"afoul", "owner", "truly", "twice", "zowie"};
		Game currGame = new Game();
		
		for (String guess: guesses) {
			assertTrue(currGame.submitGuess(guess));
		}
		
//		assumes guess limit is 5
		String extraGuess = "abuse";
		assertFalse(currGame.submitGuess(extraGuess));	
	}
	
	@Test
	void stringifyWordleTest() {
		Game wordle = new Game();
	}
	
	
}
