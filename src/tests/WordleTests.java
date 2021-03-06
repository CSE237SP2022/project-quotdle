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
	void processGuessTestCorrectAnswer() {
		Wordle testWordle = new Wordle("crane", numberOfGuesses);
		LetterState[] guess = new LetterState[currentAnswer.length()];
		
		guess[0] = new LetterState('c');
		guess[1] = new LetterState('r');
		guess[2] = new LetterState('a');
		guess[3] = new LetterState('n');
		guess[4] = new LetterState('e');

		assertTrue(testWordle.submitGuess(guess));
	}
	
	@Test
	void processGuessTestWrongAnswer() {
		Wordle testWordle = new Wordle("crane", numberOfGuesses);
		LetterState[] guess = new LetterState[currentAnswer.length()];
		
		guess[0] = new LetterState('h');
		guess[1] = new LetterState('o');
		guess[2] = new LetterState('r');
		guess[3] = new LetterState('s');
		guess[4] = new LetterState('e');

		assertFalse(testWordle.submitGuess(guess));
	}

	@Test
	void processGuessTestBlankGuess() {
		Wordle testWordle = new Wordle("crane", numberOfGuesses);
		LetterState[] guess = new LetterState[currentAnswer.length()];
		LetterState[] correctlyAssignedStatesGuess = new LetterState[currentAnswer.length()];
		
		guess[0] = new LetterState(' ');
		guess[1] = new LetterState(' ');
		guess[2] = new LetterState(' ');
		guess[3] = new LetterState(' ');
		guess[4] = new LetterState(' ');

		correctlyAssignedStatesGuess[0] = new LetterState(' ');
		correctlyAssignedStatesGuess[0].setState("blank");
		correctlyAssignedStatesGuess[1] = new LetterState(' ');
		correctlyAssignedStatesGuess[1].setState("blank");
		correctlyAssignedStatesGuess[2] = new LetterState(' ');
		correctlyAssignedStatesGuess[2].setState("blank");
		correctlyAssignedStatesGuess[3] = new LetterState(' ');
		correctlyAssignedStatesGuess[3].setState("blank");
		correctlyAssignedStatesGuess[4] = new LetterState(' ');
		correctlyAssignedStatesGuess[4].setState("blank");

		assertFalse(testWordle.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle.getGuesses()[testWordle.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
	}
	
	@Test
	void processGuessTestWrongAnswerState() {
		Wordle testWordle = new Wordle("crane", numberOfGuesses);
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

		assertFalse(testWordle.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle.getGuesses()[testWordle.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
	}
	
	@Test
	void processGuessTestWrongAnswerStateDuplicatesInAnswer() {
		Wordle testWordle = new Wordle("puffs", numberOfGuesses);
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
		correctlyAssignedStatesGuess[2].setState("wrong");
		correctlyAssignedStatesGuess[3] = new LetterState('s');
		correctlyAssignedStatesGuess[3].setState("misplaced");
		correctlyAssignedStatesGuess[4] = new LetterState('e');
		correctlyAssignedStatesGuess[4].setState("wrong");

		assertFalse(testWordle.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle.getGuesses()[testWordle.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
	}

	
	@Test
	void processGuessTestWrongAnswerStateDuplicatesInGuess() {
		Wordle testWordle = new Wordle("front", numberOfGuesses);
		LetterState[] guess = new LetterState[currentAnswer.length()];
		LetterState[] correctlyAssignedStatesGuess = new LetterState[currentAnswer.length()];
		
		guess[0] = new LetterState('f');
		guess[1] = new LetterState('l');
		guess[2] = new LetterState('u');
		guess[3] = new LetterState('f');
		guess[4] = new LetterState('f');

		correctlyAssignedStatesGuess[0] = new LetterState('f');
		correctlyAssignedStatesGuess[0].setState("correct");
		correctlyAssignedStatesGuess[1] = new LetterState('l');
		correctlyAssignedStatesGuess[1].setState("wrong");
		correctlyAssignedStatesGuess[2] = new LetterState('u');
		correctlyAssignedStatesGuess[2].setState("wrong");
		correctlyAssignedStatesGuess[3] = new LetterState('f');
		correctlyAssignedStatesGuess[3].setState("wrong");
		correctlyAssignedStatesGuess[4] = new LetterState('f');
		correctlyAssignedStatesGuess[4].setState("wrong");

		assertFalse(testWordle.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle.getGuesses()[testWordle.getCurrentGuessNumber()-1], 
					correctlyAssignedStatesGuess));
	}
	
	@Test
	void processGuessTestWrongAnswerStateDuplicatesInAnswerAndGuess() {
		Wordle testWordle = new Wordle("puffs", numberOfGuesses);
		LetterState[] guess = new LetterState[currentAnswer.length()];
		LetterState[] correctlyAssignedStatesGuess = new LetterState[currentAnswer.length()];
		
		
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

		assertFalse(testWordle.submitGuess(guess));
		assertTrue(assertLetterStateArraysEqual(testWordle.getGuesses()[testWordle.getCurrentGuessNumber()-1], 
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
//				System.out.println(larr1[i].getLetter() + ": " + larr1[i].getState() + ", " 
//											+ larr2[i].getLetter() + ": " + larr2[i].getState());
				if(larr1[i].getLetter() != larr2[i].getLetter() || larr1[i].getState() != larr2[i].getState()) {
					equal = false;
				}
			}
		}
		System.out.println();
		return equal;

	}

}
