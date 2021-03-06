package quotdle;

public class LetterState {
	
	private String possibleLetters = "abcdefghijklmnopqrstuvwxyz";
	
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
	
	public LetterState(boolean useRandomChar) {
		if(useRandomChar) {
			letter = possibleLetters.charAt((int)Math.floor(Math.random()*possibleLetters.length()));
		}
		else {
			letter = 'a';
		}
		state = States.blank;
	}
	
	public boolean isBlank() {
		return state == States.blank;
	}
	
	//just for testing
	public char getLetter() {
		return letter;
	}

	//just for testing
	public States getState() {
		return state;
	}

	//just for testing
	public void setState(String stateName) {
		switch(stateName) {
		case "correct":
			this.state = States.correct;
			break;
		case "misplaced":
			this.state = States.misplaced;
			break;
		case "wrong":
			this.state = States.wrong;
			break;
		case "blank":
			this.state = States.blank;
			break;
		default: 
			this.state = States.blank;
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		return Character.toString(this.letter);// + ": " + this.state;
	}

}
