package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.LetterState;
import quotdle.Quotdle;
import quotdle.Wordle;

class QuotdleTests {
	
	String[][] answersToTestWith = {{"well", "hello", "there"}, {"catch", "me", "outside"}};
	int numberOfGuesses = 5;	
	
	int currentIndex;
	String currentAnswer;
	int currentNumberOfGuesses;
	int currentGuessNumber;
	Quotdle currentQuotdle;
	LetterState[][] currentGuesses;
	
	
	
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

}
