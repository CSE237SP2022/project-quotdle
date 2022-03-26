package quotdle;
import javax.swing.text.html.FormSubmitEvent;

import util.ArgsProcessor;

public class Game {

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
		String guess = ap.nextString("Provide a guess");
		
		Game currGame = new Game();
		currGame.submitGuess(guess);

	}

}
