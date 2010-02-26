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
public abstract class Puzzle {
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

        //I forget what this function was supposed to do...
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

    ////////////WORD CLASS////////////////////////

    protected class Word
    {
        public final static int num_direction = 5;
        //private point front,back;
        //private String m_word = null;
        private char [] m_letters = null;
        private point [] m_pos = null;
        private point m_offset = null;
        private point m_smallest;
        private point m_largest;
        private boolean m_up, m_down, m_left, m_right;

        Word(String temp)
        {
           m_pos = new point[temp.length()];

           for(int i =0; i < m_pos.length; i++ )
           {
              m_pos[i] = new point();
           }
           
           m_offset = new point();
           m_largest = new point();
           m_smallest = new point();
           
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

        public int getLargestX()
        {
            return m_largest.getX();
        }

        public int getLargestY()
        {
            return m_largest.getY();
        }

        public int getSmallestY()
        {
            return m_smallest.getY();
        }

        public int getSmallestX()
        {
            return m_smallest.getX();
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

        public char getCharAt(int index)
        {
            return m_letters[index];
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
            if(m_up == true)
            {
                m_offset.setY(1);
                m_down = false;
            }
            else
            {
                m_offset.setY(0);
            }
             //if all directions are false then this resetpos is skipped
            if(CheckDirection())
            {
                resetPos();
            }
        }

        public void setDown(boolean temp)
        {
            m_down = temp;
            if(m_down == true)
            {
                m_offset.setY(-1);
                m_up = false;
            }
            else
            {
                m_offset.setY(0);
            }
             //if all directions are false then this resetpos is skipped
            if(CheckDirection())
            {
                resetPos();
            }
        }

        public void setRight(boolean temp)
        {
            m_right = temp;

            if(m_right == true)
            {
                m_offset.setX(1);
                m_left = false;
            }
            else
            {
                m_offset.setX(0);
            }
             //if all directions are false then this resetpos is skipped
            if(CheckDirection())
            {
                resetPos();
            }
        }

        public void setLeft(boolean temp)
        {
            m_left = temp;
            if(m_left == true)
            {
                m_offset.setX(-1);
                m_right = false;
            }
            else
            {
                m_offset.setX(0);
            }

            //if all directions are false then this resetpos is skipped
            if(CheckDirection())
            {
                resetPos();
            }
        }

        //If the word has no direction then the Direction right is set and CheckDirection() returns false
        private boolean CheckDirection()
        {
            boolean ynDir = true;
            if(m_left == false && m_right == false && m_up == false && m_down == false)
            {
                setRight(true);
                ynDir = !ynDir;
            }
            return ynDir;
        }

        public void setString(String temp)
        {
            m_letters = (char [])temp.toCharArray().clone();
        }

        public void resetPos()
        {
           
            for(int i = 1; i < m_letters.length; i++ )
            {
                m_pos[i].setX(m_pos[i-1].getX() + m_offset.getX());
                //System.out.println("PrevPosX: " + m_pos[i-1].getX() + "\nCurrPosX: " + m_pos[i].getX() + "\nOffsetX: " + m_offset.getX());
            }
            
            for(int i = 1; i < m_letters.length; i++ )
            {
                m_pos[i].setY(m_pos[i-1].getY()+ m_offset.getY());
            }
            ObtainGreatest();
            ObtainSmallest();
        }

        private void ObtainGreatest()
        {
            m_largest.setX(m_pos[0].getX());
            m_largest.setY(m_pos[0].getY());
            for(int i = 1; i < m_pos.length; i++)
            {
                if(m_largest.getX() < m_pos[i].getX())
                {
                    m_largest.setX(m_pos[i].getX());
                }
                
                if(m_largest.getY() < m_pos[i].getY())
                {
                    m_largest.setY(m_pos[i].getY());
                }
            }
        }
        private void ObtainSmallest()
        {
            m_smallest.setX(m_pos[0].getX());
            m_smallest.setY(m_pos[0].getY());
            for(int i = 1; i < m_pos.length; i++)
            {
                if(m_smallest.getX() > m_pos[i].getX())
                {
                    m_smallest.setX(m_pos[i].getX());
                }

                if(m_smallest.getY() > m_pos[i].getY())
                {
                    m_smallest.setY(m_pos[i].getY());
                }
            }

        }
    }

    /////////////////////WORDMAP//////////////////////////

    protected class WordMap extends ArrayList<Word>
    {
        point m_largest = null;
        point m_index = null;
        
        WordMap(final WordList temp)
        {
            super();
            m_largest = new point(-1,-1);
            m_index = new point(-1,-1);
            add(temp);
        }

        public void checkAllForLargest()
        {
            for(int i = 0; i < size(); i++)
            {
                checkLargest(get(i));
            }
        }

        public boolean add(final Word temp)
        {
            checkLargest(temp);

            return super.add(temp);
        }

        private boolean add(final WordList temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i)));
            }

