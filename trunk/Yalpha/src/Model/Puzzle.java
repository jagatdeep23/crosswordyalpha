package Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
/**
 * An abstract class that has an arcinal of protected classs that help subdue problems
 * that other more general wordPuzzles may have. 
 *
 * @author Team Yalpha, specifically Patrick Martin,Jordan Hollinger
 * @version 1.0
 */
public abstract class Puzzle {

    /**
     *  Class PuzzleStop
     *      ++This class is used to define what a thread should do when started.
     *      ++The thread should just wait till 2 seconds have passed and set a boolean to false
     *      ++This signal lets all the functions listeneing to that boolean know that they should stop at the next available "checkpoint"
     * 
     *
     *  @author Team Yalpha: Patrick Martin
     *  @version 1.0
     */
    protected class PuzzleStop implements Runnable
        {
            Puzzle m_puz;
            PuzzleStop(Puzzle temp)
            {
                m_puz = temp;
                setContinue(true);
            }
            public void run()
            {
                long start = System.currentTimeMillis();
                while(m_puz.getContinue())
                {
                    long current = System.currentTimeMillis();
                    if((current-start) > 2000)
                    {
                        m_puz.setContinue(false);
                    }
                }
            }
        }

    //Finished Puzzle with all words ADDED to puzzle
    WordMap FinishedList = new WordMap();

    //Removed List with all words NOT added to the puzzle
    WordMap RemovedList = new WordMap();

    //Boolean that lets all the timed based functions know to continue or stop
    boolean m_continue = false;
    
    Semaphore a = new Semaphore(1);
    Semaphore mutex = new Semaphore(1);

    protected Random myRandom = new Random();

    private char [][] map = null;

    public int size()
    {
        return FinishedList.size();
    }
            
    /**
     * Function creates a specific puzzle
     * @param words list of words to use in generate
     */
    public abstract void generate(final WordList words, int pSize);

        /**
         * This function is used in parity with a Timmed-Thread.
         *  Mainly a Timmed-Thread will use this funtion to either tell...
         *      A.) Functions when to push forward (Usually after timer is started and before the timmer is stopped).
         *      B.) Functions when to stop (Usually after timer is stopped and before the timmer is started).
         *
         * @param pBool (Tell weither or not to continue code or to stop code)
         */
        public void setContinue(boolean pBool)
        {
            try {
                mutex.acquire();
                a.acquire();
            } catch (InterruptedException ie) {
                System.out.println("aquire semaphore error..");
                System.exit(0);
            }

            m_continue = pBool;

            a.release();
            mutex.release();
        }

        /**
         * This function is used in parity with a Timmed-Thread.
         *
         *  Used by other functions that are based on the timmed-Thread.
         *
         * @return (true: continue on with code) || (false: stop code here)
         */
        public boolean getContinue()
        {
            boolean moveOn;
            try {
                mutex.acquire();
                a.acquire();
            } catch (InterruptedException ie) {
                System.out.println("aquire semaphore error..");
                System.exit(0);
            }

            moveOn = m_continue;

            a.release();
            mutex.release();

            return moveOn;
        }

    /**
     *
     * @return if used by wordsearch it returns matrix with true words hidden by random letters and crossword returns the same as MatrixSolution
     */
    public abstract char [][] getMatrixRandomize();

    /**
     * returns the solution to the puzzle
     * @return
     */
    public char [][] getMatrixSolution()
    {
         return map;
    }

    /**
     *
     * @return WordMap that has all the words being used
     */
    public WordMap getWordsUsed()
    {
        return FinishedList;
    }


    /**
     *
     * @param word
     */
    public void setWordsUsed(WordMap words)
    {
        FinishedList = words;
    }

    /**
     * return words that aren't used in the puzzle
     * @return
     */
    public WordMap getWordsNotUsed()
    {
        return RemovedList;
    }

    
   /**
    * Puts all the words into the char matrix(MxM)
    * @param tempMap word list
    */
    protected void populateWordMatrix(final WordMap tempMap)
    {
          map = new char [tempMap.getBound() ][tempMap.getBound()];

        for(int i=0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++ )
            {
               map[i][j] = '~'; //function should generate random letters from a(97) to z(122)
            }
        }

        for(int i=0; i < tempMap.size(); i++)
        {
            Word tempW = tempMap.get(i);
         for(int j = 0; j < tempW.size(); j++ )
            {
                int y = tempW.getCharPosY(j);
                int x = tempW.getCharPosX(j);
                map[y][x] = tempW.getCharAt(j);
            }
        }
 
    }

        /**
         *
         * Makes sure that word is inside the bounding box.
         * If it isnt inside the bounding box then move it there.
         * Check again after moving the word to inside the bounding box and make sure the word fits
         * 
         * @param tempW - Word being checked
         * @param tempMap - Map containing the bounding box
         * @return ( true: if word doesn't fit in bounding box) || (false: if the word does fit in the bounding box)
         */
        public boolean check_correctBounds(Word tempW, WordMap tempMap)
        {
            if(tempW.checkBounds(tempMap.getBound()))
            {
                if(tempW.moveBounds(tempMap.getBound()))
                {
                    return true;
                }
            }
            return false;
        }



        /**
         *  removes word from tempMap
         *  gives word random direction and position
         *  then returns the randomized word
         *  Largest: the largest word in tempMap before any removes/changes are made to the first fully populated instance of tempMap
         *  Assuming that user wants to go from "zero" to "end"
         */
        protected Word randomizeWord(WordMap tempMap, int end)
        {
            int index = myRandom.nextInt(end);

            Word tempW = tempMap.remove(index);

            int rX = myRandom.nextInt(tempMap.getBound());
            int rY = myRandom.nextInt(tempMap.getBound());

            tempW.setFirstCharPos(rX, rY);

            int rLR = myRandom.nextInt(3);
            int rUD = myRandom.nextInt(3);

            if(rUD == 0)
            {
                tempW.setUp(true);
            }
            else if(rUD == 1)
            {
                tempW.setDown(true);
            }
            else if (rUD == 2)
            {
                tempW.setUp(false);
                tempW.setDown(false);
            }

            if(rLR == 0)
            {
                tempW.setLeft(true);
            }
            else if(rLR == 1)
            {
                tempW.setRight(true);
            }
            else if(rLR == 2)
            {
                tempW.setLeft(false);
                tempW.setRight(false);
            }

            return tempW;
        }

    /**
     * Populates Matrix A with all the char Values in  matrix B
     * @param tempMap
     */
    public void populateWordMatrix(char [][] tempMap)
    {
        map = new char[tempMap.length][tempMap[0].length];
        
        for(int i=0; i < tempMap.length; i++)
        {
            for(int j = 0; j < tempMap[i].length; j++ )
            {
                map[i][j] = tempMap[i][j];
            }
        }

    }
    
    /**
     * Populates Matrix A with all the char Values in  matrix B
     * @param tempMap
     */
    public void populateSolutionMatrix(char [][] tempMap)
    {
        map = new char[tempMap.length][tempMap[0].length];

        for(int i=0; i < tempMap.length; i++)
        {
            for(int j = 0; j < tempMap[i].length; j++ )
            {
                map[i][j] = tempMap[i][j];
            }
        }

    }
    
    /**
     * Gets the type of puzzle (Crossword or Wordsearch)
     * @return the puzzle type
     */
    public abstract Model.PuzzleType getPuzzleType();
}

