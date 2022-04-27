package tests;
import quotdle.AnswerGenerator;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.AnswerGenerator;

class AnswerGeneratorTests {
	
	
	@Test
	public void getRandomQuordleAnswerTest() {
		assertTrue(AnswerGenerator.getRandomQuote() instanceof String[]);
	}
	
	@Test
	public void getIndexQuordleAnswerTestType() {
		int index = 0;
		assertTrue(AnswerGenerator.getQuoteAtIndex(index) instanceof String[]);
	}
	
	@Test
	public void getIndexQuordleAnswerTestAccuracy() {
		int index = 0;
		String[] generatedAnswer = AnswerGenerator.getQuoteAtIndex(index);
		String[] correctAnswer = new String[]{"a", "momentary", "lapse"};
		if(generatedAnswer.length == correctAnswer.length) {
			//assert that all the strings and the order of strings are correct
			for(int i=0; i<generatedAnswer.length; i++) {
				assertEquals(generatedAnswer[i], correctAnswer[i]);
			}
		}
		else {	//if the lengths are not the same, this test fails
			assertTrue(false);
		}
	}

}
