package tests;

import quotdle.Game;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTests {
	
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
	


}
