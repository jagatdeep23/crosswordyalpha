/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
import java.util.*;
/**
 *
 * @author Patrick
 */
enum direction {UP,DOWN,DIAGNAL,LEFT,RIGHT};
//m_up =0, m_down, m_diagnal, m_left, m_right
public abstract class puzzle {
    private class point
    {
        public int x,y;
    }
    protected class Word
    {
        public final static int num_direction = 5;
        private int m_x,m_y;
        private String m_word = null;
        private boolean [] m_direction = new boolean[num_direction];

        Word(String temp)
        {
           setString(temp);
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

        public int getLength()
        {
           return m_word.length();
        }

        public String getString()
        {
            return m_word;
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

        public void setString(String temp)
        {
            m_word = temp;
        }
    }
    protected class WordMap extends ArrayList<Word>
    {
        int m_largestX, m_largestY;
        WordMap(WordList temp)
        {
            super();
        }
        ArrayList<String> abc;

        public boolean add(Word temp)
        {
            if(temp.getX() > m_largestX)
            {
                m_largestX = temp.getX();
            }
            if(temp.getY() > m_largestY)
            {
                m_largestY = temp.getY();
            }
            return super.add(temp);
        }

        public int getLargestX()
        {
            return m_largestX;
        }
        public int getLargestY()
        {
            return m_largestY;
        }

    }

    puzzle()
    {
    }
    private char [][] map = null;

    public abstract void generate(final WordList words);

    public char [][] getMatrix()
    {
         return map;
    }

    private void populateArray(final WordMap tempMap)
    {

        for(int i=0; i < tempMap.size(); i++)
        {
            Word tempW = tempMap.get(i);
            String tempS = tempW.getString();
            point delta;
            generateDelta(tempW,delta);
            for(int j = 0; j < tempW.getLength(); j++ )
            {
                map[tempW.getX()][tempW.getY()] = tempS.charAt(j);
            }
        }

    }

    private void generateDelta(final Word tempW, final point tempP)
    {
        //crossword
        if(tempW.getDirection(UP) == true)
        {
            tempP.y =1;
        }
        if(tempW.getDirection(direction.RIGHT) == true)
    }
}
