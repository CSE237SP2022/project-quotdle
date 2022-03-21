package quotdle;

enum State {
	correct, misplaced, wrong, blank
}

public class LetterState {
	
	char letter;
	State state;

	public LetterState() {
		letter = 'a';
		state = State.blank;
	}
	
	public LetterState(char letter) {
		letter = letter;
		state = State.blank;
	}

}
