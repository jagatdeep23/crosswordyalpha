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
            
           // System.out.println("WordSearch-Generate");
           // System.out.println(words);
            populateWordMatrix(mappedWords);
        }

        public WordMap TEST(WordMap words)
        {
            //System.out.println();
            WordMap temp = new WordMap();

            //  lrg is obtained outside the loop because the longest word would change everytime a word was removed from "words"
            //  So lrg is constant through the whole loop
            int lrg = words.getLongestWord();

            int size = words.size();
            for(int i =0; i < size; i++)
            {
                Word w = randomizeWord(words,lrg);
                
                int c = checkWord(w,temp);
                
                if( c > -1 && !correctWordRandomly(w,temp) && !correctBackup(w,c,temp))
                {
                        System.out.println("NOT adding: " + w);
                }
                else
                {
                    temp.add(w);
                }
            }
            
            return temp;
        }

        /**  VERY LABOR INTENSIVE...
         *  1)  BASICLY goes through all the characters in tempW and all the characters in all the words in tempMap
         *      and checks for a character in common.
         *  2)  Once a common letter is found between tempW and a word from tempMap the two words are overlapped
         *  3)  Then tempW is given a random direction that opposes the word in tempMap
         *  4)  If not possible then return false.
         * 
         *  tempW collision word, tempI index of collided word in tempMap
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
                                if(checkWord(tempW,tempMap) <= -1)
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
                                if(checkWord(tempW,tempMap) <= -1)
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
        private boolean correctBackup(Word tempW, int index, WordMap tempMap)
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
            boolean [][] boolMap = new boolean[tempMap.getLargestY()+1][tempMap.getLargestX()+1];

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
                    boolMap[tempW.getCharPosY(j)][tempW.getCharPosX(j)] = false;
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
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceLR = new ArrayList<ArrayList<ArrayList<point>>>(); //Left-Right
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceUD = new ArrayList<ArrayList<ArrayList<point>>>(); //Up-Down
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceDR = new ArrayList<ArrayList<ArrayList<point>>>(); //Down-Right
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceDL = new ArrayList<ArrayList<ArrayList<point>>>(); //Down-Left
            
            ///SEARCH FOR FREE LEFT RIGHT BLOCKS
            int countX =0;
            int countY =0;
            freeSpaceLR.add(new ArrayList<ArrayList<point>>());
            freeSpaceLR.get(countY).add(new ArrayList<point>());
            for(int i=0; i<tempBM.length; i++)
            {
                
                for(int j =0; j <tempBM[i].length; j++)
                {
                    if(tempBM[i][j] )
                    {
                        freeSpaceLR.get(countY).get(countX).add(new point(j,i));
                    }
                    else
                    {
                        freeSpaceLR.get(countY).add(new ArrayList<point>());
                        countX++;
                        
                    }
                }
                freeSpaceLR.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceLR.get(countY).add(new ArrayList<point>());
                countX =0;
            }

            ///SEARCH FOR FREE UP DOWN BLOCKS
            countX=0;
            countY=0;
            freeSpaceUD.add(new ArrayList<ArrayList<point>>());
            freeSpaceUD.get(countY).add(new ArrayList<point>());
            for(int i=0; i<tempBM.length; i++)
            {
                for(int j =0; j <tempBM[i].length; j++)
                {
                    if(tempBM[i][j] )
                    {
                        freeSpaceUD.get(countY).get(countX).add(new point(j,i));
                    }
                    else
                    {
                        freeSpaceUD.get(countY).add(new ArrayList<point>());
                        countX++;

                    }
                }
                freeSpaceUD.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceUD.get(countY).add(new ArrayList<point>());
                countX =0;
            }

            ////SEARCHES FOR FREE DOWN/LEFT
            int row =0;
            countY =0;
            countX =0;
            freeSpaceDL.add(new ArrayList<ArrayList<point>>());
            freeSpaceDL.get(countY).add(new ArrayList<point>());
            
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
                        freeSpaceDL.get(countY).get(countX).add(new point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<point>());
                        countX++;
                    }
                    tX -=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<point>());
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
                        freeSpaceDL.get(countY).get(countX).add(new point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<point>());
                        countX++;
                    }
                    tX -=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<point>());
                countX =0;
                column++;
            }


            //SEARCH FOR FREE BLOCKS OF DOWN/RIGHT
            row =0;
            countY =0;
            countX =0;
            freeSpaceDL.add(new ArrayList<ArrayList<point>>());
            freeSpaceDL.get(countY).add(new ArrayList<point>());

            while(row < tempBM[0].length-1)
            {
                // x-=1;
                // y+=1;
                int tX = row;
                int tY = 0;
                while(tX < tempBM[0].length && tY < tempBM.length)
                {
                    if(tempBM[tY][tX])
                    {
                        freeSpaceDL.get(countY).get(countX).add(new point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<point>());
                        countX++;
                    }
                    tX +=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<point>());
                countX =0;
                row++;
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
                        freeSpaceDL.get(countY).get(countX).add(new point(tX,tY));
                    }
                    else
                    {
                        freeSpaceDL.get(countY).add(new ArrayList<point>());
                        countX++;
                    }
                    tX +=1;
                    tY +=1;
                }
                freeSpaceDL.add(new ArrayList<ArrayList<point>>());
                countY++;
                freeSpaceDL.get(countY).add(new ArrayList<point>());
                countX =0;
                column++;
            }

            



            //  SETS WORD IN UP/DOWN or LEFT/RIGHT or DOWN/RIGHT or DOWN/LEFT free blocks (chooses L/R or U/D randomly)
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
                            for(int k =0; k <freeSpaceLR.get(i).get(j).size(); k++)
                            {
                                int tX = freeSpaceLR.get(i).get(j).get(k).getX();
                                int tY = freeSpaceLR.get(i).get(j).get(k).getY();

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
                for(int i =0; i<freeSpaceLR.size(); i++)
                {
                    for(int j=0; j<freeSpaceLR.get(i).size(); j++)
                    {
                        if(freeSpaceLR.get(i).get(j).size() >= tempW.size())
                        {
                            for(int k =0; k <freeSpaceLR.get(i).get(j).size(); k++)
                            {
                                int tX = freeSpaceLR.get(i).get(j).get(k).getX();
                                int tY = freeSpaceLR.get(i).get(j).get(k).getY();

                                tempW.setRight(false);
                                tempW.setLeft(false);
                                tempW.setDown(true);
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
                            for(int k =0; k <freeSpaceDR.get(i).get(j).size(); k++)
                            {
                                int tX = freeSpaceDR.get(i).get(j).get(k).getX();
                                int tY = freeSpaceDR.get(i).get(j).get(k).getY();

                                tempW.setRight(true);
                                tempW.setDown(true);
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
                            for(int k =0; k <freeSpaceDL.get(i).get(j).size(); k++)
                            {
                                int tX = freeSpaceDL.get(i).get(j).get(k).getX();
                                int tY = freeSpaceDL.get(i).get(j).get(k).getY();

                                tempW.setLeft(true);
                                tempW.setDown(true);
                                tempW.setFirstCharPos(tX, tY);
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
            }
        }


        /** removes word from tempMap
         *  gives word random direction and position
         *  then returns the randomized word
         *  Largest: the largest word in tempMap before any removes/changes are made to the first fully populated instance of tempMap
         */
        private Word randomizeWord(WordMap tempMap, int Largest)
        {
            int index = myRandom.nextInt(tempMap.size());

            Word tempW = tempMap.remove(index);

            int rX = 0;
            int rY = 0;

            if(Largest > 0)
            {
                rX = myRandom.nextInt(Largest*2);
                rY = myRandom.nextInt(Largest*2);
            }

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

        /** Check for:
         *  overlap/collision
         *  returns the index of the word in puzzleList that "tempW" collided with
         *  returns -1 for no collision
         */
        public int checkWord(final Word tempW, final WordMap tempMap)
        {
            for(int i=0; i < tempMap.size(); i++)
            {
               if(tempW.checkCollison(tempMap.get(i)))
               {
                   return i;
               }
            }
            
            return -1;
        }

}
