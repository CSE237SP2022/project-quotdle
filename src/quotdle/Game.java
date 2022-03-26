package quotdle;
import util.ArgsProcessor;

public class Game {

	private Wordle currentWordleGame; 
	
	
	public Game() {
		currentWordleGame = new Wordle(AnswerGenerator.generateNewWordle(), 5);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
