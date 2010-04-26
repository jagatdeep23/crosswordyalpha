package Controller;

import java.util.Scanner;
import Model.*;
import View.View;

/**
 * Gathers all user input and tells the model and view what to do and when to
 * update.
 *
 * @author Team Yalpha, specifically Bob Grube AND Matthew Maze
 * @version 1.0
 */
public class Controller {
    /*
    public Controller(){
    run();
    }*/

    /**
     * Gathers input until user exits. Based on input tells model and view how
     * to update.
     *
     * @param args
     */

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View();
        boolean exit = false;
        Scanner input = new Scanner(System.in);
        char command;
        String line;
        String puzzleType = "wordsearch";


        view.printGreeting();


        while (!exit) {
            line = input.nextLine().toLowerCase();
            if (line.length() > 0) {
                command = line.charAt(0);
                switch (command) {
                    case 'a':
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Added: " + line.substring(2));
                            model.add(line.substring(2));
                        } else if (line.length() > 4 && line.startsWith("add ")) {
                            //System.out.println("Added: " + line.substring(4));
                            model.add(line.substring(4));
                        }
                        break;
                    case 'c':
                        String tempParameter = "";
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            tempParameter = line.substring(2);
                        } else if (line.length() > 7 && line.startsWith("choose ")) {
                            tempParameter = line.substring(7);
                        }

                        if (tempParameter.toLowerCase().equals("crossword"))
                        {
                            model.choosePuzzle(Model.PuzzleType.CROSSWORD);

                            puzzleType = "crossword";
                        }
                        else if (tempParameter.toLowerCase().equals("wordsearch"))
                        {
                            model.choosePuzzle(Model.PuzzleType.WORDSEARCH);
                            puzzleType = "wordsearch";
                        }
                        break;
                    case 'e':
                        //System.out.println("Goodbye");
                        exit = true;
                        break;
                    case 'g':
                        model.generate();

                        if(puzzleType.equals("wordsearch"))
                            view.printPuzzle(model.getMatrix());
                        else
                            view.printCrossword(model.getMatrix());

                        break;
                    case 'h':
                        view.printHelp();
                        break;
                    case 'i':
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Import: " + line.substring(2));
                            try {
                                model.loadWordList(line.substring(2));
                            }
                            catch (Exception e) {
                            }
                        } else if (line.length() > 7 && line.startsWith("import ")) {
                            //System.out.println("Import: " + line.substring(7));
                            try {
                                model.loadWordList(line.substring(7));
                            }
                            catch (Exception e) {
                            }
                        }
                        break;
                    case 'k':
                        if(puzzleType.equals("wordsearch"))
                        {
                            view.printPuzzle(model.getMatrixSolution());
                        }
                        else
                        {
                            view.printCrosswordSolution(model.getMatrixSolution());
                        }

                        break;
                    case 'l':
                        String [] tempWordList = model.getwordList();
                        for(int i = 0; i < tempWordList.length; ++i)
                            System.out.println(tempWordList[i]);
                        break;
                    case 'o':
                        System.out.println("Open");
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Open file: " + line.substring(2));
                            try {
                                model.loadPuzzle(line.substring(2));
                            }
                            catch (Exception e) {
                            }
                        } else if (line.length() > 5 && line.startsWith("open ")) {
                            //System.out.println("Open file: " + line.substring(5));
                            try {
                                model.loadPuzzle(line.substring(5));
                            }
                            catch (Exception e) {

                            }
                        }

                    case 'p':
                        if(puzzleType.equals("wordsearch"))
                            view.printPuzzle(model.getMatrix());
                        else
                            view.printCrossword(model.getMatrix());

                        break;
                    case 'r':
                    case 'd':
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Remove word: " + line.substring(2));
                            model.remove(line.substring(2));
                        } else if (line.length() > 7
                                && (line.startsWith("remove ") || line.startsWith("delete "))) {
                            //System.out.println("Remove word: " + line.substring(7));
                            model.remove(line.substring(7));
                        }
                        break;
                    case 's':
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Save To: " + line.substring(2));
                            try {
                                model.savePuzzle(line.substring(2));
                            } catch (Exception e) {

                            }
                        } else if (line.length() > 5 && line.startsWith("save ")) {
                            //System.out.println("Save To: " + line.substring(5));
                            try {
                                model.savePuzzle(line.substring(5));
                            }
                            catch (Exception e) {

                            }
                        }
                        break;
                    case 'w':
                        if (line.length() > 2 && line.charAt(1) == ' ') {
                            //System.out.println("Save To: " + line.substring(2));
                            try {
                                model.saveWordList(line.substring(2));
                            }
                            catch (Exception e) {

                            }
                        } else if (line.length() > 5 && line.startsWith("word ")) {
                            //System.out.println("Save To: " + line.substring(5));
                            try {
                                model.saveWordList(line.substring(5));
                            }
                            catch (Exception e) {
                                
                            }
                        }
                        break;
                }
            }
        }
    }
}