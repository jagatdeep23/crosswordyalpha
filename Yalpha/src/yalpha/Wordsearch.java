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

            recursivePuzzleSearch(randomWords, mappedWords);
            
            System.out.println("WordSearch-Generate");
            System.out.println(words);
            populateWordMatrix(mappedWords);
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
