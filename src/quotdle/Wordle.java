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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
