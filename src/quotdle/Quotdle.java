package quotdle;

import java.util.Set;

import quotdle.GameStatus.gameStatus;
import quotdle.LetterState.States;

import java.lang.reflect.Array;
import java.util.HashSet;

public class Quotdle {
	
	private String[] answer;
	private int numberOfGuesses;
	private Wordle[] wordles;
	private int focusIndex;
	private int currentGuessNumber;
	
	//The variables below here will be returned by calling the wordles, instead of storing here at quotdle
//	boolean gameDone;
//	gameStatus status;
	LetterState[][] Guesses; //each guess will contain the most recent guess for each Wordle, separated by spaces
	
	LetterState[] keyboard;

	public Quotdle(String[] answer, int numberOfGuesses){
		this.focusIndex = 0;
		this.answer = answer;
		this.wordles = new Wordle[answer.length];
		int totalAnswerLength = -1;
		for (int i = 0; i < answer.length; i++) {
			wordles[i] = new Wordle(answer[i].toLowerCase(), numberOfGuesses);
			totalAnswerLength += answer[i].length() + 1;
		}		
		this.numberOfGuesses = numberOfGuesses;
		this.currentGuessNumber = 0;
		this.keyboard = setUpKeyboard();
		
		this.Guesses = new LetterState[numberOfGuesses][totalAnswerLength];
		for(int i = 0; i < this.Guesses.length; ++i) {
			for(int j = 0; j < totalAnswerLength; ++j) {
				this.Guesses[i][j] = new LetterState(' ');
			}
		}
	}
	
	public String getAnswer() {
		return String.join(" ", this.answer);
	}
	
	public int getNumberOfGuesses() {
		return this.numberOfGuesses;
	}
	
	public int getAnswerLength() {
		int length = -1;
		for(int i = 0; i < answer.length; i++) {
			length += answer[i].length() + 1;
		}
		return length;
	}
	
	public LetterState[][] getGuesses(){
		return this.Guesses;
	}
	
	public void setFocusIndex(int newFocusIndex) {
		if(newFocusIndex < 0) {
			newFocusIndex = 0;
		}
		else if(newFocusIndex >= this.wordles.length) {
			newFocusIndex = this.wordles.length -1;
		}
		this.focusIndex = newFocusIndex;
	}
	
	public int getFocusIndex() {
		return this.focusIndex;
	}
	
	public boolean submitGuess(LetterState[] guess) {
		boolean toReturn = true;
		for(int i = 0; i < this.wordles.length; ++i) {
			if(i == this.focusIndex) {
				toReturn = toReturn && this.wordles[i].submitGuess(guess);
			}
			else {
				toReturn = toReturn && this.getAndResubmitOldGuess(i);
			}
			LetterState[][] pastGuesses = this.wordles[i].getGuesses();
			System.out.println("Guesses for wordle index: " + i);
			this.printLSDoubleArray(pastGuesses);
		}
		this.updateGuesses(guess);
		return toReturn;
	}
	private LetterState[] cloneLastGuess() {
		LetterState[] clonedGuess = new LetterState[this.Guesses[this.currentGuessNumber].length];
		if(this.currentGuessNumber > 0) {
			clonedGuess = this.Guesses[this.currentGuessNumber - 1].clone();
		}
		else {
			for(int i = 0; i < clonedGuess.length; ++i) {
				clonedGuess[i] = new LetterState(' ');
			}
		}
		return clonedGuess;
	}
	
	private void updateGuesses(LetterState[] guess) {
		LetterState[] newGuess = this.cloneLastGuess();
		
		int startOfUpdate = 0;
		for(int j = 0; j < focusIndex; j++) {
			startOfUpdate += this.answer[j].length() + 1;
		}
		
		for(int i = 0; i < guess.length; ++i) {
			newGuess[startOfUpdate + i] = guess[i];
		}
		
		this.Guesses[this.currentGuessNumber++] = newGuess;

		System.out.println("__________________________");
		System.out.println("Current this.guesses:");
		this.printLSDoubleArray(Guesses);
	}
	
	
	private boolean getAndResubmitOldGuess(int wordleIndex) {
		if(this.currentGuessNumber > 0) {
			LetterState[][] pastGuesses = this.wordles[wordleIndex].getGuesses();
			LetterState[] mostRecentGuess = pastGuesses[this.currentGuessNumber - 1];
			return this.wordles[wordleIndex].submitGuess(mostRecentGuess);
		}
		else {
			return this.wordles[wordleIndex].submitGuess(generateBlankGuess(this.wordles[wordleIndex].getAnswerLength()));
		}
	}
	
	private LetterState[] generateBlankGuess(int guessLength) {
		LetterState[] toReturn = new LetterState[guessLength];
		for(int i = 0; i < guessLength; i++) {
			toReturn[i] = new LetterState(' ');
		}
		return toReturn;
	}
	
	public int getCurrentGuessNumber() {
		return this.currentGuessNumber;
	}
	
	public gameStatus getCurrentGameStatus() {
		gameStatus[] statuses = new gameStatus[this.wordles.length];
		for(int i = 0; i < wordles.length; ++i) {
			statuses[i] = this.wordles[i].getCurrentGameStatus();
		}
		return combineGameStatuses(statuses);
	}
	
	private gameStatus combineGameStatuses(gameStatus[] statuses) {
		gameStatus status = gameStatus.won;
		for(int i = 0; i < statuses.length; ++i) {
			if(statuses[i] == gameStatus.ongoing) {
				status = gameStatus.ongoing;
			}
			else if(statuses[i] == gameStatus.ranOutOfGuesses) {
				return gameStatus.ranOutOfGuesses;
			}
		}
		return status;
	}
	
	//initializes the keyboard for this Quotdle
	private LetterState[] setUpKeyboard() {
		int keyboardLength = 26;
		LetterState[] keyboard = new LetterState[keyboardLength];
		for(char c='a'; c<='z'; ++c) {
			keyboard[c - 'a'] = new LetterState(c); 
		}
		return keyboard;
	}
	
	private void printLSArray(LetterState[] ls) {
		String toPrint = "[";
		for(int k = 0; k < ls.length; ++k) {
			toPrint = toPrint + ls[k].toString() + ", ";
		}
		System.out.println(toPrint.substring(0, toPrint.length() - 2) + "]");
	}
	
	
	private void printLSDoubleArray(LetterState[][] ls) {
		for(int i = 0; i < ls.length; ++i) {
			this.printLSArray(ls[i]);
		}	
	}

}
