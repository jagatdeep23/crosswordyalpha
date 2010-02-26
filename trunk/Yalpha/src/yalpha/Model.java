/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
/**
 *
 * @author Patrick
 */

public class Model {
    static final int CROSSWORD = 0;
    static final int WORDSEARCH = 1;
    
    private Puzzle  m_puzzle = null;
    private WordList m_list = null;

    public Model()
    {
        m_list = new WordList();

        //default puzzle is WORDSEARCH
        choosePuzzle(WORDSEARCH);
    }

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

    public void add(String temp)
    {
        m_list.add(temp);
    }

    public void remove(String temp)
    {
        m_list.remove(temp);
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

    public void generate()
    {
        m_puzzle.generate(m_list);
    }

    public char [][] getMatrix()
    {
       return m_puzzle.getMatrix();
    }
}
