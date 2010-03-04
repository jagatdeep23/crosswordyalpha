package yalpha;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gathers all user input and tells the model and view what to do and when to
 * update.
 *
 * @author Team Yalpha, specifically Bob Grube AND Matthew Maze
 * @version 1.0
 */
public class Controller {

	/**
	 * Gathers input until user exits. Based on input tells model and view how
	 * to update.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

                /*WordList list = new WordList();
		Model model = new Model();
                String name = "PATRICK";
                model.add(name);
                model.add("SAMMMIE");
                model.add("JILLIAN");
                model.generate();
                model.test();*/
                
		/*char[][] puz = { { 'a', 'b', 'e' }, { 'p', 'o', 'p' },
				{ 'd', 'a', 'd' } };
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("What");
		list.add("Lawl");
		list.add("SayWhat");
		list.add("that's what I thought");
		list.add("Indeed");*/
                Model model = new Model();
		View view = new View();
		boolean exit = false;
		Scanner input = new Scanner(System.in);
		char command;
		String line;

               

		while (!exit) {
			line = input.nextLine().toLowerCase();
                        if(line.length() > 0)
                        {
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
                            case 'e':
                                    //System.out.println("Goodbye");
                                    exit = true;
                                    break;
                            case 'g':
                                    model.generate();
                                    view.printPuzzle(model.getMatrix());
                                    //view.printPuzzle(puz);
                                    break;
                            case 'h':
                                    view.printHelp();
                                    break;
                            case 'i':
                                    if (line.length() > 2 && line.charAt(1) == ' ') {
                                            //System.out.println("Import: " + line.substring(2));
                                            model.loadWordList(line.substring(2));
                                    } else if (line.length() > 7 && line.startsWith("import ")) {
                                            //System.out.println("Import: " + line.substring(7));
                                            model.loadWordList(line.substring(7));
                                    }
                                    break;
                            case 'o':
                                    System.out.println("Open");
                                    if (line.length() > 2 && line.charAt(1) == ' ') {
                                            //System.out.println("Open file: " + line.substring(2));
                                            model.loadPuzzle(line.substring(2));
                                    } else if (line.length() > 5 && line.startsWith("open ")) {
                                            //System.out.println("Open file: " + line.substring(5));
                                            model.loadPuzzle(line.substring(5));
                                    }
                                    break;
                            case 'p':
                                    view.printPuzzle(model.getMatrix());
                                    //view.printPuzzle(puz);
                                    break;
                            case 'r':
                            case 'd':
                                    System.out.println("Remove");
                                    if (line.length() > 2 && line.charAt(1) == ' ') {
                                            //System.out.println("Remove word: " + line.substring(2));
                                            model.remove(line.substring(2));
                                    } else if (line.length() > 7
                                                    && (line.startsWith("remove ") || line
                                                                    .startsWith("delete "))) {
                                            //System.out.println("Remove word: " + line.substring(7));
                                            model.remove(line.substring(7));
                                    }
                                    break;
                            case 's':
                                    System.out.println("Save");
                                    if (line.length() > 2 && line.charAt(1) == ' ') {
                                            //System.out.println("Save To: " + line.substring(2));
                                            model.savePuzzle(line.substring(2));
                                    } else if (line.length() > 5 && line.startsWith("save ")) {
                                            //System.out.println("Save To: " + line.substring(5));
                                            model.savePuzzle(line.substring(5));
                                    }
                                    break;
                            }
                        }
		}
	}
}
