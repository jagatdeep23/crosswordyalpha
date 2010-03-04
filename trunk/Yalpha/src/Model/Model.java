package Model;
/**
 * Alters the data handed to it by the controller and exports the final 
 * product(aka <Specific>puzzle) back to the controller in the form of a
 * matric array.
 *
 * @author Team Yalpha, specifically Patrick Martin, Jordan
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

    //Allows driver to test puzzle through model
    public boolean test()
    {
        if(m_puzzle.testWord(m_list.get(0)) && m_puzzle.testWordMap(m_list))
        {
            return true;
        }
        return false;
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
        System.out.println(temp);
    }

    public void loadPuzzle(String temp)
    {
        System.out.println(temp);
    }

    public void loadWordList(String temp)
    {
        System.out.println(temp);
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
