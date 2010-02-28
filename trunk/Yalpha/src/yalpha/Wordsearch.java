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
                
                if( c > -1)// && !correctWord(w,c,temp))
                {
                        //System.out.println("Removing: " + (temp.remove(c)).toString());
                        System.out.println("NOT adding: " + w);
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

        //  VERY LABOR INTENSIVE... need better function..
        //  Corrects the word's Position so it no longer collides with anything.
        //  If not possible then return false.
        //  tempW collision word, tempI index of collided word in tempMap
        private boolean correctWord(Word tempW, int tempI, final WordMap tempMap)
        {
            if(tempI > -1)
            {
                for(int i =0; i < tempMap.size(); i++)
                {
                    Word mapsWord = tempMap.get(i);
                    for(int j = 0; j < mapsWord.size(); j++)
                    {
                        for(int k = 0; k < tempW.size(); k++)
                        {
                            // if the words share a letter
                            if(tempW.getCharAt(k) == mapsWord.getCharAt(j))
                            {
                                // check direction of map's word to set random word's direction in a direction that is opposing.
                                if(mapsWord.getDown())
                                {
                                    if(mapsWord.getLeft())
                                    {
                                        
                                    }
                                    else if(mapsWord.getRight())
                                    {
                                        
                                    }

                                }
                                else if(mapsWord.getUp())
                                {
                                    if(mapsWord.getLeft())
                                    {

                                    }
                                    else if(mapsWord.getRight())
                                    {

                                    }
                                }
                                else if (mapsWord.getRight())
                                {
                                    if(mapsWord.getUp())
                                    {

                                    }
                                    else if(mapsWord.getDown())
                                    {

                                    }
                                }
                                else if (mapsWord.getLeft())
                                {
                                    if(mapsWord.getUp())
                                    {

                                    }
                                    else if(mapsWord.getDown())
                                    {

                                    }
                                }
                            }
                        }
                    }
                }

                findEmpty(tempW,tempMap);
                
            }

            return true;
        }

        private void findEmpty(Word tempW, WordMap tempMap)
        {
            
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
