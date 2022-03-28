package quotdle;

public class Game {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	// https://en.wikipedia.org/wiki/ANSI_escape_code
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// TODO make non static and remove parameter
	public static String[] stringifyWordle(Wordle wordle) {
		return null;
//		String[] expectedOutput;
//		for (LetterState[] guess : wordle.getGuesses()) {
//			String guessString;
//			for (LetterState letter : guess) {
//				
//			}
//		}
//		return expectedOutput;
	}
	
	public static String stringifyGuess(LetterState[] guess) {
		String guessString = "";
		for (int i = 0; i < guess.length; i++) {
			guessString += colorChar(guess[i]);
			if (i < guess.length-1) {
				guessString += " ";
			}
		}
		return guessString;
	}
	
	public static String colorChar(LetterState letter) {
		switch (letter.getState()) {
		case correct:
			return ANSI_GREEN_BACKGROUND + letter.getLetter() + ANSI_RESET;
		case misplaced:
			return ANSI_YELLOW_BACKGROUND + letter.getLetter() + ANSI_RESET;
		case wrong:
			return letter.getLetter() + ANSI_RESET;
		case blank:
			return letter.getLetter() + ANSI_RESET;	
		default:
			return "TODO ERROR";
		}
	}
	
}
