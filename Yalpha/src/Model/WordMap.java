/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.util.*;

/**
 *
 * @author Patrick
 */
 public class WordMap extends ArrayList<Word>
    {
        Point m_largest = null;
        Point m_index = null;
        int m_bound = 0;

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

        WordMap()
        {
           initalize();
        }

        private void initalize()
        {
            m_bound = 10;
            m_largest = new Point(0,0);
            m_index = new Point(-1,-1);
        }

        private void checkAllForLargest()
        {
            for(int i = 0; i < size(); i++)
            {
                checkLargest(get(i));
            }
        }

        private boolean add(final WordList temp)
        {
            for(int i =0; i < temp.size(); i++)
            {
                add(new Word(temp.get(i)));
            }

            return true;
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

        public void setBound(int tempB)
        {
            m_bound = tempB;
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
            return m_index.getX();
        }

        public int getLargestY()
        {
            checkAllForLargest();
            return m_largest.getY();
        }

        public int posLargestY()
        {
            checkAllForLargest();
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
