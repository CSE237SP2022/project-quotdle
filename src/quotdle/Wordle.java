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
	//if a state has been assigned to any letter in guess before being sent to processGuess, it won't work correctly
	public boolean processGuess(LetterState[] guess) {
		
		//g is index in guess
		for(int g = 0; g < guess.length; g++) {
			
			//we don't want to reevaluate letters (duplicates may be evaluated before they're reached)
			if(guess[g].state == States.blank) {
				
				//is guess at index g a duplicate letter in guess?
				if(checkForDuplicateLetters(guess, guess[g])) {
					
					//how many times does this letter, which is a duplicate in guess, appear in answer?
					int countOfThisLetterInAnswer = 0;
					for (int i=0; i<answer.length(); i++) {
						if(answer.charAt(i) == guess[g].letter) {
							countOfThisLetterInAnswer++;
						}
					}
					
					//if there are any of this duplicate letter in answer...
					if(countOfThisLetterInAnswer > 0) {
						
						//first, assign correct to all duplicates in the right place
						for (int dupg = 0; dupg < guess.length; dupg++) {
							
							//is this one of the duplicates and has it not yet been assigned a state (state is blank when not yet assigned)?
							//don't want to reevaluate letters that have already been assigned a state
							if(guess[dupg].letter == guess[g].letter && guess[dupg].state == States.blank) {
								
								//check if that duplicate is correct
								if(guess[dupg].letter == answer.charAt(dupg)) {
									//guess[dupg].state = States.correct;
									assignState(guess[dupg], States.correct);
									
									//we have used up one of this letter in answer
									countOfThisLetterInAnswer--;
								}
							}
						}
					}	//end if(countOfThisLetterInAnswer > 0).
					//If countOfThisLetterInAnswer was 0, we don't need to check where guess at index g is correct. Instead, below
					//we assign wrong to those duplicates.
						
					//next, assign misplaced to remaining duplicates, until duplicates in answer have been exhausted. then, assign wrong.
					for (int dupg = 0; dupg < guess.length; dupg++) {
						
						//is this one of the duplicates and has it not yet been assigned a state?
						//don't want to reevaluate letters that have already been assigned a state
						if(guess[dupg].letter == guess[g].letter && guess[dupg].state == States.blank) {
							
							//if there are more of the duplicate letter in answer, then for each of that letter in guess that is 
							//not correct (we filtered for this by assigning correct to the correct ones, so remaining should 
							//be blank still), assign misplaced. once there are no more of the duplicate letter in answer, assign wrong to the rest.
							if(countOfThisLetterInAnswer > 0) {
								//guess[dupg].state = States.misplaced;
								assignState(guess[g], States.misplaced);
								countOfThisLetterInAnswer--;
							}
							//if there are no more of this letter in answer, the remaining duplicates in guess are wrong
							else {
								//guess[dupg].state = States.wrong;
								assignState(guess[g], States.wrong);
							}
							
						}
					}
				}
				//if guess at index g is not a duplicate letter in guess
				else {
				
					//guess at index g, answer at index g: are they the same?
					if(guess[g].letter == answer.charAt(g)) {
						//guess[g].state = States.correct;
						assignState(guess[g], States.correct);
					}
					//they are not the same
					else {
						//does the letter in guess at index g exist elsewhere in the word?
						boolean misplaced = false;
						//a is index in answer
						for(int a = 0; a < answer.length(); a++) {
							//if at any point, the letter in guess at index g matches a letter in the answer, it is misplaced
							if(guess[g].letter == answer.charAt(a)) {
								misplaced = true;
							}
						}
						//set state to misplaced if it is misplaced, wrong otherwise
						if(misplaced) {
							//guess[g].state = States.misplaced;
							assignState(guess[g], States.misplaced);
						}
						else {
							//guess[g].state = States.wrong;
							assignState(guess[g], States.wrong);
						}
					}
					
				}
			}
		}

//		//check if the guess is evaluated correctly
//		System.out.println(answer);
//		for(int g = 0; g < guess.length; g++) {
//			System.out.print(guess[g].letter + ": ");
//			System.out.println(guess[g].state);
//		}
//		System.out.println();
//		
//		//check if the keyboard is updating correctly
//		for(LetterState key : keyboard) {
//			System.out.print(key.letter + ": ");
//			System.out.println(key.state);
//		}
//		System.out.println();

		return true;
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
	
	//maybe another function that takes a LetterState and a state (one of States enum), assigns that state to
	//the LetterState and also adjusts the keyboard of this wordle
	private void assignState(LetterState letter, States state) {
		letter.state = state;
		for(LetterState key : keyboard) {
			if(key.state == States.blank && key.letter == letter.letter) {
				key.state = state;
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
