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
//enum direction {UP,DOWN,DIAGNAL,LEFT,RIGHT};
//m_up =0, m_down, m_diagnal, m_left, m_right
public abstract class puzzle {
    private class point
    {
        private int x,y;

        point(int tx, int ty)
        {
            x = tx;
            y = ty;
        }
        point()
        {
            x =0;
            y =0;
        }

        void setX(int temp)
        {
            x = temp;
        }

        void setY(int temp)
        {
            y = temp;
        }

        int getX()
        {
            return x;
        }

        int getY()
        {
            return y;
        }

        private void generateDelta(final Word tempW)
        {
            setX(0);
            setY(0);
            if(tempW.getUp() == true || tempW.getDown() == true)
            {
                setY(1);
            }
            if(tempW.getRight() == true || tempW.getLeft() == true)
            {
                setX(1);
            }
        }
    }
    protected class Word
    {
        public final static int num_direction = 5;
        //private point front,back;
        //private String m_word = null;
        private char [] m_letters = null;
        private point [] m_pos = null;
        private point offset = null;
        private boolean m_up, m_down, m_left, m_right;

        Word(String temp)
        {
           m_pos = new point[temp.length()];
           offset = new point(0,0);
           setString(temp);
           setRight(true);
        }

        public final int getCharPosX(int c)
        {
            return getX(m_pos[c]);
        }

        public final int getCharPosY(int c)
        {
            return getY(m_pos[c]);
        }

        private final int getX(point temp)
        {
            return temp.getX();
        }

        private final int getY(point temp)
        {
            return temp.getY();
        }

        public boolean getUp()
        {
            return m_up;
        }

        public boolean getDown()
        {
            return m_down;
        }

        public boolean getRight()
        {
            return m_right;
        }

        public boolean getLeft()
        {
            return m_left;
        }

        public int getLength()
        {
           return m_letters.length;
        }

        public char [] getString()
        {
            return m_letters;
        }

        public void setFirstCharPos(int chX, int chY)
        {
            m_pos[0].setX(chX);
            m_pos[0].setY(chY);
            resetPos();
        }

        private void setCharPosY(int chr, int yp)
        {
            m_pos[chr].y = yp;
        }

        private void setCharPosX(int chr, int xp)
        {
            m_pos[chr].x = xp;
        }

        public void setUp(boolean temp)
        {
            m_up = temp;
            offset.setY(1);
            resetPos();
        }

        public void setDown(boolean temp)
        {
            m_down = temp;
            offset.setY(-1);
            resetPos();
        }

        public void setRight(boolean temp)
        {
            m_right = temp;
            offset.setX(-1);
            resetPos();
        }

        public void setLeft(boolean temp)
        {
            m_left = temp;
            offset.setX(1);
            resetPos();
        }

        public void setString(String temp)
        {
            m_letters = (char [])temp.toCharArray().clone();
        }

        public void resetPos()
        {
               for(int i = 1; i < m_letters.length; i++ )
               {
                   m_pos[i].setX(m_pos[i-1].getX() + offset.getX());
               }
            
           
                for(int i = 1; i < m_letters.length; i++ )
                {
                    m_pos[i].setY(m_pos[i-1].getY()+ offset.getY());
                }
        }
    }
    protected class WordMap extends ArrayList<Word>
    {
        int m_largestX, m_largestY;
        ArrayList<Word> m_list = null;
        WordMap(WordList temp)
        {
            super();
            set(temp);
        }
        

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

        public boolean set(final WordList temp)
        {
            m_list = new ArrayList<Word>(temp.size());
            
            for(int i =0; i < temp.size(); i++)
            {
                m_list.set(i,new Word(temp.get(i)));
            }
            
            return true;
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

    //puts all the words into the char matrix(2x2)
    private void matrixWordPopulation(final WordMap tempMap)
    {
        map = new char [tempMap.getLargestX()][tempMap.getLargestY()];
        
        for(int i=0; i < tempMap.size(); i++)
        {
            Word tempW = tempMap.get(i);
            String tempS = tempW.getString();
            point delta = new point();

            delta.generateDelta(tempW);

            int posY = tempW.getY();
            int posX = tempW.getX();

            int dX = delta.getX();
            int dY = delta.getY();
            
            for(int j = 0; j < tempW.getLength(); j++ )
            {
                map[posX + dX][posY + dY] = tempS.charAt(j);
            }
        }

    }
}
