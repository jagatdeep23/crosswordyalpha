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
    puzzle  m_puzzle;
    private WordList m_list = null;

    public Model()
    {
        m_list = new WordList();
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

    public void generate()
    {
        m_puzzle.generate(m_list);
    }

    public char [][] getMatrix()
    {
       return m_puzzle.getMatrix();
    }
}
