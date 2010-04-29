package Model;
import java.util.*;

/**
 * Generates a wordsearch puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle.
 *
 * @author Team Yalpha, specifically Patrick Martin, Jordan Hollinger
 * @version 1.0
 */
public class Wordsearch extends Puzzle {

        //Max size should be 20 

       char map_Randomize [][] = null;
       
        public void generate(final WordList words, int pSize)
        {
            RemovedList = new WordMap(words, pSize);
           
            FinishedList = new WordMap();

            FinishedList = TEST(RemovedList);
            
            populateWordMatrix(FinishedList);
        }

        public WordMap TEST(WordMap words)
        {
            WordMap temp = new WordMap();
            WordMap bad = new WordMap();
            try
            {
                temp.setBound(words.getBound());
                bad.setBound(temp.getBound());
            }
            catch (Exception e)
            {
                System.out.println("BOUND CANT BE EQUAL TO ZERO (aka board can't have zero size)");
            }

            Thread stop = new Thread(new PuzzleStop(this));
            stop.start();

            int size = words.size();

            for(int i =0; i < size; i++)
            {
                if(!getContinue())
                {
                    break;
                }
                
                Word w = randomizeWord(words, bad);
                if(w != null)
                {
                    if(checkWord(w,temp) && !correctWordRandomly(w,temp) && !correctBackup(w,temp))
                    {
                        bad.add(w);
                    }
                    else
                    {
                        temp.add(w);
                    }
                }
            }

            setContinue(false);

            words.add(bad);
            return temp;
        }

        /**  VERY LABOR INTENSIVE...
         *  1)  BASICLY goes through all the characters in tempW and all the characters in all the words in tempMap
         *      and checks for a character in common.
         *  2)  Once a common letter is found between tempW and a word from tempMap the two words are overlapped
         *  3)  Then tempW is given a random direction that opposes the word in tempMap
         *  4)  If not possible then return false.
         * 
         *  tempW collision word, tempMap arraylist of words to check VS tempW
         *  Needs work - Exits at the first sign of a character match
         */
        private boolean correctWordRandomly(Word tempW, final WordMap tempMap)
        {
            //  loop through all the words in tempMap
                for(int i =0; i < tempMap.size(); i++)
                {
                    //  loop through all the characters of the selected word in tempMap
                    Word mapsWord = tempMap.get(i);
                    for(int j = 0; j < mapsWord.size(); j++)
                    {
                        //  loop through all the characters in tempW
                        for(int k = 0; k < tempW.size(); k++)
                        {
                            if(!getContinue())
                            {
                                return false;
                            }
                            // if the words share a letter
                            if(tempW.getCharAt(k) == mapsWord.getCharAt(j))
                            {
                                // generate random direction
                                correctByRandomDirection(tempW,mapsWord);
                                // check if the word doesn't have a TRUE Collision with any other surounding word
                                if(!checkWord(tempW,tempMap))
                                {
                                    return true;
                                }
                                else
                                {
                                    // If a TRUE collision happends after the randomDirection, then change the direction of the word
                                    // Ex: work :becomes: krow (vis versa)
                                    if(tempW.getDown())
                                    {
                                        tempW.setUp(true);
                                    }
                                    else if(tempW.getUp())
                                    {
                                        tempW.setDown(true);
                                    }
                                    if(tempW.getRight())
                                    {
                                        tempW.setLeft(true);
                                    }
                                    else if(tempW.getLeft())
                                    {
                                        tempW.setRight(true);
                                    }
                                }
                                // check the word now that its direction is changed
                                if(!checkWord(tempW,tempMap))
                                {
                                    return true;
                                }

                            }
                        }
                    }
                }
                
                return false;
        }

         /**    Last step in which the word is fitted into the WordMap
          *    tempW(word to be added to tempMap) | index is the word in tempMap that tempW collided with
          */
        private boolean correctBackup(Word tempW, WordMap tempMap)
        {
            boolean [][] space = findEmptySpace(tempMap);
            
            if(correctByEmptySpace(tempW,space))
            {
                return true;
            }

            return false;
            
        }

