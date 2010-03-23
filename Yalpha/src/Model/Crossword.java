package Model;

/**
 * Generates a crossword puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle through an exausting recursive function.
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */
public class Crossword extends Puzzle {
        public void generate(final WordList words)
        {
            WordMap mList = new WordMap(words, 10);
            System.out.println("CROSSWORD - GENERATE");
        }

}
