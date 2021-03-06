package quotdle;

import java.util.Set;

import quotdle.GameStatus.gameStatus;
import quotdle.LetterState.States;

import java.util.HashSet;

public class Wordle {
	
	
	private int numberOfGuesses;
	private String answer;
	gameStatus status;
	boolean gameDone;
	int currentGuessNumber;
	LetterState[][] Guesses;
	LetterState[] keyboard;

	public Wordle(String answer, int numberOfGuesses){
		this.answer = answer.toLowerCase();
		this.numberOfGuesses = numberOfGuesses;
		this.gameDone = false;
		this.status = gameStatus.ongoing;
		this.currentGuessNumber = 0;
		this.Guesses = new LetterState[numberOfGuesses][answer.length()];
		for(int i = 0; i < this.Guesses.length; ++i) {
			for(int j = 0; j < this.Guesses[i].length; ++j) {
				this.Guesses[i][j] = new LetterState('_');
			}
		}
		this.keyboard = setUpKeyboard();
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public int getNumberOfGuesses() {
		return this.numberOfGuesses;
	}
	
	public int getAnswerLength() {
		return getAnswer().length();
	}
	
	public LetterState[][] getGuesses() {
		return this.Guesses;
	}
	
	public int getCurrentGuessNumber() {
		return this.currentGuessNumber;
	}
	
	public gameStatus getCurrentGameStatus() {
		return this.status;
	}
	
	public boolean submitGuess(LetterState[] guess) {
//		System.out.print("Guess submitted for wordle with answer: " + this.answer + " ");
		this.printLSArray(guess);
		if (status == gameStatus.ranOutOfGuesses) {
			return gameDone;
		}

		this.processGuess(guess);
		this.Guesses[currentGuessNumber] = guess;
		this.currentGuessNumber++;
		return gameDone;
	}
	
	private void printLSArray(LetterState[] ls) {
		String toPrint = "[";
		for(int k = 0; k < ls.length; ++k) {
			toPrint = toPrint + ls[k].toString() + ", ";
		}
		System.out.println(toPrint.substring(0, toPrint.length() - 2) + "]");
	}


	//assigns states to guess based on this Wordle's answer
	//IMPORTANT NOTE: guesses must be sent with all LetterStates having state = States.blank
	public void processGuess(LetterState[] guess) {
		//blank guesses should return all States.blank
		if(processBlankGuess(guess)) { return; }
		
		//g is index in guess
		for(int g = 0; g < guess.length; g++) {
			
			//we don't want to reevaluate letters (duplicates may be evaluated before they're reached)
			if(guess[g].state == States.blank) {
				
				//is guess at index g a duplicate letter in guess?
				if(checkForDuplicateLetters(guess, guess[g])) {
					assignDuplicateStates(guess, g);
				}
				//if guess at index g is not a duplicate letter in guess
				else {
					assignState(guess, g);
				}
			}
		}
		
		if (this.currentGuessNumber >= this.numberOfGuesses-1) {
			gameDone = true;
			status = gameStatus.ranOutOfGuesses;
		}
		
		//return true if the guess is correct (indicates that the game is over)
		if (correctGuess(guess)) {
			gameDone = true;
			status = gameStatus.won;
		}


	}
	
	private boolean processBlankGuess(LetterState[] guess) {
		//check if guess is all blank. If it is not, return false
		for(int g = 0; g < guess.length; g++) {
			if(guess[g].letter != ' ') {
				return false;
			}
		}
		//if guess is all blank, set the state of each letter to States.blank and return true
		for(int g = 0; g < guess.length; g++) {
			guess[g].state = States.blank;
		}
		return true;
	}
	
	//assigns state to given LetterState (at index letterIndex in guess) (for non-duplicate letters)
	private void assignState(LetterState[] guess, int letterIndex) {
		//guess at index letterIndex, answer at index letterIndex: are they the same?
		if(isCorrect(guess, letterIndex)) {
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.correct);
		}
		//set state to misplaced if it is misplaced, wrong otherwise
		else if(isMisplaced(guess, letterIndex)){
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.misplaced);
		}
		else {
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.wrong);
		}
	}
	
	//returns true if the LetterState at index letterIndex in guess is in the correct position
	private boolean isCorrect(LetterState[] guess, int letterIndex) {
		
		if (letterIndex >= answer.length()) {
			return false;
		}
		
		return guess[letterIndex].letter == answer.charAt(letterIndex);
	}

	//returns true if the LetterState at index letterIndex in guess is not in the correct position, but
	//is found elsewhere in the answer
	private boolean isMisplaced(LetterState[] guess, int letterIndex) {
		boolean misplaced = false;
		//a is index in answer
		for(int a = 0; a < answer.length(); a++) {
			//if at any point, the letter in guess at index letterIndex matches a letter in the answer, it is misplaced
			if(guess[letterIndex].letter == answer.charAt(a)) {
				misplaced = true;
			}
		}
		return misplaced;
	}
	
	
	//words with duplicates must be handled differently - this method checks if character has duplicates within guess
	private boolean checkForDuplicateLetters(LetterState[] guess, LetterState character) {
		int countDuplicates = 0;
		
		for (LetterState letter : guess) {
		    if (letter.letter == character.letter) {
		    	countDuplicates++;
		    }
		}
		if(countDuplicates > 1) {
			return true;
		}
		return false;
	}
	

	//assigns state to all duplicates of given LetterState (at index letterIndex in guess)
	private void assignDuplicateStates(LetterState[] guess, int duplicateLetterIndex) {
		
		//how many times does this letter, which is a duplicate in guess, appear in answer?
		int countOfThisLetterInAnswer = getCountDuplicatesInAnswer(guess[duplicateLetterIndex]);

		//first, assign correct to all duplicates in the right place
		if(countOfThisLetterInAnswer > 0) {	//if there are any of this duplicate letter in answer...
			//assignDuplicateCorrect returns the assignDuplicateCorrect *excluding* those that are correct
			countOfThisLetterInAnswer = assignDuplicateCorrect(guess, guess[duplicateLetterIndex], countOfThisLetterInAnswer);
			
		}	//end if(countOfThisLetterInAnswer > 0).
		//If countOfThisLetterInAnswer was 0, we don't need to check where guess at index g is correct. Instead, below
		//we assign wrong to those duplicates.

		//next, assign misplaced to remaining duplicates, until duplicates in answer have been exhausted. then, assign wrong.
		assignDuplicateMisplacedWrong(guess, guess[duplicateLetterIndex], countOfThisLetterInAnswer);
		
	}
	
	//count how many times dupLetter appears in answer
	private int getCountDuplicatesInAnswer(LetterState dupLetter) {
		int count = 0;
		for (int i=0; i<answer.length(); i++) {
			if(answer.charAt(i) == dupLetter.letter) {
				count++;
			}
		}
		return count;
	}
	
	//assign correct to all of the duplicate letter that are in the right place
	private int assignDuplicateCorrect(LetterState[] guess, LetterState guessLetter, 
			int countOfThisLetterInAnswer) {
		
		//assign correct to all duplicates in the right place
		for (int dupg = 0; dupg < guess.length; dupg++) {

			//is this one of the duplicates and has it not yet been assigned a state (state is blank when not yet assigned)?
			//don't want to reevaluate letters that have already been assigned a state
			if(guess[dupg].letter == guessLetter.letter && guess[dupg].state == States.blank) {
				
				//check if that duplicate is correct
				if(guess[dupg].letter == answer.charAt(dupg)) {
					assignLetterStateUpdateKeyboard(guess[dupg], States.correct);
					
					//we have used up one of this letter in answer
					countOfThisLetterInAnswer--;
				}
			}
		}
		
		return countOfThisLetterInAnswer;
		
	}

	//assign misplaced to all of the duplicate letter that are in answer but not in the right place
	private void assignDuplicateMisplacedWrong(LetterState[] guess, LetterState guessLetter, 
			int countOfThisLetterInAnswer) {
		
		//assign misplaced to remaining duplicates, until duplicates in answer have been exhausted. then, assign wrong.
		for (int dupg = 0; dupg < guess.length; dupg++) {
			
			//is this one of the duplicates and has it not yet been assigned a state?
			//don't want to reevaluate letters that have already been assigned a state
			if(guess[dupg].letter == guessLetter.letter && guess[dupg].state == States.blank) {

				//if there are more of the duplicate letter in answer, then for each of that letter in guess that is 
				//not correct (we filtered for this by assigning correct to the correct ones, so remaining should 
				//be blank still), assign misplaced. once there are no more of the duplicate letter in answer, assign wrong to the rest.
				if(countOfThisLetterInAnswer > 0 && isMisplaced(guess, dupg)) {
					assignLetterStateUpdateKeyboard(guess[dupg], States.misplaced);
					countOfThisLetterInAnswer--;
				}
			}
		}
		
		//the remaining duplicates in guess are wrong (remaining if have not been assigned a state yet, meaning
		//there are no more of that letter in answer)
		assignDuplicateWrong(guess, guessLetter);
	}

	//assign wrong to all remaining of the duplicate letter that still have state blank
	private void assignDuplicateWrong(LetterState[] guess, LetterState guessLetter) {
		for (int dupg = 0; dupg < guess.length; dupg++) {
			
			//is this one of the duplicates and has it not yet been assigned a state?
			//remaining duplicates that have blank state are wrong
			if(guess[dupg].letter == guessLetter.letter && guess[dupg].state == States.blank) {
				assignLetterStateUpdateKeyboard(guess[dupg], States.wrong);
			}
		}
	}
	
	
	//takes a LetterState and the state (one of States enum) to be assigned to the LetterState, assigns 
	//that state to the LetterState and adjusts the keyboard of this Wordle
	private void assignLetterStateUpdateKeyboard(LetterState letter, States state) {
		//assign state to the LetterState
		letter.state = state;
		
		//adjust the keyboard to reflect the results
		for(LetterState key : keyboard) {
			
			//for the key in keyboard representing this letter...
			if(key.letter == letter.letter &&
						//if the key does not yet have an assigned state, or
						( key.state == States.blank || 
							//if the key was misplaced and the new state is correct...
							(key.state == States.misplaced && state == States.correct) 
						)
					) {
				key.state = state;	//assign the new state to the key
			}
			
		}
	}
	
	private boolean correctGuess(LetterState[] guess) {
		boolean correct = true;
		for(LetterState letter : guess) {
			if(letter.state != States.correct) {
				correct = false;
			}
		}
		return correct;
	}
	
	
	//initializes the keyboard for this Wordle
	private LetterState[] setUpKeyboard() {
		int keyboardLength = 26;
		LetterState[] keyboard = new LetterState[keyboardLength];
		for(char c='a'; c<='z'; ++c) {
			keyboard[c - 'a'] = new LetterState(c); 
		}
		return keyboard;
	}

}
