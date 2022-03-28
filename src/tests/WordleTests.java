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
		
		LetterState[] guess = new LetterState[currentAnswer.length()];
		guess[0] = new LetterState('h');
		guess[1] = new LetterState('o');
		guess[2] = new LetterState('r');
		guess[3] = new LetterState('s');
		guess[4] = new LetterState('e');
		
//		assertTrue(currentWordle.processGuess(guess));
		
		guess[0] = new LetterState('a');
		guess[1] = new LetterState('r');
		guess[2] = new LetterState('r');
		guess[3] = new LetterState('a');
		guess[4] = new LetterState('y');
		
//		assertTrue(currentWordle.processGuess(guess));
		
		guess[0] = new LetterState('i');
		guess[1] = new LetterState('c');
		guess[2] = new LetterState('i');
		guess[3] = new LetterState('n');
		guess[4] = new LetterState('g');
		
//		assertTrue(currentWordle.processGuess(guess));

		guess[0] = new LetterState('f');
		guess[1] = new LetterState('l');
		guess[2] = new LetterState('u');
		guess[3] = new LetterState('f');
		guess[4] = new LetterState('f');
		
//		assertTrue(currentWordle.processGuess(guess));
	}

}