            return true;
        }

        public int getLargestX()
        {
            return m_largest.getX();
        }

        public int posLargestX()
        {
            return m_index.getX();
        }

        public int getLargestY()
        {
            return m_largest.getY();
        }

        public int posLargestY()
        {
            return m_index.getY();
        }

        private void checkLargest(final Word temp)
        {
            if(temp.getLargestX() > m_largest.getX())
            {
                m_largest.setX(temp.getLargestX());
                m_index.setX(super.size());
            }
            if(temp.getLargestY() > m_largest.getY())
            {
                m_largest.setY(temp.getLargestY());
                m_index.setY(super.size());
            }

        }

    }

    ////////////////PUZZLE METHODS/////////////////////////////
    
    private char [][] map = null;

    public abstract void generate(final WordList words);

    Puzzle()
    {
    }

    public boolean testWord(final String tempS)
    {
        Word tempW = new Word(tempS);

        tempW.setFirstCharPos(2, 4);

        System.out.println("Length: " + tempW.getLength());
        System.out.println("PosX: " + tempW.getCharPosX(0));
        System.out.println("PosY: " + tempW.getCharPosY(6));
        System.out.println("LargestX: " + tempW.getLargestX());
        System.out.println(tempW.getString());
        
        tempW.setDown(true);

        System.out.println("Length: " + tempW.getLength());
        System.out.println("PosX: " + tempW.getCharPosX(0));
        System.out.println("PosY: " + tempW.getCharPosY(6));
        System.out.println("LargestX: " + tempW.getLargestX());
        System.out.println(tempW.getString());
        //System.out.println(tempW.g)
        return true;
    }

    public boolean testWordMap(final WordList words)
    {
        System.out.println("TEST - WORDMAP\n\n");

        WordMap tempMap = new WordMap(words);
        
        for(int i = 0; i < tempMap.size(); i++)
        {
            tempMap.get(i).setFirstCharPos(0, i);
        }
        tempMap.checkAllForLargest();

        System.out.println("LargestX: " + tempMap.getLargestX());
        System.out.println("LargestY: " + tempMap.getLargestY());


        return true;
    }

    public char [][] getMatrix()
    {
         return map;
    }

    //puts all the words into the char matrix(2x2)
    protected void matrixWordPopulation(final WordMap tempMap)
    {
        map = new char [tempMap.getLargestX()+1][tempMap.getLargestX()+1];

        for(int i=0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++ )
            {
                map[i][j] = 'F';
            }
        }

        for(int i=0; i < tempMap.size(); i++)
        {
            Word tempW = tempMap.get(i);
            tempW.setUp(true);
            /*char [] tempS = tempW.getString();
            point delta = new point();

            delta.generateDelta(tempW);

            int posY = tempW.getCharPosY(0);
            int posX = tempW.getCharPosX(0);

            int dX = delta.getX();
            int dY = delta.getY();*/

            for(int j = 0; j < tempW.getLength(); j++ )
            {
                map[tempW.getCharPosX(j)][tempW.getCharPosY(j)] = tempW.getCharAt(j);
            }
        }

       /*for(int i=0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++ )
            {
                if(map[i][j] == ' ')
                {
                    map[i][j] = 'F';
                }
            }
        }*/
 
    }
}

