package quotdle;

public class Wordle {
	
	String answer;
	
	int numberAllowedGuesses;
	int currentGuess;
	
	LetterState[][] currentGuesses;
	LetterState[] keyboard;
	
	public String getAnswer() {
		return answer;
	}
	
	public LetterState[][] getGuesses() {
		return currentGuesses;
	}
	
	public void submitGuess(LetterState[] guess) {
		processGuess(guess);
	}
	
	private boolean processGuess(LetterState[] guess) {
		
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
