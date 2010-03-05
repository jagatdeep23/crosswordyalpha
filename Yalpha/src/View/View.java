package View;

import java.util.ArrayList;

public class View {

	public View(){
	}
	public void printPuzzle(char[][] puzzle){
		for (int i = 0; i < puzzle.length; ++i){
			for (int j = 0; j < puzzle[i].length; ++j){
				System.out.print(puzzle[i][j] + " ");
			}
			System.out.println();
		}
	}

        public void printGreeting()
        {
            System.out.println("Welcome to Yalpha's puzzle! \nThe rules are:\nEvery word needs to be over 3 and under 12 letters in length. \nIf help needed type h.");
        }

	public void printWordList(ArrayList<String> words){
		System.out.println("Word list:");
		for (int i = 0; i < words.size(); ++i){
			System.out.println(words.get(i));
		}
	}
	public void printHelp(){
		System.out
		.println("Valid Commands:"
				+ "\na-A-add Add a word to be used in puzzle."
				+ "\ne-E-exit Exit the program."
				+ "\ng-G-generate Generate the puzzle and displays the result."
				+ "\nh-H-help Help display of commands."
				+ "\ni-I-import Import a word list from file."
				+ "\no-O-open Open a puzzle from file."
				+ "\np-P-print Print the current puzzle."
				+ "\nr-R-remove-d-D-delete Remove a word to be used for a puzzle."
				+ "\ns-S-save Save a word list to file.");
	}
}
