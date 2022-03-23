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
	
	public LetterState(char newLetter) {
		letter = newLetter;
		state = States.blank;
	}
	
	//delete this method when done with stupid printing debugging strategy (Daniel)
	public char getLetter() {
		return letter;
	}
	//delete this method when done with stupid printing debugging strategy (Daniel)
	public States getState() {
		return state;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
