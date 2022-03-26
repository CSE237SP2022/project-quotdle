package quotdle;
import javax.swing.text.html.FormSubmitEvent;

import util.ArgsProcessor;

public class Game {

	private Wordle currentWordleGame; 
	
	
	public Game() {
		currentWordleGame = new Wordle(AnswerGenerator.generateNewWordle(), 5);
	}
	
	public boolean submitGuess(String guess) {   
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArgsProcessor ap = new ArgsProcessor(args);
		String guess = ap.nextString("Provide a guess");
		
		Game currGame = new Game();
		currGame.submitGuess(guess);

	}

}
