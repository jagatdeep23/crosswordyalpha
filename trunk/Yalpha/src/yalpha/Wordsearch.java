/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
import java.util.Random;
import java.util.*;
/**
 *
 * @author Patrick
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
            System.out.println("TESTING: checkWord");
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
                
                if( c > -1 && !correctWordRandomly(w,c,temp))
                {
                    if(correctBackup(w,c,temp))
                    {
                        //System.out.println("Removing: " + (temp.remove(c)).toString());
                        System.out.println("NOT adding: " + w);
                    }
                }
                else
                {
                    temp.add(w);
                }
            }
            
            System.out.println("checkWord PASSED");
            return temp;
        }

        // Recursivly solves the puzzle - like n-Queens
        public boolean recursivePuzzleSearch(WordMap userList, WordMap puzzleList)
        {
            WordMap bad = new WordMap();
            if(userList.isEmpty())
            {
                return true;
            }

            int Lrg = userList.getLongestWord();

            for(int i =0; i < userList.size(); i++)
            {
                Word tempW = randomizeWord(userList, Lrg);
                int index = checkWord(tempW, puzzleList);

                if(correctWordRandomly(tempW, index, puzzleList))
                {
                    puzzleList.add(tempW);
                    if(recursivePuzzleSearch(combineMaps(userList,bad), puzzleList))
                    {
                        return true;
                    }
                }
                else
                {
                    bad.add(tempW);
                }
            }
            return false;
        }

        private WordMap combineMaps(WordMap tempUser, WordMap tempbad)
        {
            tempUser.addAll(tempbad);
            return tempUser;
        }

        //  VERY LABOR INTENSIVE... need better function..
        //  Corrects the word's Position so it no longer collides with anything.
        //  If not possible then return false.
        //  tempW collision word, tempI index of collided word in tempMap
        private boolean correctWordRandomly(Word tempW, int tempI, final WordMap tempMap)
        {
            boolean found = false;
        
                for(int i =0; i < tempMap.size() && !found; i++)
                {
                    Word mapsWord = tempMap.get(i);
                    for(int j = 0; j < mapsWord.size() && !found; j++)
                    {
                        for(int k = 0; k < tempW.size() && !found; k++)
                        {
                            // if the words share a letter
                            if(tempW.getCharAt(k) == mapsWord.getCharAt(j))
                            {
                                found = true;
                                correctByRandomDirection(tempW,mapsWord);
                            }
                        }
                    }
                }
                
                if(checkWord(tempW,tempMap) > -1)
                {
                   return false;
                }

            return true;
        }

        private boolean correctBackup(Word tempW, int index, WordMap tempMap)
        {
            boolean [][] space = findEmptySpace(tempMap);

            if(correctByMove(tempW,index,tempMap))
            {
             
            }
            else if(correctByEmptySpace(tempW,space))
            {
                return false;
            }

            return true;
            
        }

        private boolean [][] findEmptySpace(final WordMap tempMap)
        {
            boolean [][] boolMap = new boolean[tempMap.getLargestY()+1][tempMap.getLargestX()+1];

            for(int i=0; i< boolMap.length; i++)
            {
                for(int j=0; j<boolMap[i].length; j++)
                {
                    boolMap[i][j] = true;
                }
            }
            
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
        private boolean correctByEmptySpace(Word tempW, final boolean tempBM [][])
        {
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceLR = new ArrayList<ArrayList<ArrayList<point>>>(); //Left-Right
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceUD = new ArrayList<ArrayList<ArrayList<point>>>(); //Up-Down
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceDR = new ArrayList<ArrayList<ArrayList<point>>>(); //Down-Right
            ArrayList<ArrayList<ArrayList<point>>> freeSpaceDL = new ArrayList<ArrayList<ArrayList<point>>>(); //Down-Left

            /*freeSpace.add(new ArrayList<point>());  //Down/Up     0   share x's
            freeSpace.add(new ArrayList<point>());  //Left/right    1   share y's
            freeSpace.add(new ArrayList<point>());  //Down/right    2   one -x/-y away
            freeSpace.add(new ArrayList<point>());  //Down/left     3   one +x/+y away*/
            
            ///SEARCH FOR FREE LEFT RIGHT SPOTS
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

            ///SEARCH FREE UP DOWN SPOTS
            /*countX=0;
            countY=0;
            
            for(int i=0; i<tempBM.length; i++)
            {
                for(int j =0; j <tempBM[i].length; j++)
                {
                    if(tempBM[j][i] )
                    {
                        freeSpaceUD.get(countY).get(countX).add(new point(j,i));
                    }
                    else
                    {
                        freeSpaceUD.get(countY).add(new ArrayList<point>());
                        countX++;

                    }
                }
                countY++;
            }*/

            //only sets LEFT RIGHT
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

            return true;
            
        }

        private boolean correctByMove(Word tempW, int index, WordMap tempMap)
        {
            return false;
        }

        private void correctByRandomDirection(Word tempA, Word tempB)
        {
            // check direction of map's word to set random word's direction in a direction that is opposing.
            // WordExample: mapsWord is down to the right then, other word could be: (down,left),(up,right),(-,right),(-,left)
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

        private boolean [][] findEmptySpace(Word tempW, WordMap tempMap)
        {
            //int largestX =
            return null;
        }

        //  removes word from list and...
        //  gives word random direction and position
        //  then returns it
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

            if(rLR == 1)
            {
                tempW.setLeft(true);
            }
            else if(rLR == 2)
            {
                tempW.setRight(true);
            }

            if(rUD == 1)
            {
                tempW.setUp(true);
            }
            else if(rUD == 2)
            {
                tempW.setDown(true);
            }
            
            return tempW;
        }

        //Check for
        //  overlap/collision
        //  returns the index of the word in puzzleList that "tempW" collided with
        //  returns -1 for no collision
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
