package quotdle;

enum States {
	correct,
	misplaced,
	wrong,
	blank
}

public class LetterState {
	
	char letter;
	
	States state;
	
	public LetterState() {
		letter = 'a';
		state = States.blank;
	}
	
	public LetterState(char letter) {
		letter = letter;
		state = States.blank;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
