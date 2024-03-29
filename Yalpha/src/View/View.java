package View;

import java.util.ArrayList;

/**
 * Displays information and state of the model as well the puzzle.
 *
 * @author Team Yalpha, specifically Bob Grube and Matt Maze
 * @version 1.0
 */
public class View {

	public View(){
	}

        /**
         * Print the puzzle text
         * @param puzzle    matrix of characters from the model
         */
	public void printPuzzle(char[][] puzzle){
            if (puzzle.length > 1){
		for (int i = 0; i < puzzle.length; ++i){
			for (int j = 0; j < puzzle[i].length; ++j){
				System.out.print(puzzle[i][j] + " ");
			}
			System.out.println();
		}
            }
	}

        /**
         * Print wordsearch text
         * @param puzzle    matrix of characters from the model
         */

        public void printWordsearchSolution(char[][] puzzle){
            for(int i = 0; i < puzzle.length; ++i)
            {
                for(int j = 0; j < puzzle[0].length; ++j)
                {
                    if(puzzle[i][j] == ' ')
                        puzzle[i][j] = '-';
                }
            }
            printPuzzle(puzzle);
        }


        /**
         * Print crossword text
         * @param puzzle    matrix of characters from the model
         */
        public void printCrossword(char[][] puzzle)
        {
            for(int i = 0; i < puzzle.length; ++i)
            {
                for(int j = 0; j < puzzle[0].length; ++j)
                {
                    if(puzzle[i][j] == '~')
                        System.out.print("   ");
                    else
                        System.out.print("[ ]");
                }
                System.out.println();
            }
        }


        /**
         * Print crossword solution text
         * @param puzzle    matrix of characters from the model
         */
        public void printCrosswordSolution(char[][] puzzle){
            for(int i = 0; i < puzzle.length; ++i)
            {
                for(int j = 0; j < puzzle[0].length; ++j)
                {
                    if(puzzle[i][j] == '~')
                        System.out.print("   ");
                    else
                        System.out.print("[" + puzzle[i][j] + "]");
                }
                System.out.println();
            }
	}


        /**
         * Print the Yalpha greeting
         */
        public void printGreeting()
        {
            System.out.println("Welcome to Yalpha's puzzle! \nThe rules are:\nEvery word needs to be over 1 and under 20 letters in length. \nIf help needed type h.");
        }


        /**
         * Print the current list of words
         * @param words The list of words
         */
	public void printWordList(ArrayList<String> words){
		System.out.println("Word list:");
		for (int i = 0; i < words.size(); ++i){
			System.out.println(words.get(i));
		}
	}

        /**
         * Print the help information
         */
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
				+ "\ns-S-save Save a puzzle to file."
                                + "\nw-W-word Save a word list to file.");
	}
}
