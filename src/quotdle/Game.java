package quotdle;
import java.util.LinkedList;
import quotdle.GameStatus.gameStatus;


import quotdle.LetterState.States;
import util.ArgsProcessor;

public class Game {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	// https://en.wikipedia.org/wiki/ANSI_escape_code
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

	public Quotdle currentQuotdleGame; 
	private LetterState[] currentGuess;
	
	public Game() {
		int guesses = 10;
		String[] answer = AnswerGenerator.getRandomQuote();
		currentQuotdleGame = new Quotdle(answer, guesses);
		currentGuess = new LetterState[currentQuotdleGame.getAnswerLength()];
	}
	
	public Game(String[] words) {
		
		int guesses = 10;
		currentQuotdleGame = new Quotdle(words, guesses);
		currentGuess = new LetterState[currentQuotdleGame.getAnswerLength()];

	}
	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArgsProcessor ap = new ArgsProcessor(args);
		Game currGame = new Game();
		
		String input = "";
		
		while(!currGame.handleInput(input)) {
			currGame.printQuotdle();
			currGame.printKeyboard();
			input =  ap.nextString("Provide a guess. Press ',' to switch to the word on the left and '.' to switch to the word on the right");
		}
    
		currGame.printQuotdle();
		currGame.printKeyboard();
		
		switch (currGame.getGameStatus()) {
			case won:
				System.out.println("YOU WON");
				break;
			case ranOutOfGuesses:
				System.out.println("Ran out of guesses : ( ");
				break;
			default:
				System.out.println("Game still ongoing");
				break;
		}
		
		
	}
	
	public gameStatus getGameStatus() {
		return currentQuotdleGame.getCurrentGameStatus();
	}
	
	private void incrementFocus() {
		currentQuotdleGame.setFocusIndex(currentQuotdleGame.getFocusIndex()+1);
	}
	
	private void decrementFocus() {
		currentQuotdleGame.setFocusIndex(currentQuotdleGame.getFocusIndex()-1);
	}
	
	private boolean submitGuess(String guess) {
		
		int focus = currentQuotdleGame.getFocusIndex();
		int expectedLength = currentQuotdleGame.getWordleGuesses(focus)[0].length;
		
		if (guess == null) return false;
		if (guess.length() != expectedLength) return false;
//		if (!AnswerGenerator.getWordleList().contains(guess)) return false;
		
		
		LetterState[] guessAsLetterState = new LetterState[guess.length()];
		char[] guessChars = guess.toCharArray();
		
		for (int i = 0; i < guessChars.length; i++) {
			char guessChar = guessChars[i];
			guessAsLetterState[i] = new LetterState(guessChar);
		}		
		
		boolean isGameDone = currentQuotdleGame.submitGuess(guessAsLetterState);
		return isGameDone;
	}
	
	public boolean handleInput(String input) {
		
		if (input.equals(".")) {
			incrementFocus();
			System.out.println("focus is now: " + currentQuotdleGame.getFocusIndex());
			return false;
		}
		
		if (input.equals(",")) {
			 decrementFocus();
			System.out.println("focus is now: " + currentQuotdleGame.getFocusIndex());
			 return false;
		}
		
		return submitGuess(input);
		
	}

	
	
	public void printQuotdle() {
		
		String[] out = stringifyQuotdle();
		for (int i = 0; i < out.length; i++) {
			System.out.println(out[i]);
		}

	}
	
	public String[] stringifyQuotdle() {
		boolean focus = (0 == currentQuotdleGame.getFocusIndex());
		String[] quotdleOutput = stringifyWordle(currentQuotdleGame.getWordleGuesses(0), currentQuotdleGame.getCurrentGuessNumber(), focus);
		for (int i = 1; i < currentQuotdleGame.getWordleCount(); i++) {
			focus = (i == currentQuotdleGame.getFocusIndex());
			LetterState[][] guesses = currentQuotdleGame.getWordleGuesses(i);
			String[] wordleOutput = stringifyWordle(guesses, currentQuotdleGame.getCurrentGuessNumber(), focus);
			mergeWordleStrings(quotdleOutput, wordleOutput);
		}
		return quotdleOutput;
	}
	
	public static String[] stringifyWordle(LetterState[][] guesses, int guessNumber, boolean focus) {
		int length = guesses.length;
		String[] wordleOutput = new String[length];
				
		for (int i = 0; i < length; i++) {
			if (i < guessNumber) {
				wordleOutput[i] = stringifyGuess(guesses[i]);
			} else {
				char c = getBlankCharacter(focus);
				wordleOutput[i] = stringifyGuess(generateBlank(guesses[i].length, c));
			}
		}
		
		return wordleOutput;
	}
	
	public static char getBlankCharacter(boolean focus) {
		if (focus) {
			return '░';
		} else {
			return '_';
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
			return Character.toString(letter.letter);
		}
	}
	
	public static LetterState[] generateBlank(int length, char c) {
		LetterState[] blank = new LetterState[length];
		
		for (int i = 0; i < blank.length; i++) {
			blank[i] = new LetterState(c, States.blank);				
		}
		
		return blank;
		
	}
	
	public void printKeyboard() {
		String keyboard = "Q W E R T Y U I O P\n" + 
						  " A S D F G H J K L\n" +
						  "   Z X C V B N M\n\n";
		
		for (Character c : keyboard.toCharArray()) {
			System.out.print(colorKeyboardLetter(c));
		}
		
	}
	
	public String colorKeyboardLetter(char c) {
		LetterState[] keyboard = currentQuotdleGame.keyboard;
		for (int i = 0; i < keyboard.length; i++) {
			if (Character.toLowerCase(c) == keyboard[i].letter) {
				return colorLetter(keyboard[i]);
			}
		}
		return Character.toString(c);
	}
	
	public static LetterState[] stringToLetterState(String word) {
		LetterState[] wordAsLetterState = new LetterState[word.length()];
		char[] wordChars = word.toCharArray();
		
		for (int i = 0; i < wordChars.length; i++) {
			char wordChar = wordChars[i];
			wordAsLetterState[i] = new LetterState(wordChar);
		}	
		return wordAsLetterState;
	}
	
	private static void mergeWordleStrings(String[] inA, String[] inB) {
		for (int i = 0; i < inA.length; i++) {
			inA[i] += "   " + inB[i];
		}
	}
	
}
