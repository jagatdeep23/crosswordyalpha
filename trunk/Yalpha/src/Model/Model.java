package Model;

import java.io.IOException;

/**
 * Alters the data handed to it by the controller and exports the final 
 * product(aka <Specific>puzzle) back to the controller in the form of a
 * matric array.
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */


public class Model {
    public enum PuzzleType{CROSSWORD,WORDSEARCH};
    private Puzzle  m_puzzle = null;
    private WordList m_list = null;
    private PuzzleType m_type = PuzzleType.WORDSEARCH;

    /**
     * Default constructor
     */
    public Model()
    {
        m_list = new WordList();

        //default puzzle
        choosePuzzle(PuzzleType.WORDSEARCH);
    }

    /**
     * Sets the puzzle type to be used in the model
     * @param puzType the type of puzzle (WORDSEARCH or CROSSWORD)
     */
    public void choosePuzzle(PuzzleType puzType)
    {
        m_type = puzType;
        
        if(puzType == PuzzleType.CROSSWORD)
        {
            m_puzzle = new Crossword();
        }
        else if (puzType == PuzzleType.WORDSEARCH)
        {
            m_puzzle = new Wordsearch();
        }
    }

    /**
     * Gets the type of puzzle currently stored by the model
     * @return the current puzzle type
     */
    public PuzzleType getPuzzleType()
    {
        return m_type;
    }

    /**
     * Gets the Matrix containing the solution key (the words only)
     * @return the solution matrix
     */
    public char[][] getMatrixSolution()
    {
        return m_puzzle.getMatrixSolution();
    }

    /**
     * Adds a word to the model
     * @param temp word to add
     * @return whether or not the word was added
     */
    public boolean add(String temp)
    {
        return m_list.add(temp);
    }

    /**
     * Removes a word from the model
     * @param temp word to remove
     */
    public void remove(String temp)
    {
        m_list.remove(temp);
    }

    /**
     * Removes all words from the model
     */
    public void removeAll()
    {
        m_list.clear();
    }

    /**
     * Saves the current puzzle to a file
     * @param temp the name of the destination file
     */
    public void savePuzzle(String temp)
        throws IOException
    {
        FileHandler.savePuzzleText(temp, m_list, getMatrix());
    }

    /**
     * Loads the puzzle and word list from a file
     * @param temp name of the file to load
     */
    public void loadPuzzle(String temp)
        throws IOException
    {
        Pair<WordList, char[][]> tempP = null;// = new Pair<WordList, char[][]>(m_list, m_cArray);

        tempP = FileHandler.loadPuzzleText(temp);

        if(tempP != null)
        {
            m_list = tempP.getFirst();
            char [][] m_cArray = tempP.getSecond();
            m_puzzle.populateWordMatrix(m_cArray);
        }
    }

    /**
     * Loads just the word list from a file
     * @param temp name of file to load
     */
    public void loadWordList(String temp)
        throws IOException
    {
        m_list = FileHandler.loadWordList(temp);
    }

    /**
     * Saves just the word list to a file
     * @param temp name of destination file
     */
    public void saveWordList(String temp)
        throws IOException
    {
        FileHandler.saveWordList(temp, m_list);
    }

    public void export(String fileName, boolean isSolution)
        throws IOException
    {
        if (m_type == PuzzleType.CROSSWORD)
        {

            if (isSolution)
            {
                FileHandler.exportCrosswordSolution(fileName, m_puzzle.getMatrixSolution());
            }
            else
            {
                FileHandler.exportCrossword(fileName, m_puzzle.getMatrixSolution(), m_list);
            }
        }
        else if (isSolution)
        {
            FileHandler.exportWordSearchSolution(fileName, m_puzzle.getMatrixSolution());
        }
        else
        {
            FileHandler.exportWordSearch(fileName, m_puzzle.getMatrixRandomize(), m_list);
        }
    }

    /**
     * Generates a puzzle of the currently selected type
     */
    public void generate()
    {
        m_puzzle.generate(m_list);
    }

    /**
     * gets the puzzle matrix, including randomized characters to fill in word search
     * @return the puzzle matrix
     */
    public char [][] getMatrix()
    {
       return m_puzzle.getMatrixRandomize();
    }

    /**
     * Gets the current word list
     * @return gets the current word list
     */
    public String [] getwordList()
    {
        String [] tempS = new String [m_list.size()];
        return m_list.toArray(tempS);
    }
}
