/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;

/**
 *
 * @author Patrick
 */
enum direction {UP,DOWN,DIAGNAL,LEFT,RIGHT};
//m_up =0, m_down, m_diagnal, m_left, m_right
public class puzzle {
    private class WordMap extends WordList
    {
        private int m_x,m_y;
        private boolean [] m_direction = new boolean[5];
        public final static int num_direction = 5;
        WordMap()
        {
            super();
            m_x = 0;
            m_y = 0;
        }

        public final int getX()
        {
            return m_x;
        }

        public final int getY()
        {
            return m_y;
        }

        public boolean getDirection(int index)
        {
            return m_direction[index];
        }

        public void setY(int temp)
        {
            m_y = temp;
        }

        public void setX(int temp)
        {
            m_x = temp;
        }
        
        public void setDirection(int index, boolean mod)
        {
            m_direction[index] = mod;
        }

    }
    //private WordList m_list = null;
    private char [][] map = null;

    public void generate(WordList words)
    {
        
    }

    public char [][] getMatrix()
    {
         return map;
    }

    private void populateArray(final WordMap tempMap)
    {

    }
}
