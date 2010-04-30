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
 * @author Team Yalpha, specifically Patrick Martin, Jordan Hollinger
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

        /**
         * 
         * @param temp List of words to add
         * @param bounds Bounds for puzzle generation
         * @param checkNeg Positive position checking on or off
         */
        WordMap(final WordList temp, int bounds, boolean checkNeg)
        {
            super();
            initalize();
            //m_bound = bounds;
            try
            {
                setBound(bounds);
            }
            catch (Exception e)
            {
                System.out.println("Set bounds error.");
                System.exit(0);
            }
            m_checkNeg = checkNeg;
            add(temp);
        }

        /**
         *
         * @param temp List of words to add
         * @param bounds
         */
        WordMap(final WordList temp, int bounds)
        {
            super();
            initalize();
            m_bound = bounds;
            add(temp);
        }

        /**
         *
         * @param temp List of words to add
         */
        WordMap(final WordList temp)
        {
            super();
            initalize();
            add(temp);
        }

        /**
         *
         * @param checkNeg Positive position checking on or off
         */
        WordMap(boolean checkNeg)
        {
            this();
            m_checkNeg = checkNeg;
        }

        /**
         * Default constructor
         */
        WordMap()
        {
           initalize();
        }

        /**
         * Initialize data
         */
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

        /**
         * Add contents of a WordList to this instance
         * @param temp WordList to add
         * @return true
         */
        private boolean add(final WordList temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i),m_checkNeg));
            }

            return true;
        }

        /**
         * Creates a duplicat of the current instance
         * @return The clone of the current WordMap
         */
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
         * Adds an offset to all words in the map
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
        public void TranslatePositionalStateOfWordsToTheConditionOfBeingNotNegative()
        {
            Point tP = new Point();
            
            tP.setX(getSmallestX());
            tP.setY(getSmallestY());

            if(tP.getX() < 0 || tP.getY() < 0)
            {
                addOffset(tP);
            }
        }

        /**
         * Adds the contents of a WordMap to this instance
         * @param temp the WordMap to add
         * @return true
         */
        public boolean add(final WordMap temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i)));
            }

            return true;
        }

        /**
         * Gets the size bound of the wordmap
         * @return
         */
        public int getBound()
        {
            return m_bound;
        }

        /**
         * Sets the size bound
         * @param tempB Desired size for puzzle generation
         * @throws Exception Eror setting bounds
         */
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

        /**
         * Gets the longest word in the map
         * @return The longest word
         */
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

        /**
         * Gets the largest X value of all the words in the wordmap
         * @return the largest X value
         */
        public int getLargestX()
        {
            checkAllForLargest();
            return m_largest.getX();
        }

        /**
         * Gets the largest X value of all the words in the wordmap
         * @return the largest X value
         */
        public int posLargestX()
        {
            checkAllForLargest();
            return m_indexLargest.getX();
        }

        /**
         * Gets the largest Y value of all the words in the wordmap
         * @return the largest Y value
         */
        public int getLargestY()
        {
            checkAllForLargest();
            return m_largest.getY();
        }

        /**
         * Gets the largest Y value of all the words in the wordmap
         * @return the largest Y value
         */
        public int posLargestY()
        {
            checkAllForLargest();
            return m_indexLargest.getY();
        }

        /**
         * Gets the smallest X value of all the words in the wordmap
         * @return the smallest X value
         */
        public int getSmallestX()
        {
            checkAllForSmallest();
            return m_smallest.getX();
        }
        /**
         * Gets the smallest X value of all the words in the wordmap
         * @return the smallest X value
         */
        public int posSmallestX()
        {
            checkAllForSmallest();
            return m_indexSmallest.getX();
        }

        /**
         * Gets the smallest Y value of all the words in the wordmap
         * @return the smallest Y value
         */
        public int getSmallestY()
        {
            checkAllForSmallest();
            return m_smallest.getY();
        }

        /**
         * Gets the smallest Y value of all the words in the wordmap
         * @return the smallest Y value
         */
        public int posSmallestY()
        {
            checkAllForSmallest();
            return m_indexSmallest.getY();
        }

        /**
         * Gets WordList containing all the words from this map
         * @return WordList
         */
        public WordList toWordList()
        {
            WordList list = new WordList();

            for (Word w : this)
            {
                list.add(w.toString());
            }

            return list;
        }

        /**
         *
         * @param temp
         * @param index
         */
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

        /**
         * 
         * @param temp
         * @param index
         */
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
