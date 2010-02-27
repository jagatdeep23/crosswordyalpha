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
            for(int i = 0; i < words.size(); i++)
            {
                Word w = words.get(i);
                
                int c = checkWord(w,temp);
                
                if( c > -1)
                {
                    System.out.println("Removing: " + (temp.remove(c)).toString());
                }
                temp.add(words.get(i));
            }
            
            System.out.println("checkWord PASSED");
            return temp;
        }

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

                if(CorrectWord(tempW, index, puzzleList))
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
        private boolean CorrectWord(final Word tempW, int tempI, final WordMap tempMap)
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
            return tempMap.get(0);
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
