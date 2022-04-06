package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.LetterState;
import quotdle.Quotdle;
import quotdle.Wordle;
import quotdle.GameStatus.gameStatus;
import quotdle.GameStatus;

class QuotdleTests {
	
	String[][] answersToTestWith = {{"well", "hello", "there"}  };//, {"catch", "me", "outside"}};
	int numberOfGuesses = 5;	
	
	int currentIndex;
	String currentAnswer;
	int currentNumberOfGuesses;
	int currentGuessNumber;
	Quotdle currentQuotdle;
	LetterState[][] currentGuesses;
	int currentNumberOfWordles;
	
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		String[] answer = answersToTestWith[(int)Math.floor(Math.random()*answersToTestWith.length)];
		int numberOfGuesses =  5 + (int)Math.random()*10;
		
		currentQuotdle = new Quotdle(answer, numberOfGuesses);
		currentAnswer = "";
		currentNumberOfGuesses = numberOfGuesses;
		currentGuessNumber = 0;
		currentIndex = 0;
		currentNumberOfWordles = answer.length;
		
		int totalAnswerLength = -1;
		for (int i = 0; i < answer.length; i++) {
			totalAnswerLength += answer[i].length() + 1;
			currentAnswer = currentAnswer + answer[i] + " ";
		}
		currentAnswer = currentAnswer.substring(0, currentAnswer.length() - 1);
		currentGuesses = new LetterState[numberOfGuesses][totalAnswerLength];
	}

	@Test
	void constructorTest() {
		for(int i = 0; i < 10; ++i) {
			String[] answer = answersToTestWith[(int)Math.floor(Math.random()*answersToTestWith.length)];
			Quotdle newQuotdle = new Quotdle(answer, 5 + (int)Math.random()*10);
		}
	}
	
	@Test 
	void getAnswerTest() {
		assertTrue(currentAnswer.equals(currentQuotdle.getAnswer()));
	}
	
	@Test 
	void getNumberOfGuesses() {
		assertEquals(currentNumberOfGuesses, currentQuotdle.getNumberOfGuesses(), 0.01);
	}
	
	@Test
	void getAnswerLengthTest() {
		assertEquals(currentAnswer.length(), currentQuotdle.getAnswerLength(), 0.01);
	}
	
	@Test
	void getAndSetFocusIndexTests() {		
		assertEquals(currentIndex, currentQuotdle.getFocusIndex(), 0.01);
		for(int i = -2*currentNumberOfWordles; i < 2*currentNumberOfWordles; i++) {
			currentQuotdle.setFocusIndex(i);
			int correctIndex = Math.max(0, Math.min(i, currentNumberOfWordles - 1));
			assertEquals(correctIndex, currentQuotdle.getFocusIndex(), 0.01);
		}
	}
	
	@Test
	void submitGuessAndGetStatusOngoingAndOutOfGuessesTest() {
		for(int i = 0; i < answersToTestWith.length; ++i) {
			String[] testAnswer = answersToTestWith[i];
			Quotdle testQuotdle = new Quotdle(testAnswer, 2*testAnswer.length);
			for(int j = 0; j < testAnswer.length; j++) {
				testQuotdle.setFocusIndex(j);
				LetterState[] badGuess1 = generateBadGuess(testAnswer[j].length());
				LetterState[] badGuess2 = generateBadGuess(testAnswer[j].length());
				while(badGuess1.equals(testAnswer[i])|| badGuess2.equals(testAnswer[i])) {
					badGuess1 = generateBadGuess(testAnswer[j].length());
					badGuess2 = generateBadGuess(testAnswer[j].length());
				}
				testQuotdle.submitGuess(badGuess1);
				assertEquals(testQuotdle.getCurrentGameStatus(), gameStatus.ongoing);
				testQuotdle.submitGuess(badGuess2);
				if(j != testAnswer.length - 1) {
					assertEquals(testQuotdle.getCurrentGameStatus(), gameStatus.ongoing);
				}
				else {
					assertEquals(testQuotdle.getCurrentGameStatus(), gameStatus.ranOutOfGuesses);
				}
			}
		}
		
	}
	
	@Test 
	void submitGuessAndStatusWonTest(){
		for(int i = 0; i < answersToTestWith.length; ++i) {
			String[] testAnswer = answersToTestWith[i];
			Quotdle testQuotdle = new Quotdle(testAnswer, 2*testAnswer.length);
			for(int j = 0; j < testAnswer.length; j++) {
				testQuotdle.setFocusIndex(j);
				testQuotdle.submitGuess(generateGoodGuess(testAnswer[j]));
				if(j != testAnswer.length - 1) {
					assertEquals(testQuotdle.getCurrentGameStatus(), gameStatus.ongoing);
				}
				else {
					assertEquals(testQuotdle.getCurrentGameStatus(), gameStatus.won);
				}
			}
		}
	}
	
	LetterState[] generateBadGuess(int guessLength) {
		LetterState[] badGuess = new LetterState[guessLength];
		for(int i = 0; i < guessLength; i++) {
			badGuess[i] = new LetterState(true);
		}
		return badGuess;
	}
	LetterState[] generateGoodGuess(String answer) {
		LetterState[] goodGuess = new LetterState[answer.length()];
		for(int i = 0; i < answer.length(); i++) {
			goodGuess[i] = new LetterState(answer.charAt(i));
		}
		return goodGuess;
	}
}






