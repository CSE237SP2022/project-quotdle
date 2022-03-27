package quotdle;

import java.util.Set;
import java.util.HashSet;

public class Wordle {
	private int numberOfGuesses;
	private String answer;
	int currentGuessNumber;
	LetterState[][] Guesses;
	LetterState[] keyboard;

	public Wordle(String answer, int numberOfGuesses){
		this.answer = answer.toLowerCase();
		this.numberOfGuesses = numberOfGuesses;
		this.currentGuessNumber = 0;
		this.Guesses = new LetterState[numberOfGuesses][answer.length()];
		this.keyboard = setUpKeyboard();
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public int getNumberOfGuesses() {
		return this.numberOfGuesses;
	}
	
	public LetterState[][] getGuesses() {
		return this.Guesses;
	}
	
	public int getCurrentGuessNumber() {
		return this.currentGuessNumber;
	}
	
	public boolean submitGuess(LetterState[] guess) {
		if(this.processGuess(guess)) {
			this.Guesses[currentGuessNumber] = guess;
			this.currentGuessNumber++;
			return true;
		}
		else {
			return false;
		}
	}
	
	//IMPORTANT NOTE: one potential future bug is:
	//if a state has been assigned to any letter in guess before being sent to processGuess, processGuess won't work correctly
	public boolean processGuess(LetterState[] guess) {
		
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

		//DELETE THESE PRINT STATEMENTS WHEN DONE TESTING
		
		//check if the guess is evaluated correctly
		System.out.println(answer);
		for(int g = 0; g < guess.length; g++) {
			System.out.print(guess[g].letter + ": ");
			System.out.println(guess[g].state);
		}
		System.out.println();
		
//		//check if the keyboard is updating correctly
//		for(LetterState key : keyboard) {
//			System.out.print(key.letter + ": ");
//			System.out.println(key.state);
//		}
//		System.out.println();

		return true;
	}
	
	
	private void assignState(LetterState[] guess, int letterIndex) {
		//guess at index letterIndex, answer at index letterIndex: are they the same?
		if(isCorrect(guess, letterIndex)) {
			//guess[letterIndex].state = States.correct;
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.correct);
		}
		//set state to misplaced if it is misplaced, wrong otherwise
		else if(isMisplaced(guess, letterIndex)){
			//guess[letterIndex].state = States.misplaced;
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.misplaced);
		}
		else {
			//guess[letterIndex].state = States.wrong;
			assignLetterStateUpdateKeyboard(guess[letterIndex], States.wrong);
		}
	}
	
	
	private boolean isCorrect(LetterState[] guess, int letterIndex) {
		return guess[letterIndex].letter == answer.charAt(letterIndex);
	}
	
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
	
	
	//words with duplicates must be handled differently - this method helps check if need to handle differently
	private boolean checkForDuplicateLetters(LetterState[] guess, LetterState character) {
		int count = 0;
		
		for (LetterState letter : guess) {
		    if (letter.letter == character.letter) {
		    	count++;
		    }
		}
		if(count > 1) {
			return true;
		}
		return false;
	}
	
	
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
	
	
	private int getCountDuplicatesInAnswer(LetterState dupLetter) {
		int count = 0;
		for (int i=0; i<answer.length(); i++) {
			if(answer.charAt(i) == dupLetter.letter) {
				count++;
			}
		}
		
		return count;
	}
	
	private int assignDuplicateCorrect(LetterState[] guess, LetterState guessLetter, 
			int countOfThisLetterInAnswer) {
		
		//assign correct to all duplicates in the right place
		for (int dupg = 0; dupg < guess.length; dupg++) {

			//is this one of the duplicates and has it not yet been assigned a state (state is blank when not yet assigned)?
			//don't want to reevaluate letters that have already been assigned a state
			if(guess[dupg].letter == guessLetter.letter && guess[dupg].state == States.blank) {
				
				//NOTE TO FUTURE SELF: THE PROBLEMS ARE 1. THIS METHOD DOESN'T WORK AND 2. assignDuplicateStates SHOULD
				//ASSIGN STATES FOR ALL OF THAT DUPLICATE LETTER, SEEMS TO BE DOING ONLY ONE RN
				
				//check if that duplicate is correct
				if(guess[dupg].letter == answer.charAt(dupg)) {
					//guess[dupg].state = States.correct;
					assignLetterStateUpdateKeyboard(guess[dupg], States.correct);
					
					//we have used up one of this letter in answer
					countOfThisLetterInAnswer--;
				}
			}
		}
		
		return countOfThisLetterInAnswer;
		
	}
	
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
	
	private void assignDuplicateWrong(LetterState[] guess, LetterState guessLetter) {
		for (int dupg = 0; dupg < guess.length; dupg++) {
			
			//is this one of the duplicates and has it not yet been assigned a state?
			//remaining duplicates that have blank state are wrong
			if(guess[dupg].letter == guessLetter.letter && guess[dupg].state == States.blank) {
				assignLetterStateUpdateKeyboard(guess[dupg], States.wrong);
			}
		}
	}
	
	
	//takes a LetterState and the state (one of States enum) to be assigned to it, assigns 
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
	
	
	private LetterState[] setUpKeyboard() {
		int keyboardLength = 26;
		LetterState[] keyboard = new LetterState[keyboardLength];
		for(char c='a'; c<='z'; ++c) {
			keyboard[c - 'a'] = new LetterState(c); 
		}
		return keyboard;
	}

}
