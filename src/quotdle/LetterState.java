package quotdle;

public class LetterState {
	
	public enum States {
		correct,
		misplaced,
		wrong,
		blank
	}
	
	char letter;
	
	States state;
	
	public LetterState() {
		letter = 'a';
		state = States.blank;
	}
	
	public LetterState(char newLetter) {
		letter = newLetter;
		state = States.blank;
	}
	
	public LetterState(char newLetter, States newState) {
		letter = newLetter;
		state = newState;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
