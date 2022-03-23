package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.Wordle;

class WordleTests {
	
	String[] answersToTestWith = {"hello", "power", "smoke", "loose", "table", "chair", "hairy", "bowls", "taxed", "boast", "bound", "fluff", "gassy", "ameer", "nacre", "omega", "pirai", "large", "letch", "adore", "whame", "skimp", "anode", "irade", "blent", "blunt", "scant"};
	int numberOfGuesses = 5;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void constructorTest() {
		for(int i = 0; i < 10; ++i) {
			String answer = answersToTestWith[(int)Math.floor(Math.random()*answersToTestWith.length)];
			Wordle newWordle = new Wordle(answer, (int)Math.random()*10);
		}
	}

}