        /**
         *  returns MxN matrix of boolean values (true means empty space, false means non-empty space)
         */
        private boolean [][] findEmptySpace(final WordMap tempMap)
        {
            boolean [][] boolMap = new boolean[tempMap.getBound()][tempMap.getBound()];

            //intalizes all values to true
            for(int i=0; i< boolMap.length; i++)
            {
                for(int j=0; j<boolMap[i].length; j++)
                {
                    boolMap[i][j] = true;
                }
            }

            // all character's x/y positions in the WordMap are set to false
            // The characters are not empty spaces
            for(int i = 0; i < tempMap.size(); i++)
            {
                Word tempW = tempMap.get(i);
                for(int j =0; j < tempW.size(); j++)
                {
                    int y = tempW.getCharPosY(j);
                    int x = tempW.getCharPosX(j);
                    
                    boolMap[y][x] = false;
                }
            }
            
            return boolMap;
        }

        //STILL NEEDS
        //Search for - diaginals(x2)
        //output - diaginals(x2) & updown
        //Finds blocks of empty space in the (up/down), (left/right), (diaginal)
        private boolean correctByEmptySpace(Word tempW, final boolean tempBM [][])
        {
            ArrayList<ArrayList<ArrayList<Point>>> freeSpaceLR = new ArrayList<ArrayList<ArrayList<Point>>>(); //Left-Right
            ArrayList<ArrayList<ArrayList<Point>>> freeSpaceUD = new ArrayList<ArrayList<ArrayList<Point>>>(); //Up-Down
            ArrayList<ArrayList<ArrayList<Point>>> freeSpaceDR = new ArrayList<ArrayList<ArrayList<Point>>>(); //Down-Right
            ArrayList<ArrayList<ArrayList<Point>>> freeSpaceDL = new ArrayList<ArrayList<ArrayList<Point>>>(); //Down-Left
            
            ///SEARCH FOR FREE LEFT RIGHT BLOCKS
            int countX =0;
            int countY =0;
            freeSpaceLR.add(new ArrayList<ArrayList<Point>>());
            freeSpaceLR.get(countY).add(new ArrayList<Point>());
            for(int i=0; i<tempBM.length; i++)
            {
                
                for(int j =0; j <tempBM[i].length; j++)
                {
                    if(tempBM[i][j] )
                    {
                        freeSpaceLR.get(countY).get(countX).add(new Point(j,i));
                    }
                    else
                    {
                        freeSpaceLR.get(countY).add(new ArrayList<Point>());
                        countX++;
                        
                    }
                }
                freeSpaceLR.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceLR.get(countY).add(new ArrayList<Point>());
                countX =0;
            }

            if(!getContinue())
                {
                    return false;
                }

            ///SEARCH FOR FREE UP DOWN BLOCKS
            countX=0;
            countY=0;
            freeSpaceUD.add(new ArrayList<ArrayList<Point>>());
            freeSpaceUD.get(countY).add(new ArrayList<Point>());
            for(int i=0; i<tempBM.length; i++)
            {
                for(int j =0; j <tempBM[i].length; j++)
                {
                    if(tempBM[j][i] )
                    {
                        freeSpaceUD.get(countY).get(countX).add(new Point(i,j));
                    }
                    else
                    {
                        freeSpaceUD.get(countY).add(new ArrayList<Point>());
                        countX++;

                    }
                }
                freeSpaceUD.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceUD.get(countY).add(new ArrayList<Point>());
                countX =0;
            }

             if(!getContinue())
                {
                    return false;
                }


            ////SEARCHES FOR FREE DOWN/LEFT
            int row =0;
            countY =0;
            countX =0;
            freeSpaceDL.add(new ArrayList<ArrayList<Point>>());
            freeSpaceDL.get(countY).add(new ArrayList<Point>());
            
            while(row < tempBM[0].length-1)
            {
                int tX = row;
                int tY = 0;
                while(tX > -1 && tY < tempBM.length)
                {
                    if(tempBM[tY][tX])
                    {
                        freeSpaceDL.get(countY).get(countX).add(new Point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<Point>());
                        countX++;
                    }
                    tX -=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<Point>());
                countX =0;
                row++;
            }
            int column =0;
            while(column < tempBM.length)
            {
                int tX = row;
                int tY = column;
                while(tX > -1 && tY < tempBM.length)
                {
                    if(tempBM[tY][tX])
                    {
                        freeSpaceDL.get(countY).get(countX).add(new Point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<Point>());
                        countX++;
                    }
                    tX -=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<Point>());
                countX =0;
                column++;
            }

            if(!getContinue())
                {
                    return false;
                }



            //SEARCH FOR FREE BLOCKS OF DOWN/RIGHT

            row = tempBM[0].length;
            countY =0;
            countX =0;
            freeSpaceDR.add(new ArrayList<ArrayList<Point>>());
            freeSpaceDR.get(countY).add(new ArrayList<Point>());

            while(row > 0)
            {
                int tX = row;
                int tY = 0;
                while(tX < tempBM[0].length && tY < tempBM.length)
                {
                    if(tempBM[tY][tX])
                    {
                        freeSpaceDR.get(countY).get(countX).add(new Point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDR.get(countY).add(new ArrayList<Point>());
                        countX++;
                    }
                    tX +=1;
                    tY +=1;
                }
                freeSpaceDR.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceDR.get(countY).add(new ArrayList<Point>());
                countX =0;
                row--;
            }
            column =0;
            while(column < tempBM.length)
            {
                int tX = row;
                int tY = column;
                while(tX < tempBM[0].length && tY < tempBM.length)
                {
                    if(tempBM[tY][tX])
                    {
                        freeSpaceDR.get(countY).get(countX).add(new Point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDR.get(countY).add(new ArrayList<Point>());
                        countX++;
                    }
                    tX +=1;
                    tY +=1;
                }
                freeSpaceDR.add(new ArrayList<ArrayList<Point>>());
                countY++;
                freeSpaceDR.get(countY).add(new ArrayList<Point>());
                countX =0;
                column++;
            }

            
             if(!getContinue())
                {
                    return false;
                }



            //  SETS WORD IN UP/DOWN or LEFT/RIGHT or DOWN/RIGHT or DOWN/LEFT free blocks (chooses L/R or U/D randomly)
            
            boolean correct = false;
           while(!correct)
           {
               int randomUD_LR_DR_DL = myRandom.nextInt(4);
            if(randomUD_LR_DR_DL == 0)
            {
                //only sets tempW in any of the free blocks in the LEFT RIGHT blocks
                for(int i =0; i<freeSpaceLR.size(); i++)
                {
                    for(int j=0; j<freeSpaceLR.get(i).size(); j++)
                    {
                        if(!getContinue())
                        {
                            return false;
                        }

                        if(freeSpaceLR.get(i).get(j).size() >= tempW.size())
                        {
                                 
                                correct = true;
                                int tX = freeSpaceLR.get(i).get(j).get(0).getX();
                                int tY = freeSpaceLR.get(i).get(j).get(0).getY();

                                tempW.setRight(true);
                                tempW.setDown(false);
                                tempW.setUp(false);
                                tempW.setFirstCharPos(tX, tY);
                                break;
                        }
                    }
                }
            }
            else if(randomUD_LR_DR_DL == 1)
            {
                //only sets tempW in any of the free blocks in the UP DOWN blocks
                for(int i =0; i<freeSpaceUD.size(); i++)
                {
                    for(int j=0; j<freeSpaceUD.get(i).size(); j++)
                    {
                        if(freeSpaceUD.get(i).get(j).size() >= tempW.size())
                        {
                             if(!getContinue())
                            {
                                return false;
                            }
                                correct = true;
                                int tX = freeSpaceUD.get(i).get(j).get(0).getX();
                                int tY = freeSpaceUD.get(i).get(j).get(0).getY();

                                tempW.setDown(true);
                                tempW.setRight(false);
                                tempW.setLeft(false);
                                
                                tempW.setFirstCharPos(tX, tY);
                                break;
                        }
                    }
                }
            }
            else if(randomUD_LR_DR_DL == 2)
            {
                //only sets tempW in any of the free blocks in the DOWN RIGHT blocks
                for(int i =0; i<freeSpaceDR.size(); i++)
                {
                    for(int j=0; j<freeSpaceDR.get(i).size(); j++)
                    {
                        if(freeSpaceDR.get(i).get(j).size() >= tempW.size())
                        {
                             if(!getContinue())
                            {
                                return false;
                            }

                                correct = true;
                                int tX = freeSpaceDR.get(i).get(j).get(0).getX();
                                int tY = freeSpaceDR.get(i).get(j).get(0).getY();

                                tempW.setRight(true);
                                tempW.setDown(true);
                                tempW.setFirstCharPos(tX, tY);
                                break;
                        }
                    }
                }
            }
            else
            {
                //only sets tempW in any of the free blocks in the DOWN LEFT blocks
                for(int i =0; i<freeSpaceDL.size(); i++)
                {
                    for(int j=0; j<freeSpaceDL.get(i).size(); j++)
                    {
                        if(freeSpaceDL.get(i).get(j).size() >= tempW.size())
                        {
                             if(!getContinue())
                            {
                                return false;
                            }
                                correct = true;
                                int tX = freeSpaceDL.get(i).get(j).get(0).getX();
                                int tY = freeSpaceDL.get(i).get(j).get(0).getY();

                                tempW.setLeft(true);
                                tempW.setDown(true);
                                //tempW.setDown(true);
                                tempW.setFirstCharPos(tX, tY);
                                break;
                        }
                    }
                }
            }
           }
            return true;
            
        }
        

        /** check direction of tempB to set tempA's direction randomly such that it is opposing tempB.
            WordExample: mapsWord is down to the right then, other word could be: (down,left),(up,right),(-,right),(-,left)
         */
        private void correctByRandomDirection(Word tempA,final Word tempB)
        {
            boolean up = false;
            boolean down = false;
            boolean right = false;
            boolean left = false;

            while((tempA.getDown() == tempB.getDown() || tempA.getUp() == tempB.getUp()) && (tempA.getRight() == tempB.getRight() || tempA.getLeft() == tempB.getLeft()))
            {
                down = myRandom.nextBoolean();
                tempA.setDown(down);

                up = myRandom.nextBoolean();
                tempA.setUp(up);

                left = myRandom.nextBoolean();
                tempA.setLeft(left);

                right = myRandom.nextBoolean();
                tempA.setRight(right);

                
            }
        }

        public char [][] getMatrixRandomize()
        {
            return map_Randomize;
        }

        @Override
        protected void populateWordMatrix(final WordMap tempMap)
       {
            super.populateWordMatrix(tempMap);
            char [][] map_R = super.getMatrixSolution();
            map_Randomize = new char [tempMap.getBound()][tempMap.getBound()];

            for(int i=0; i < map_Randomize.length; i++)
            {
                for(int j = 0; j < map_Randomize[i].length; j++ )
                {

                    if(map_R[i][j] == '~')
                    {
                        map_Randomize[i][j] = (char) (myRandom.nextInt(24) + 97); //function should generate random letters from a(97) to z(122)
                    }
                    else
                    {
                        map_Randomize[i][j] = map_R[i][j];
                    }

                }
            }

    }

    //puts all the words into the char matrix(MxM)
    public void populateWordMatrix(char [][] tempMap)
    {
        map_Randomize = new char[tempMap.length][tempMap[0].length];

        for(int i=0; i < tempMap.length; i++)
        {
            for(int j = 0; j < tempMap[i].length; j++ )
            {
                map_Randomize[i][j] = tempMap[i][j];
            }
        }

    }

    /** Check for:
     *  overlap/collision
     *  returns true if "tempW" collided
     *  returns false for no collision
     */
    public boolean checkWord(final Word tempW, final WordMap tempMap)
    {
        for(int i=0; i < tempMap.size(); i++)
        {
           if(tempW.checkCollision(tempMap.get(i)))
           {
               return true;
           }
        }

        if(tempW.checkBounds(tempMap.getBound()))
        {
            return true;
        }

        return false;
    }

    private Word randomizeWord(WordMap tempMap, WordMap badMap)
    {
        Word tempW = super.randomizeWord(tempMap,tempMap.size());

        if(check_correctBounds(tempW,tempMap))
        {
            badMap.add(tempW);
            return null;
        }

        return tempW;
    }

    /**
     * Gets the type of puzzle (Crossword or Wordsearch)
     * @return the word search puzzle type
     */
    public Model.PuzzleType getPuzzleType()
    {
        return Model.PuzzleType.WORDSEARCH;
    }
}
