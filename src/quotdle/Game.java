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
		String[] answer = {AnswerGenerator.generateNewWordle(), AnswerGenerator.generateNewWordle(), AnswerGenerator.generateNewWordle()};
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
		
		String input = ap.nextString("Provide a guess. Press ',' to switch to the word on the leftand '.' to switch to the word on the right");
		
		while(!currGame.handleInput(input)) {
			currGame.printWordle();
			currGame.printKeyboard();
			input =  ap.nextString("Provide a guess. Press ',' to switch to the word on the leftand '.' to switch to the word on the right");
		}
		
		currGame.printWordle();
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
		
		if (guess == null) return false;
		if (guess.length() != 5) return false;
		if (!AnswerGenerator.getWordleList().contains(guess)) return false;
		
		
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
			return false;
		}
		
		if (input.equals(",")) {
			 decrementFocus();
			 return false;
		}
		
		return submitGuess(input);
		
	}
	

	
	public void printWordle() {
		for (String line : stringifyWordle()) {
			System.out.println(line);
		}
	}
	
	public String[] stringifyWordle() {
		String[] wordleOutput = new String[currentQuotdleGame.Guesses.length];
		int length = currentQuotdleGame.Guesses.length;
		
		for (int i = 0; i < length; i++) {
			if (i < currentQuotdleGame.getCurrentGuessNumber()) {
				wordleOutput[i] = stringifyGuess(currentQuotdleGame.Guesses[i]);
//			} else if (i == currentWordleGame.currentGuessNumber) {
//				wordleOutput[i] = stringifyGuess(currentGuess);
			} else {
				wordleOutput[i] = stringifyGuess(generateBlank());
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
			return Character.toString(letter.letter);
		}
	}
	
	public LetterState[] generateBlank() {
		LetterState[] blank = new LetterState[currentQuotdleGame.getAnswerLength()];
		
		for (int i = 0; i < blank.length; i++) {
			blank[i] = new LetterState('░', States.blank);
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
	
}
