package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.LetterState;
import quotdle.Wordle;

class WordleTests {
	
	String[] answersToTestWith = {"hello", "power", "smoke", "loose", "timid", "table", "chair", "frier", "rager", "hairy", "bowls", "taxed", "boast", "bound", "fluff", "puffs", "gassy", "ameer", "nacre", "omega", "pirai", "large", "letch", "adore", "whame", "skimp", "anode", "irade", "blent", "blunt", "scant"};
	int numberOfGuesses = 5;	
	
	Wordle currentWordle;
	String currentAnswer;
	int currentNumberOfGuesses;
	int currentGuessNumber;
	LetterState[][] currentGuesses;
	
	
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		String answer = answersToTestWith[(int)Math.floor(Math.random()*answersToTestWith.length)];
		int numberOfGuesses =  (int)Math.random()*10;
		
		currentWordle = new Wordle(answer, numberOfGuesses);
		currentAnswer = answer;
		currentNumberOfGuesses = numberOfGuesses;
		currentGuessNumber = 0;
		currentGuesses = new LetterState[numberOfGuesses][answer.toLowerCase().length()];

	}

	@Test
	void constructorTest() {
		for(int i = 0; i < 10; ++i) {
			String answer = answersToTestWith[(int)Math.floor(Math.random()*answersToTestWith.length)];
			Wordle newWordle = new Wordle(answer, (int)Math.random()*10);
		}
	}
	
	@Test
	void getAnswerTest() {
		assertTrue(currentAnswer.equals(currentWordle.getAnswer()));
	}
	
	@Test
	void getNumberOfGuessesTest() {
		assertEquals(currentNumberOfGuesses, currentWordle.getNumberOfGuesses(), 0.01);
	}
	
	@Test
	void getCurrentGuessNumberTest() {
		assertEquals(currentGuessNumber, currentWordle.getCurrentGuessNumber(), 0.01);
		while(currentWordle.getCurrentGuessNumber() < currentNumberOfGuesses) {
			
			
			LetterState[] sampleFiveLengthLetterState = new LetterState[5];
			for(int i = 0; i < 5; ++i) {
				char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
				sampleFiveLengthLetterState[i] = new LetterState(characters[(int)Math.floor(Math.random()*characters.length)]);
			}
			
			currentWordle.submitGuess(sampleFiveLengthLetterState);
			currentGuessNumber += 1;
			
			assertEquals(currentWordle.getCurrentGuessNumber(), currentGuessNumber, 0.01);
		}
	}
	
	@Test
	void getCurrentGuessesTest() {
//		assertTrue(currentGuesses.equals(currentWordle.getGuesses()));
		
//		while(currentWordle.getCurrentGuessNumber() < currentNumberOfGuesses) {
//			
//			
//			LetterState[] sampleFiveLengthLetterState = new LetterState[5];
//			for(int i = 0; i < 5; ++i) {
//				char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
//				sampleFiveLengthLetterState[i] = new LetterState(characters[(int)Math.floor(Math.random()*characters.length)]);
//			}
//			
//			currentWordle.submitGuess(sampleFiveLengthLetterState);
//			currentGuessNumber += 1;
//			
//			assertTrue(currentGuesses.equals(currentWordle.getGuesses()));
//
//		}
	}

	@Test
	void processGuessTest() {
		
		Wordle testWordle1 = new Wordle("crane", numberOfGuesses);
		
		LetterState[] guess = new LetterState[currentAnswer.length()];
		LetterState[] correctlyAssignedStatesGuess = new LetterState[currentAnswer.length()];
		
		guess[0] = new LetterState('h');
		guess[1] = new LetterState('o');
		guess[2] = new LetterState('r');
		guess[3] = new LetterState('s');
		guess[4] = new LetterState('e');

		correctlyAssignedStatesGuess[0] = new LetterState('h');
		correctlyAssignedStatesGuess[0].setState("wrong");
		correctlyAssignedStatesGuess[1] = new LetterState('o');
		correctlyAssignedStatesGuess[1].setState("wrong");
		correctlyAssignedStatesGuess[2] = new LetterState('r');
		correctlyAssignedStatesGuess[2].setState("misplaced");
		correctlyAssignedStatesGuess[3] = new LetterState('s');
		correctlyAssignedStatesGuess[3].setState("wrong");
		correctlyAssignedStatesGuess[4] = new LetterState('e');
		correctlyAssignedStatesGuess[4].setState("correct");

		assertFalse(testWordle1.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle1.getGuesses()[testWordle1.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
		
		guess[0] = new LetterState('a');
		guess[1] = new LetterState('r');
		guess[2] = new LetterState('r');
		guess[3] = new LetterState('a');
		guess[4] = new LetterState('y');

		correctlyAssignedStatesGuess[0] = new LetterState('a');
		correctlyAssignedStatesGuess[0].setState("misplaced");
		correctlyAssignedStatesGuess[1] = new LetterState('r');
		correctlyAssignedStatesGuess[1].setState("correct");
		correctlyAssignedStatesGuess[2] = new LetterState('r');
		correctlyAssignedStatesGuess[2].setState("wrong");
		correctlyAssignedStatesGuess[3] = new LetterState('a');
		correctlyAssignedStatesGuess[3].setState("wrong");
		correctlyAssignedStatesGuess[4] = new LetterState('y');
		correctlyAssignedStatesGuess[4].setState("wrong");

		assertFalse(testWordle1.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle1.getGuesses()[testWordle1.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
		
		guess[0] = new LetterState('c');
		guess[1] = new LetterState('r');
		guess[2] = new LetterState('a');
		guess[3] = new LetterState('n');
		guess[4] = new LetterState('e');

		correctlyAssignedStatesGuess[0] = new LetterState('c');
		correctlyAssignedStatesGuess[0].setState("correct");
		correctlyAssignedStatesGuess[1] = new LetterState('r');
		correctlyAssignedStatesGuess[1].setState("correct");
		correctlyAssignedStatesGuess[2] = new LetterState('a');
		correctlyAssignedStatesGuess[2].setState("correct");
		correctlyAssignedStatesGuess[3] = new LetterState('n');
		correctlyAssignedStatesGuess[3].setState("correct");
		correctlyAssignedStatesGuess[4] = new LetterState('e');
		correctlyAssignedStatesGuess[4].setState("correct");

		assertTrue(testWordle1.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle1.getGuesses()[testWordle1.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
		
		
		Wordle testWordle2 = new Wordle("puffs", numberOfGuesses);
		
		guess[0] = new LetterState('f');
		guess[1] = new LetterState('l');
		guess[2] = new LetterState('u');
		guess[3] = new LetterState('f');
		guess[4] = new LetterState('f');

		correctlyAssignedStatesGuess[0] = new LetterState('f');
		correctlyAssignedStatesGuess[0].setState("misplaced");
		correctlyAssignedStatesGuess[1] = new LetterState('l');
		correctlyAssignedStatesGuess[1].setState("wrong");
		correctlyAssignedStatesGuess[2] = new LetterState('u');
		correctlyAssignedStatesGuess[2].setState("misplaced");
		correctlyAssignedStatesGuess[3] = new LetterState('f');
		correctlyAssignedStatesGuess[3].setState("correct");
		correctlyAssignedStatesGuess[4] = new LetterState('f');
		correctlyAssignedStatesGuess[4].setState("wrong");

		assertFalse(testWordle2.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle2.getGuesses()[testWordle2.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));

		guess[0] = new LetterState('p');
		guess[1] = new LetterState('u');
		guess[2] = new LetterState('f');
		guess[3] = new LetterState('f');
		guess[4] = new LetterState('s');

		correctlyAssignedStatesGuess[0] = new LetterState('p');
		correctlyAssignedStatesGuess[0].setState("correct");
		correctlyAssignedStatesGuess[1] = new LetterState('u');
		correctlyAssignedStatesGuess[1].setState("correct");
		correctlyAssignedStatesGuess[2] = new LetterState('f');
		correctlyAssignedStatesGuess[2].setState("correct");
		correctlyAssignedStatesGuess[3] = new LetterState('f');
		correctlyAssignedStatesGuess[3].setState("correct");
		correctlyAssignedStatesGuess[4] = new LetterState('s');
		correctlyAssignedStatesGuess[4].setState("correct");

		assertTrue(testWordle2.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle2.getGuesses()[testWordle2.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
	}
	
	//just for testing processGuess
	boolean assertLetterStateArraysEqual(LetterState[] larr1, LetterState[] larr2) {
		boolean equal = true;
		if(larr1.length != larr2.length) {
			equal = false;
		}
		else {
			for(int i=0; i<larr1.length; i++) {
				System.out.println(larr1[i].getLetter() + ": " + larr1[i].getState() + ", " 
											+ larr2[i].getLetter() + ": " + larr2[i].getState());
				if(larr1[i].getLetter() != larr2[i].getLetter() || larr1[i].getState() != larr2[i].getState()) {
					equal = false;
				}
			}
		}
		System.out.println();
		return equal;
	}

}
