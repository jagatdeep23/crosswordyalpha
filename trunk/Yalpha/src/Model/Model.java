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
    static final int CROSSWORD = 0;
    static final int WORDSEARCH = 1;
    
    private Puzzle  m_puzzle = null;
    private WordList m_list = null;

    public Model()
    {
        m_list = new WordList();

        //default puzzle
        choosePuzzle(WORDSEARCH);
    }

    public void choosePuzzle(int puzType)
    {
        if(puzType == CROSSWORD)
        {
            m_puzzle = new Crossword();
        }
        else if (puzType == WORDSEARCH)
        {
            m_puzzle = new Wordsearch();
        }
    }

    public boolean add(String temp)
    {
        return m_list.add(temp);
    }

    public void remove(String temp)
    {
        m_list.remove(temp);
    }
    public void removeAll()
    {
        m_list.clear();
    }

    public void savePuzzle(String temp)
    {
        try
        {
            FileHandler.savePuzzleText(temp, m_list, getMatrix());
        }
        catch(IOException e)
        {
            System.out.println("Can't Save puzzle ");
            System.exit(0);
        }
    }

    public void loadPuzzle(String temp)
    {
        Pair<WordList, char[][]> tempP = null;// = new Pair<WordList, char[][]>(m_list, m_cArray);
        try
        {
            tempP = FileHandler.loadPuzzleText(temp);
        }
        catch(IOException e)
        {
            System.out.println("Can't Load puzzle ");
            System.exit(0);
        }

        if(tempP != null)
        {
            m_list = tempP.getFirst();
            char [][] m_cArray = tempP.getSecond();
            m_puzzle.populateWordMatrix(m_cArray);
        }
    }

    public void loadWordList(String temp)
    {
        try
        {
           m_list = FileHandler.loadWordList(temp);
        }
        catch(IOException e)
        {
            System.out.println("Can't Load WordList ");
            System.exit(0);
        }
    }

    public void saveWordList(String temp)
    {
        try
        {
            FileHandler.saveWordList(temp, m_list);
        }
        catch(IOException e)
        {
            System.out.println("Can't Save WordList");
            System.exit(0);
        }
    }

    // generates choosen puzzle (if no puzzle was choosen the default puzzle is WordSearch
    public void generate()
    {
        m_puzzle.generate(m_list);
    }

    public char [][] getMatrix()
    {
       return m_puzzle.getMatrix();
    }

    public String [] getwordList()
    {
        String [] tempS = new String [m_list.size()];
        return m_list.toArray(tempS);
    }
}
