package quotdle;

public class Wordle {
	
	String answer;
	int numberAllowedGuesses;
	int currentGuess;
	LetterState[][] currentGuesses;
	LetterState[] keyboard;
	
	String getAnswer() {
		return answer;
	}
	
	LetterState[][] getGuesses() {
		return currentGuesses;
	}
	
	void submitGuess(LetterState[] guess) {
		boolean didthiswork = processGuess(guess);
	}
	
	boolean processGuess(LetterState[] guess) {
		//Daniel: computation here
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
