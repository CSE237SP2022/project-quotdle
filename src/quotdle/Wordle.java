package quotdle;

public class Wordle {
	private int numberOfGuesses;
	private String answer;
	int currentGuessNumber;
	LetterState[][] Guesses;

	public Wordle(String answer, int numberOfGuesses){
		this.answer = answer.toLowerCase();
		this.numberOfGuesses = numberOfGuesses;
		this.currentGuessNumber = 0;
		this.Guesses = new LetterState[numberOfGuesses][answer.length()];
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public int getNumberOfGuesses() {
		return this.numberOfGuesses;
	}
	
	public LetterState[][] getGuesses() {
		return this.Guesses;
	}
	
	public int getCurrentGuessNumber() {
		return this.currentGuessNumber;
	}
	
	public boolean submitGuess(LetterState[] guess) {
		if(this.processGuess(guess)) {
			this.Guesses[currentGuessNumber] = guess;
			this.currentGuessNumber++;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean processGuess(LetterState[] guess) {
		//Daniel's method here
		return true;
	}

}
