/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
import java.util.Random;
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

            randomWords.get(0).setFirstCharPos(0,1);
            //randomWords.get(0).resetPos();
            //randomWords.checkAllForLargest();
            //recursivePuzzleSearch(randomWords, mappedWords);
            randomWords = TEST(randomWords);
            
           // System.out.println("WordSearch-Generate");
           // System.out.println(words);
            populateWordMatrix(randomWords);
        }

        public WordMap TEST(WordMap words)
        {
            System.out.println("TESTING: checkWord");
            //System.out.println();
            WordMap temp = new WordMap();

            int size = words.size();
            for(int i =0; i < size; i++)
            {
                Word w = randomizeWord(words);
                
                int c = checkWord(w,temp);
                
                if( c > -1)
                {
                    if(!correctWord(w,c,words))
                    {
                        System.out.println("Removing: " + (temp.remove(c)).toString());
                    }
                }
                temp.add(w);
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

            for(int i =0; i < userList.size(); i++)
            {
                Word tempW = randomizeWord(userList);
                int index = checkWord(tempW, puzzleList);

                if(correctWord(tempW, index, puzzleList))
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

        //  Corrects the word's Position so it no longer collides with anything.
        //  If not possible then return false.
        //  tempW collision word, tempI index of collided word in tempMap
        private boolean correctWord(final Word tempW, int tempI, final WordMap tempMap)
        {
            if(tempI > -1)
            {
                
            }
            return true;
        }

        //  removes word from list and...
        //  gives word random direction and position
        //  then returns it
        private Word randomizeWord(WordMap tempMap)
        {
            int index = myRandom.nextInt(tempMap.size());

            Word tempW = tempMap.remove(index);

            int rX = 0;
            int rY = 0;

            int largeX = tempMap.getLargestX();
            int largeY = tempMap.getLargestY();
            
            if(largeX > 0)
            {
                rX = myRandom.nextInt(largeX);
            }
            if(largeY > 0)
            {
                rY = myRandom.nextInt(largeY);
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
