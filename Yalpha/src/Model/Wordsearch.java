package Model;
import java.util.Random;
import java.util.*;

/**
 * Generates a wordsearch puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle.
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */
public class Wordsearch extends Puzzle {

        //Max size should be 20 

       private Random myRandom = new Random();
       
        public void generate(final WordList words)
        {
            WordMap randomWords = new WordMap(words);
            WordMap mappedWords = new WordMap();

            //randomWords.get(0).setFirstCharPos(0,1);
            //randomWords.get(0).resetPos();
            //randomWords.checkAllForLargest();
            //recursivePuzzleSearch(randomWords, mappedWords);
            mappedWords = TEST(randomWords);
            System.out.println(randomWords);
           // System.out.println("WordSearch-Generate");
           // System.out.println(words);
            populateWordMatrix(mappedWords);
        }

        public WordMap TEST(WordMap words)
        {
            //System.out.println();
            WordMap temp = new WordMap();
            temp.setBound(words.getBound());
            WordMap bad = new WordMap();

            //  lrg is obtained outside the loop because the longest word would change everytime a word was removed from "words"
            //  So lrg is constant through the whole loop
            //int lrg = words.getLongestWord();

            int size = words.size();
           // Word w = randomizeWord(words, bad);
           // temp.add(w);
            for(int i =0; i < size; i++)
            {
                Word w = randomizeWord(words, bad);
                if(w != null)
                {
                    if( checkWord(w,temp) && !correctWordRandomly(w,temp) && !correctBackup(w,temp))
                    {
                        bad.add(w);
                        System.out.println("NOT adding: " + w);
                    }
                    else
                    {
                        temp.add(w);
                    }
                }
            }
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

        /*public void printW(Word tempW)
        {
            System.out.println(tempW);

            System.out.println("PosX[0]: " + tempW.getCharPosX(0));
            System.out.println("PosY[0]: " + tempW.getCharPosY(0));

            System.out.println("PosX[LAST]: " + tempW.getCharPosX(tempW.size() -1));
            System.out.println("PosY[LAST]: " + tempW.getCharPosY(tempW.size() -1));

            System.out.println("UP: " + tempW.getUp());
            System.out.println("DOWN: " + tempW.getDown());
            System.out.println("LEFT: " + tempW.getLeft());
            System.out.println("RIGHT: " + tempW.getRight());

        }*/

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

            ////SEARCHES FOR FREE DOWN/LEFT
            int row =0;
            countY =0;
            countX =0;
            freeSpaceDL.add(new ArrayList<ArrayList<Point>>());
            freeSpaceDL.get(countY).add(new ArrayList<Point>());
            
            while(row < tempBM[0].length-1)
            {
                // x-=1;
                // y+=1;
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
                // x-=1;
                // y+=1;
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


            //SEARCH FOR FREE BLOCKS OF DOWN/RIGHT
            




            row = tempBM[0].length;
            countY =0;
            countX =0;
            freeSpaceDR.add(new ArrayList<ArrayList<Point>>());
            freeSpaceDR.get(countY).add(new ArrayList<Point>());

            while(row > 0)
            {
                // x-=1;
                // y+=1;
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
                // x-=1;
                // y+=1;
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
                        if(freeSpaceLR.get(i).get(j).size() >= tempW.size())
                        {
                            //for(int k =0; k <freeSpaceLR.get(i).get(j).size(); k++)
                            {
                                correct = true;
                                int tX = freeSpaceLR.get(i).get(j).get(0).getX();
                                int tY = freeSpaceLR.get(i).get(j).get(0).getY();

                                tempW.setRight(true);
                                tempW.setDown(false);
                                tempW.setUp(false);
                                tempW.setFirstCharPos(tX, tY);
                            }
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
                            //for(int k =0; k <freeSpaceLR.get(i).get(j).size(); k++)
                            {
                                correct = true;
                                int tX = freeSpaceUD.get(i).get(j).get(0).getX();
                                int tY = freeSpaceUD.get(i).get(j).get(0).getY();

                                tempW.setUp(true);
                                tempW.setRight(false);
                                tempW.setLeft(false);
                                
                                tempW.setFirstCharPos(tX, tY);
                            }
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
                            //for(int k =0; k <freeSpaceDR.get(i).get(j).size(); k++)
                            {
                                correct = true;
                                int tX = freeSpaceDR.get(i).get(j).get(0).getX();
                                int tY = freeSpaceDR.get(i).get(j).get(0).getY();

                                tempW.setRight(true);
                                //tempW.setLeft(true);
                                tempW.setUp(true);
                                //tempW.setDown(true);
                                tempW.setFirstCharPos(tX, tY);
                            }
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
                            //for(int k =0; k <freeSpaceDL.get(i).get(j).size(); k++)
                            {
                                correct = true;
                                int tX = freeSpaceDL.get(i).get(j).get(0).getX();
                                int tY = freeSpaceDL.get(i).get(j).get(0).getY();

                                tempW.setLeft(true);
                                tempW.setUp(true);
                                //tempW.setDown(true);
                                tempW.setFirstCharPos(tX, tY);
                            }
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
                //System.out.println("3.6");
                //printW(tempA);
                down = myRandom.nextBoolean();
                tempA.setDown(down);

               // System.out.println("3.7");
               // printW(tempA);
                up = myRandom.nextBoolean();
                tempA.setUp(up);

               // System.out.println("3.8");
                //printW(tempA);
                left = myRandom.nextBoolean();
                tempA.setLeft(left);

               // System.out.println("3.9");
               // printW(tempA);
                right = myRandom.nextBoolean();
                tempA.setRight(right);

                
            }
            
            /*
            if (tempB.getDown() || tempB.getUp()) {
                down = myRandom.nextBoolean();
                tempA.setDown(down);

                up = myRandom.nextBoolean();
                tempA.setUp(up);
            } else {
                boolean chooseUD = myRandom.nextBoolean();
                if (chooseUD) {
                    tempA.setDown(true);
                } else {
                    tempA.setUp(true);
                }
            }

            if (tempB.getRight() || tempB.getLeft()) {
                if ((down == tempB.getDown() && down == true)) {
                    if ((tempB.getRight() == true)) {
                        tempA.setLeft(true);
                    } else if (tempB.getLeft() == true) {
                        tempA.setRight(true);
                    }
                } else if ((up == tempB.getUp() && up == true)) {
                    if ((tempB.getRight() == true)) {
                        tempA.setLeft(true);
                    } else if (tempB.getLeft() == true) {
                        tempA.setRight(true);
                    }
                } else {
                    left = myRandom.nextBoolean();
                    tempA.setLeft(left);
                }
            } else {
                boolean chooseRL = myRandom.nextBoolean();
                if (chooseRL) {
                    tempA.setLeft(true);
                } else {
                    tempA.setRight(true);
                }
            }*/
        }


        /** removes word from tempMap
         *  gives word random direction and position
         *  then returns the randomized word
         *  Largest: the largest word in tempMap before any removes/changes are made to the first fully populated instance of tempMap
         */
        private Word randomizeWord(WordMap tempMap, WordMap badMap)
        {
            int index = myRandom.nextInt(tempMap.size());

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

            if(check_correctBounds(tempW,tempMap))
            {
                badMap.add(tempW);
                return null;
            }

            return tempW;
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
               if(tempW.checkCollison(tempMap.get(i)))
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

}
