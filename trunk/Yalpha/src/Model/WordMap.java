/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.util.*;
import java.rmi.NotBoundException;

/**
 * Generates a crossword puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle through an exausting recursive function.
 *
 * @author Team Yalpha, specifically Patrick Martin,Jordan Hollinger
 * @version 1.0
 */
 public class WordMap extends ArrayList<Word> //implements Cloneable
    {
        Point m_largest = null;
        Point m_smallest = null;
        Point m_indexLargest = null;
        Point m_indexSmallest = null;

        /*Point    m_largest = new Point(0,0);
        Point    m_smallest = new Point(0,0);
         Point   m_indexLargest = new Point(-1,-1);
         Point   m_indexSmallest = new Point(-1,-1);*/
        //bounding box - aka the size of the box that the puzzle is in
        int m_bound = 1;
        boolean m_checkNeg = true;

        WordMap(final WordList temp, int bounds, boolean checkNeg)
        {
            super();
            initalize();
            //m_bound = bounds;
            try
            {
                this.setBound(bounds);
            }catch(Exception e)
            {
                System.out.println("HEllo world fuck you!");
                System.exit(0);
            }
            m_checkNeg = checkNeg;
            add(temp);
        }

        WordMap(final WordList temp, int bounds)
        {
            super();
            initalize();
            m_bound = bounds;
            add(temp);
        }
        
        WordMap(final WordList temp)
        {
            super();
            initalize();
            add(temp);
        }

        WordMap(boolean checkNeg)
        {
            this();
            m_checkNeg = checkNeg;
        }

        WordMap()
        {
           initalize();
        }

        private void initalize()
        {
            m_bound = 10;
            m_largest = new Point(0,0);
            m_smallest = new Point(0,0);
            m_indexLargest = new Point(-1,-1);
            m_indexSmallest = new Point(-1,-1);
        }

        /**
         * Obtain the Largest X and Y
         */
        private void checkAllForLargest()
        {
            m_largest.setX(get(0).getLargestX());
            m_largest.setY(get(0).getLargestY());
            for(int i = 1; i < size(); i++)
            {
                checkLargest(get(i),i);
            }
        }

        /**
         * Obtain the smallest X and Y
         */
        private void checkAllForSmallest()
        {
            m_smallest.setX(get(0).getSmallestX());
            m_smallest.setY(get(0).getSmallestY());
            for(int i = 1; i < size(); i++)
            {
                checkSmallest(get(i),i);
            }
        }

        private boolean add(final WordList temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i),m_checkNeg));
            }

            return true;
        }

        /*public boolean set(final WordMap tempWM)
        {
            this.clear();
            
            for(Word A: tempWM)
            {
                this.add(A.clone());
            }

            return true;
        }*/

        @Override
        public WordMap clone()
        {
            WordMap temp = null;
            temp = (WordMap)super.clone();

            temp.m_bound = m_bound;
            temp.m_checkNeg = m_checkNeg;
            temp.m_indexLargest = m_indexLargest.clone();
            temp.m_indexSmallest = m_indexSmallest.clone();
            temp.m_largest = m_largest.clone();

            return temp;


        }

        /**
         * 
         * @param hPoint the offset to add all the other points
         */
        private void addOffset(Point hPoint)
        {
            for(Word feWord: this)
            {
                feWord.setFirstCharPos(feWord.getCharPosX(0)- hPoint.getX(), feWord.getCharPosY(0)-hPoint.getY());
            }

        }

        /**
         * Moves the Smallest X and the Smallest Y to be equal to zero (indirectly moving all the points in the entire list as well)
         */
        public void TranslatePositionalStateOfWordToTheConditionOfBeingNotNegative()
        {
            Point tP = new Point();
            
            tP.setX(getSmallestX());
            tP.setY(getSmallestY());

            if(tP.getX() < 0 || tP.getY() < 0)
            {
                addOffset(tP);
            }
        }

        public boolean add(final WordMap temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i)));
            }

            return true;
        }

        public int getBound()
        {
            return m_bound;
        }

        public void setBound(int tempB) throws Exception
        {
            if(tempB >= 10)
            {
                m_bound = tempB;
            }
            else
            {
                throw new Exception();
            }
        }

        public int getLongestWord()
        {
            if(size() > 0)
            {
                int lrg = get(0).size();
                for(int i = 1; i < size(); i++)
                {
                    if(lrg < get(i).size())
                    {
                        lrg = get(i).size();
                    }
                }

                return lrg;
            }
            return -1;
        }

        public int getLargestX()
        {
            checkAllForLargest();
            return m_largest.getX();
        }

        public int posLargestX()
        {
            checkAllForLargest();
            return m_indexLargest.getX();
        }

        public int getLargestY()
        {
            checkAllForLargest();
            return m_largest.getY();
        }

        public int posLargestY()
        {
            checkAllForLargest();
            return m_indexLargest.getY();
        }

        public int getSmallestX()
        {
            checkAllForSmallest();
            return m_smallest.getX();
        }

        public int posSmallestX()
        {
            checkAllForSmallest();
            return m_indexSmallest.getX();
        }

        public int getSmallestY()
        {
            checkAllForSmallest();
            return m_smallest.getY();
        }

        public int posSmallestY()
        {
            checkAllForSmallest();
            return m_indexSmallest.getY();
        }

        private void checkLargest(final Word temp, int index)
        {
            if(temp.getLargestX() > m_largest.getX())
            {
                m_largest.setX(temp.getLargestX());
                m_indexLargest.setX(index);
            }
            if(temp.getLargestY() > m_largest.getY())
            {
                m_largest.setY(temp.getLargestY());
                m_indexLargest.setY(index);
            }

        }

        private void checkSmallest(final Word temp, int index)
        {
            if(temp.getSmallestX() < m_smallest.getX())
            {
                m_smallest.setX(temp.getSmallestX());
                m_indexSmallest.setX(index);
            }
            if(temp.getSmallestY() < m_smallest.getY())
            {
                m_smallest.setY(temp.getSmallestY());
                m_indexSmallest.setY(index);
            }

        }

    }
