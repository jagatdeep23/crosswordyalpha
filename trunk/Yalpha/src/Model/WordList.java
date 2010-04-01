package Model;

import java.util.ArrayList;

/**
 * Contains a list of non-repeating lower-case valid words. A valid word is
 * defined as a String that is between 3 and 12 characters exclusively with only
 * standard US alphabet letters.
 *
 * @author Team Yalpha, specifically Bob Grube AND Matthew Maze
 * @version 1.0
 */
public class WordList extends ArrayList<String> {

    /**
     * Adds a word to the WordList if it is a valid word. A valid word is
     * defined as a String that is between 3 and 12 characters exclusively with
     * only standard US alphabet letters. Will return true if the word was
     * added.
     *
     * @param wordLine
     *            the String to be added to the list.
     * @return boolean if the word is valid and added to list.
     */
    @Override
    public boolean add(String wordLine) {
        boolean wordAdded = false;
        wordLine = wordLine.toLowerCase();
        String word;

        while (wordLine.indexOf(' ') > 0) {
            word = wordLine.substring(0, wordLine.indexOf(' '));
            if (!super.contains(word) && word.length() > 3 && word.length() < 20
                    && isLegalWord(word)) {
                super.add(word);
                wordAdded = true;
            }
            wordLine = wordLine.substring(wordLine.indexOf(' ') + 1, wordLine.length());
        }
        if (!super.contains(wordLine) && wordLine.length() > 3 && wordLine.length() < 20
                && isLegalWord(wordLine)) {
            super.add(wordLine);
            wordAdded = true;
        }
        return wordAdded;
    }

    /**
     * Checks if a word is a valid word or not. A valid word is defined as a
     * String that is between 3 and 12 characters exclusively with only US
     * alphabet letters. Will return true if the word is valid.
     *
     * @param word
     *            the String to be checked for validity.
     * @return boolean if the word is valid.
     */
    private boolean isLegalWord(String word) {
        int i = 0;
        for (; i < word.length(); ++i) {
            if (word.charAt(i) < 96 || word.charAt(i) > 123) {
                break;
            }
        }
        return (i == (word.length()));
    }

    /**
     * Removes a word from the WordList. Does nothing if the word is not in the
     * list. Returns true if the word was removed.
     *
     * @param wordLine
     *            the String to be removed from list.
     * @return boolean if the word is removed.
     */
    public boolean remove(String wordLine) {
        boolean wordRemoved = false;
        wordLine = wordLine.toLowerCase();

        while (wordLine.indexOf(' ') > 0) {
            if (super.remove(wordLine.substring(0, wordLine.indexOf(' ')))) {
                wordRemoved = true;
            }
            wordLine = wordLine.substring(wordLine.indexOf(' ') + 1, wordLine.length());
        }
        if (super.remove(wordLine)) {
            wordRemoved = true;
        }
        return wordRemoved;
    }
}
