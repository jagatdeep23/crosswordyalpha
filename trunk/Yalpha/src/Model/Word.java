package Model;

/**
 * This class contains the letters of the word, the position of each character,
 * the direction, and a boolean to check whether or not the character positions are negative.
 *
 * @author Team Yalpha, specifically Patrick Martin,Jordan Hollinger
 * @version 1.0
 */
 public class Word implements Cloneable
    {
        public final static int num_direction = 5;
        private char [] m_letters = null;
        private Point [] m_pos = null;
        private Point m_offset = null;
        private Point m_smallest;
        private Point m_largest;
        private boolean m_up, m_down, m_left, m_right;
        private boolean m_checkPosNeg = true;

        /**
         * @param temp String containing the word
         * @param checkPos Positive position checking on or off
         */
        Word(String temp, boolean checkPos)
        {
           this(temp);
           m_checkPosNeg = checkPos;
           
        }

        /**
         * @param temp String containing the word
         */
        Word(String temp)
        {
           initalizePoints(temp.length());

           m_offset = new Point();
           m_largest = new Point();
           m_smallest = new Point();

           setString(temp);
           setRight(true);
        }

        /**
         * Copy Constructor
         * @param tempW Word to copy
         */
        Word(Word tempW)
        {
           initalizePoints(tempW.m_pos.length);

           m_offset = new Point();
           m_largest = new Point();
           m_smallest = new Point();

            m_offset.set(tempW.m_offset);
            m_largest.set(tempW.m_largest);
            m_smallest.set(tempW.m_smallest);

            m_letters = new char[tempW.m_letters.length];
            for(int i = 0; i < m_letters.length; i++)
            {
                m_letters[i] = tempW.m_letters[i];
            }

            for(int i = 0; i < m_pos.length; i++)
            {
                m_pos[i].set(tempW.m_pos[i]);
            }

            m_left = tempW.m_left;
            m_right = tempW.m_right;
            m_up = tempW.m_up;
            m_down = tempW.m_down;

            m_checkPosNeg = tempW.m_checkPosNeg;
            
        }

        /**
         * Initalizes all the Points
         * @param tempS the number of point that need to be initalized
         */
        private void initalizePoints(int tempS)
        {
            m_pos = new Point[tempS];

           for(int i =0; i < m_pos.length; i++ )
           {
              m_pos[i] = new Point();
           }
        }

        /**
         * gets character c's X position
         * @param c character index
         * @return X value
         */
        public final int getCharPosX(int c)
        {
            return m_pos[c].getX();
        }

        /**
         *
         * gets character c's Y position
         * @param c character index
         * @return Y value
         */
        public final int getCharPosY(int c)
        {
            return m_pos[c].getY();
        }

        /**
         * Gets the largest X value of all the characters in the word
         * @return the Largest X value out of all the characters
         */
        public int getLargestX()
        {
            return m_largest.getX();
        }

        /**
         * Gets the largest Y value of all the characters in the word
         * @return the Largest Y value out of all the characters
         */
        public int getLargestY()
        {
            return m_largest.getY();
        }

        /**
         * Gets the smallest Y value of all the characters in the word
         * @return the Smallest Y value out of all the characters
         */
        public int getSmallestY()
        {
            return m_smallest.getY();
        }

        /**
         * Gets the largest X value of all the characters in the word
         * @return the Smallest X value out of all the characters
         */
        public int getSmallestX()
        {
            return m_smallest.getX();
        }

        /**
         * Determines if the direction of the word is up
         * @return Up - true/false
         */
        public boolean getUp()
        {
            return m_up;
        }

        /**
         * Determines if the direction of the word is down
         * @return Down - true/false
         */
        public boolean getDown()
        {
            return m_down;
        }

        /**
         * Determines if the direction of the word is right
         * @return Right - true/false
         */
        public boolean getRight()
        {
            return m_right;
        }

        /**
         * Determines if the direction of the word is left
         * @return Left - true/false
         */
        public boolean getLeft()
        {
            return m_left;
        }

        /**
         * Gets the number of letters in the word
         * @return number of characters
         */
        public int size()
        {
           return m_letters.length;
        }

        /**
         * Gets a character array containing the word
         * @return array of characters in the word
         */
        public char [] getString()
        {
            return m_letters;
        }

        /**
         * Gets a String containing the word
         * @return String containing the word
         */
        public String toString()
        {
            return String.copyValueOf(m_letters);
        }

        /**
         * Gets the character at a given index
         * @param index is index of the characters
         * @return character at index
         */
        public char getCharAt(int index)
        {
            return m_letters[index];
        }

        /**
         * Sets the position of the first character at (chX,chY)
         * @param chX - X value
         * @param chY - Y value
         */
        public void setFirstCharPos(int chX, int chY)
        {
            m_pos[0].setX(chX);
            m_pos[0].setY(chY);
            resetPos();
        }

        /**
         * Sets direction of the word to up or not
         * @param temp true/false
         */
        public void setUp(boolean temp)
        {
            m_up = temp;
            if(m_up == true)
            {
                m_offset.setY(-1);
                m_down = false;
            }
            else if(m_down == false)
            {
                m_offset.setY(0);
            }
             //if all directions are false then this resetpos is skipped
            /*if(CheckDirection())
            {
                resetPos();
            }*/
            CheckDirection();
            resetPos();
        }

        /**
         * Sets direction down the word to up or not
         * @param temp true/false
         */
        public void setDown(boolean temp)
        {
            m_down = temp;
            if(m_down == true)
            {
                m_offset.setY(1);
                m_up = false;
            }
            else if(m_up == false)
            {
                m_offset.setY(0);
            }
             //if all directions are false then this resetpos is skipped
            CheckDirection();
            resetPos();
        }

        /**
         * Sets direction down the word to right or not
         * @param temp true/false
         */
        public void setRight(boolean temp)
        {
            m_right = temp;

            if(m_right == true)
            {
                m_offset.setX(1);
                m_left = false;
            }
            else if(m_left == false)
            {
                m_offset.setX(0);
            }
             //if all directions are false then this resetpos is skipped
            CheckDirection();
            resetPos();
        }

        /**
         * Sets direction down the word to left or not
         * @param temp true/false
         */
        public void setLeft(boolean temp)
        {
            m_left = temp;
            if(m_left == true)
            {
                m_offset.setX(-1);
                m_right = false;
            }
            else if(m_right == false)
            {
                m_offset.setX(0);
            }

            //if all directions are false then this resetpos is skipped
            CheckDirection();
            resetPos();
        }

        /**
         * Creates a duplicate of this instance of Word
         * @return Clone of this word
         */
        @Override
        public Word clone()
        {
            Word temp = null;
            try
            {
                temp = (Word)super.clone();
            }
            catch(CloneNotSupportedException e)
            {
                throw new RuntimeException("Clone Not supported");
            }

            temp.m_pos = new Point[m_pos.length];
            for(int i =0; i < m_pos.length; i++)
            {
                temp.m_pos[i] = m_pos[i].clone();
            }
            temp.m_offset = m_offset.clone();
            temp.m_smallest = m_smallest.clone();
            temp.m_largest = m_largest.clone();

            return temp;

        }

        /**
         * Sets the content of this word to the content of the string
         * @param temp String representing the new word
         */
        public void setString(String temp)
        {
            m_letters = (char [])temp.toCharArray().clone();
            initalizePoints(m_letters.length);
        }

        /**
         * Resets the positions of each character based on direction and the first character's position.
         */
        public void resetPos()
        {
           if(m_letters.length != m_pos.length)
           {
               System.out.println("LETTER SIZE DOESN'T MATCH POS!!");
               System.exit(0);
           }
           else
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

                if(m_checkPosNeg)
                {
                    checkNegatives();
                }

                ObtainGreatest();
                ObtainSmallest();
           }
        }
         
       /**
         * if the position of the characters is negative then move the characters into a positive postion
         */
        private void checkNegatives()
        {
            int leastX = 0;
            int leastY = 0;
            for(int i = 0; i < m_pos.length; i++ )
            {
                int gX = m_pos[i].getX();
                int gY = m_pos[i].getY();

                if(gX < 0 && gX < leastX)
                {
                    leastX = gX;
                }
                if(gY < 0 && gY < leastY)
                {
                    leastY = gY;
                }
                //System.out.println("PrevPosX: " + m_pos[i-1].getX() + "\nCurrPosX: " + m_pos[i].getX() + "\nOffsetX: " + m_offset.getX());
            }

            for(int i = 0; i < m_pos.length; i++ )
            {
                int gX = m_pos[i].getX();
                m_pos[i].setX(gX-leastX);

                int gY = m_pos[i].getY();
                m_pos[i].setY(gY-leastY);
            }

        }

        /**
         * Obtains Greatest X & Y Value of the word
         */ 
        private void ObtainGreatest()
        {
            
            // max x
            if (m_right)
            {
                m_largest.setX(m_pos[m_pos.length - 1].getX());
            }
            else
            {
                m_largest.setX(m_pos[0].getX());
            }
            
            // max Y
            if (m_down)
            {
                m_largest.setY(m_pos[m_pos.length - 1].getY());
            }
            else
            {
                m_largest.setY(m_pos[0].getY());
            }
        }

        /**
         * Obtains Smallest X & Y Value of the word
         */
        private void ObtainSmallest()
        {
            // min x
            if (m_left)
            {
                m_smallest.setX(m_pos[m_pos.length - 1].getX());
            }
            else
            {
                m_smallest.setX(m_pos[0].getX());
            }

            // min Y
            if (m_up)
            {
                m_smallest.setY(m_pos[m_pos.length - 1].getY());
            }
            else
            {
                m_smallest.setY(m_pos[0].getY());
            }

        }

        
       /**
        * If the word has no direction then the default direction is right and CheckDirection() returns false
        * when all the directions are false then default direction is true
        * @return true when at least one direction is set to true
        */
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

        /**
         * Determines if this word overlaps another
         * @param tempW is a word to check VS this for an overlapped position and character
         * @return (true: if words overlap) || (false: if words dont overlap)
         */
        public boolean overlap(Word tempW)
        {
            if(tempW != null)
            {
               for(int i =0; i<size(); i++)
               {             
                    for(int j= 0; j < tempW.size(); j++)
                    {
                        if(comparePos(i, tempW, j ))
                        {
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        /**
         * Checks all the words in tempM and make sure that they don't collide with THIS word
         * @param tempM
         * @return
         */
        public boolean checkCollision(final WordMap tempM)
        {
            for(Word tempW: tempM)
            {
                if(checkCollision(tempW))
                {
                    return true;
                }
            }
            return false;
        }

        
        /**
         * returns TRUE for a collision and FALSE for a non-collision
         * @param tempW
         * @return
         */
        public boolean checkCollision(final Word tempW)
        {
            if(tempW != null)
            {
                return ( checkLettersPos(tempW) );
            }
            return false;
        }

        // If a TRUE collision occured then it will return true, else returns false.
        // TRUE collision = when a position matches, BUT letter doesn't.
        // SO... When a letter and position of the both words are equal this is NOT a TRUE collision
        /**
         *
         * If a TRUE collision occured then it will return true, else returns false.
         * TRUE collision = when a position matches, BUT letter doesn't.
         * SO... When a letter and position of the both words are equal this is NOT a TRUE collision
         *
         * @param tempW
         * @return
         */
        private boolean checkLettersPos(final Word tempW)
        {
            if(tempW != null)
            {
                boolean rtn = true;
                int index = 0;

                while(rtn && (index < size()) )
                {
                    for(int j= 0; ((j < tempW.size()) && rtn); j++)
                    {
                        if(comparePos(index, tempW, j ) )
                        {
                            rtn = compareChar(index, tempW, j);
                        }
                    }
                    index++;
                }
                return (!rtn);
            }
            return false;
        }

        /**
         * Compares the position at a given index of this word with the position at a given index of another word
         * @param mytempI Index of character in this word
         * @param tempW word to compare to this word
         * @param tempI character index of tempW
         * @return true if the character's position in "this" word at index mytempI and character's position in tempW at index tempI
         */
        private boolean comparePos(final int mytempI, final Word tempW, final int tempI)
        {
            if(tempW != null)
            {
                return (tempW.getCharPosX(tempI) == getCharPosX(mytempI) && tempW.getCharPosY(tempI) == getCharPosY(mytempI));
            }
            return false;
        }

        /**
         * Compares if the characters of this word and passed word tempW are the same
         * @param mytempI
         * @param tempW
         * @param tempI
         * @return true if characters match
         */
        private boolean compareChar(final int mytempI, final Word tempW, final int tempI)
        {
            if(tempW != null)
            {
                return (tempW.getCharAt(tempI) == getCharAt(mytempI));
            }
            return false;
        }

        
        /**
         *
         * bounding box - aka the size of the box that the puzzle is in
         * returns true if the word is greater then the bound
         * returns false if the word is inside the bound
         *
         * @param bound
         * @return
         */
        public boolean checkBounds(int bound)
        {
            if(getLargestX() >= bound)
            {
                return true;
            }
            if(getLargestY() >= bound)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        /**
         * Moves the word to fit inside of the given bound
         * 
         * @param bound
         * @return
         */
        public boolean moveBounds(int bound)
        {
            int ChangeX = getCharPosX(0);
            int ChangeY = getCharPosY(0);
            int LargestX = getLargestX();
            int LargestY = getLargestY();

            if(bound <= getLargestX())
            {
                ChangeX = ChangeX - (LargestX-bound) -1;
            }

            if(bound <= getLargestY())
            {
                ChangeY = ChangeY - (LargestY-bound) -1;
            }


            setFirstCharPos(ChangeX,ChangeY);

            ObtainGreatest();
            ObtainSmallest();

            return checkBounds(bound);
        }
    }
