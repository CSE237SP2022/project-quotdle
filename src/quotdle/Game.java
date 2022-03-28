package quotdle;
import quotdle.LetterState.States;
import util.ArgsProcessor;

public class Game {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	// https://en.wikipedia.org/wiki/ANSI_escape_code
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

	private Wordle currentWordleGame; 
	
	public Game() {
		currentWordleGame = new Wordle(AnswerGenerator.generateNewWordle(), 5);
	}
	
	public boolean submitGuess(String guess) {
		
		if (guess.length() != 5) return false;
		if (!AnswerGenerator.getWordleList().contains(guess)) return false;
		if (currentWordleGame.getCurrentGuessNumber() >=5) return false; 
		
		
		LetterState[] guessAsLetterState = new LetterState[guess.length()];
		char[] guessChars = guess.toCharArray();
		
		for (int i = 0; i < guessChars.length; i++) {
			char guessChar = guessChars[i];
			guessAsLetterState[i] = new LetterState(guessChar);
		}
		
		currentWordleGame.submitGuess(guessAsLetterState);
		
		
		return true;
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArgsProcessor ap = new ArgsProcessor(args);
		Game currGame = new Game();
		
		String guess = ap.nextString("Provide a guess");
		
		while(!currGame.submitGuess(guess)) {
			guess = ap.nextString("Provide a guess");
		}
	}
	
	public static String stringifyGuess(LetterState[] guess) {
		String guessString = "";
		for (int i = 0; i < guess.length; i++) {
			guessString += colorLetter(guess[i]);
			if (i < guess.length-1) {
				guessString += " ";
			}
		}
		return guessString;
	}
	
	public static String colorLetter(LetterState letter) {
		switch (letter.state) {
		case correct:
			return ANSI_GREEN_BACKGROUND + letter.letter + ANSI_RESET;
		case misplaced:
			return ANSI_YELLOW_BACKGROUND + letter.letter + ANSI_RESET;
		default:
			return letter.letter + ANSI_RESET;
		}
	}
	
}
