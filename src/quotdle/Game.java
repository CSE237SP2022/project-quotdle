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
	private LetterState[] currentGuess;
	
	public Game() {
		int length = 5;
		int guesses = 6;
		currentWordleGame = new Wordle(AnswerGenerator.generateNewWordle(), guesses);
		currentGuess = new LetterState[length];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public String[] stringifyWordle() {
		String[] wordleOutput = new String[currentWordleGame.Guesses.length];
		int length = currentWordleGame.Guesses.length;
		
		for (int i = 0; i < length; i++) {
			if (i < currentWordleGame.currentGuessNumber) {
				wordleOutput[i] = stringifyGuess(currentWordleGame.Guesses[i]);
//			} else if (i == currentWordleGame.currentGuessNumber) {
//				wordleOutput[i] = stringifyGuess(currentGuess);
			} else {
				wordleOutput[i] = stringifyGuess(generateBlank(length));
			}
		}
		
		return wordleOutput;
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
	
	public static LetterState[] generateBlank(int length) {
		LetterState[] blank = new LetterState[length];
		
		for (int i = 0; i < length; i++) {
			blank[i] = new LetterState('░', States.blank);
		}
		
		return blank;
		
	}
	
}
