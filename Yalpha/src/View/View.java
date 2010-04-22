package View;

import java.util.ArrayList;
import Model.WordMap;
import Model.Word;

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
         *
         * @param map       Wordmap that stores all letter locations
         * @param numRows   num of rows
         * @param numCols   num of columns
         * @return          returns a character array of the puzzle solution
         */
        public char[][] createSolution(WordMap map, int numRows, int numCols) {
            Word[] arrayMap = new Word[map.size()];
            map.toArray(arrayMap);

            char[][] puzzle = new char[numRows][numCols];

            for(int word = 0; word <= arrayMap.length; ++word)
            {
                for(int letter = 0; letter < arrayMap[word].size(); ++letter )
                {
                    puzzle[arrayMap[word].getCharPosX(letter)][arrayMap[word].getCharPosY(letter)] = arrayMap[word].getCharAt(letter);
                }
            }
            return puzzle;
        }



        public void printGreeting()
        {
            System.out.println("Welcome to Yalpha's puzzle! \nThe rules are:\nEvery word needs to be over 1 and under 20 letters in length. \nIf help needed type h.");
        }


        /**
         *
         * @param words The list of words
         */
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
				+ "\ns-S-save Save a puzzle to file."
                                + "\nw-W-word Save a word list to file.");
	}
}
