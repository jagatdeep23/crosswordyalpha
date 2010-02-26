/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
/**
 *
 * @author Patrick
 */
enum enPuzzle {CROSSWORD,WORDSEARCH};

public class Model {
    private Puzzle  m_puzzle = null;
    private WordList m_list = null;

    public Model()
    {
        m_list = new WordList();

        //
        choosePuzzle(enPuzzle.WORDSEARCH);
    }

    public boolean test()
    {
        if(m_puzzle.testWord(m_list.get(0)) && m_puzzle.testWordMap(m_list))
        {
            return true;
        }
        return false;
    }

    public void choosePuzzle(enPuzzle puzType)
    {
        if(puzType == enPuzzle.CROSSWORD)
        {
            m_puzzle = new Crossword();
        }
        else if (puzType == enPuzzle.WORDSEARCH)
        {
            m_puzzle = new Wordsearch();
        }
    }

    public void add(String temp)
    {
        m_list.add(temp);
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
