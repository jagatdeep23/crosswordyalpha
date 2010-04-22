package Model;
import java.util.*;
/**
 * Generates a crossword puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle through an exausting recursive function.
 *
 * @author Team Yalpha, specifically Patrick Martin,Jordan Hollinger
 * @version 1.0
 */
public class Crossword extends Puzzle {
        
        WordMap FinishedList = new WordMap(false);

        @Override
        /**
         * Is the caller method to rec(A,B)
         *
         * Initalizes the values in order to call rec properly
         *
         * @param wordlist is the words that are generated into puzzle
         */
        public void generate(final WordList words)
        {
            FinishedList.clear();
            WordMap mList = new WordMap(words, 10,false);
            WordMap SolvingPuzzle = new WordMap(false);

            int size = mList.size();
            for(; 0 < size; --size)
            {
                Word random = randomizeWord(mList, size); //issues with random direction - need to make it Right/Down
                random.setFirstCharPos(0,0);
                SolvingPuzzle.add(random);
                if(!rec(mList.clone(),SolvingPuzzle))
                {
                    SolvingPuzzle.remove(0); // randomly picks word
                }
                else
                {
                    break;
                }
                mList.add(random);
            }

            
            FinishedList.TranslatePositionalStateOfWordToTheConditionOfBeingNotNegative();

            populateWordMatrix(FinishedList);
            //rec(mList,SolvingPuzzle);

        }

        /**
         * Obtains array of 3D points that keep track of...
         *  //X = Index of WordA in Map
         *  //Y = Index of character in WordA
         *  //Z = Index of WordB that matches a character in WordA
         *
         * @param tempWM is a list of words to check VS a word tempW.
         * @return returns list of Point3D where character overlap.
         */
        public ArrayList<Point3D> getIntersection(WordMap tempWM, Word tempW)
        {
            ArrayList<Point3D> pointList = new ArrayList<Point3D>();
            for(int i=0; i < tempWM.size(); i++)
            {
                Word tempP = tempWM.get(i);
                for(int j=0; j < tempP.size(); j++)
                {
                    for(int k =0; k < tempW.size(); k++)
                    {
                        if(tempP.getCharAt(j) == tempW.getCharAt(k))
                        {
                            //X = Index of WordA in Map
                            //Y = Index of character in WordA
                            //Z = Index of WordB that matches a character in WordA
                            pointList.add(new Point3D(i,j,k));
                        }
                    }
                }
            }
            return pointList;

        }

        /**
         * Sets random word to the opposite direction of the word it needs to be overlapped.
         * Then overlaps the character and compares the position of all the other characters in
         * random to see if there is an collision of characters in B that dont match.
         *
         * @return returns true if No TRUE collision happens, False if a true collision
         * @param B is a list of words, random is the word to check against list B, randomSolution is the word in B and it's character the character in random that needs to be overlapped
         */
        public boolean checkSetWordWorks(WordMap B, Word random,Point3D randomSolution)
        {
            Word tempWordB = B.get(randomSolution.getX());
            
            int bX = tempWordB.getCharPosX(randomSolution.getY());
            int bY = tempWordB.getCharPosY(randomSolution.getY());

            random.setDown(!tempWordB.getDown());       //change of direction from UP to DOWN
            random.setRight(!tempWordB.getRight());  //change of direction from UP to DOWN

             if(random.getDown())                     //change of direction from UP TO DOWN
             {
                 bY -= randomSolution.getZ();
             }
             else
             {
                bX -= randomSolution.getZ();
             }

            random.setFirstCharPos(bX, bY);

            if(random.checkCollision(B))
            {
                return false;
            }
            if(checkBuntingWords(random,B))
            {
                return false;
            }

            if(checkParallelWords(random,B))
            {
                return false;
            }
            
            return true;
        }

        public boolean checkParallelWords(Word temp, WordMap tempList)
        {
            //first check if the word overlaps with any words in WordMap if it doesnt then
            for(int i =0; i < tempList.size(); i++)
            {
                //if temp doesnt overlap with the i-th word
                if(temp.overlap(tempList.get(i)))
                {
                   if(temp.getDown() == tempList.get(i).getDown() || temp.getRight() == tempList.get(i).getRight())
                   {
                       return true;
                   }
                }
            }
            return false;
        }

        public boolean checkBuntingWords(Word temp, WordMap tempList)
        {
            //first check if the word overlaps with any words in WordMap if it doesnt then
            for(int i =0; i < tempList.size(); i++)
            {
                //if temp doesnt overlap with the i-th word
                if(!temp.overlap(tempList.get(i)))
                {
                    if(!(temp.getSmallestX() > 1+tempList.get(i).getLargestX() || temp.getLargestX() +1 < tempList.get(i).getSmallestX()))
                    {
                       if(!(temp.getSmallestY() > 1+tempList.get(i).getLargestY() || temp.getLargestY() +1 < tempList.get(i).getSmallestY()))
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /*private boolean checkBuntingWords(Word hWord, WordMap hMap)
        {
            if()
            return true;
        }*/

        /**
         * First records the best crossword that is possible
         * If users List size is zero then all the words have been used.
         * Go through all the Words left in user list and randomly remove one at a time
         * For each word removed find all the intersections(overlaping letters)
         *  while still having the removed word go through each of all the intersections and check for collisions with the rest of the words in the final puzzle list
         *  for each intersection that works update the removed word's position
         *      add the removed word to final puzzle list
         *      Then check if it is in the bounding box of the puzzle
         *          call rec(A,B)...
         *      If not inside of bounding box or previous recursion returned false
         *      remove the random word from THIS recursive function call and append it to the end of users list size
         *  
         *  
         * 
         *
         * @param A is users list , B is the best case
         * @return returns true if the words overlap and dont collide incorectly
         */
        private boolean rec(WordMap A, WordMap B)
        {

        //Obtain the best possible list of words. So even if not all words are used we
        if(FinishedList.size() < B.size())
        {
            FinishedList = B.clone();
        }

       	int Size = A.size();
        if(Size <= 0)
       	{
            return true;
        }else{

                for( ; Size > 0; Size--)
                {

                        Word random = randomizeWord(A,Size); // randomly picks word


                        ArrayList<Point3D> possibleIntersection = getIntersection(B, random);
                        int PIASize = possibleIntersection.size();
                        
                    for(; PIASize > 0; PIASize--)
                    {
                        Point3D randomSolution = randomizeAnswers(possibleIntersection,PIASize);

                        //int choosenAnswer = checkSetWordWorks(B,A[random],possibleIntersectionAnswers[j]); // collision && boundBox
                        //choosenAnswer is the word in B

                        //checkSetWordWorks alters the position of random once a possible intersection is found
                       	if(checkSetWordWorks(B,random,randomSolution))
                        {
                               // string tempS = A[random];
                               // A[random] = A.back();
                               // A.pop_back();
                                //Maninpulate tempS's position (which should be cell/word)
                                B.add(random);
                                if(checkCrosswordBound(B) && rec(A.clone(),B)) //tempAdd - adds List A and List B returns some List C
                                {
                                        return true;
                                }else{
                                        B.remove(B.size()-1);

                                }
                        }

                    }
                    //Adding it to the back, but dont pay attention to it once added
                    A.add(random);
            }
            return false;
            }
        }

        /**
         *
         * Finds the top of the crossword and the bottom of the crossword and
         * makes sure that the length isnt greater then the bound of the bounding box
         *
         * @param temp
         * @return TRUE for if the crossword fits inside the bounds
         */
        boolean checkCrosswordBound(WordMap temp)
        {
            int LargestX = temp.getLargestX();
            int SmallestX = temp.getSmallestX();
            int LargestY = temp.getLargestY();
            int SmallestY = temp.getSmallestY();
            int bound = temp.getBound();

            if((LargestX - SmallestX) < bound && (LargestY - SmallestY) < bound)
            {
                
                return true;
            }

            return false;
        }

        /**
         *
         * randomly removes a word from tempMap
         *
         * @param tempMap word list to randomly choose from
         * @param end randomize from zero to end-1
         * @return
         */
        @Override
         protected Word randomizeWord(WordMap tempMap, int end)
        {
            int index = myRandom.nextInt(end);

            Word tempW = tempMap.remove(index);

            //int rX = myRandom.nextInt(tempMap.getBound());
            //int rY = myRandom.nextInt(tempMap.getBound());

            //tempW.setFirstCharPos(rX, rY);

            //int rLR = myRandom.nextInt(3);
            //int rUD = myRandom.nextInt(3);
/*
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
            }*/

            return tempW;
        }

        /**
         * randomly picks a Point3D from o to end-1 and returns it.
         *
         * @param tempALP a list of all possible answers
         * @param end random 0 to end-1
         * @return
         */
        private Point3D randomizeAnswers(ArrayList<Point3D> tempALP, int end)
        {
            int tempi = myRandom.nextInt(end);
            return tempALP.remove(tempi);
        }

        /**
         *
         *
         * @return the same answer as getMatrixSolution in puzzle...
         */
        public char [][] getMatrixRandomize()
        {
            return super.getMatrixSolution();
        }
}





/*class CrossWord
{

public boolean rec(stringList & A, stringList & B)
{

        if(globalList.size() < B.size())
        {
            globalList.set(B);
        }
        
       	int Size = A.size();
        if(Size <= 0)
       	{
            return true;
        }else{

                for( ; size > 0; size--)
                {
                    
                        word random = randomize(0, Size,A); // randomly picks word
                        
                        int [] possibleIntersectionAnswer = getAnswers(B, random);
                        int PIASize = possibleIntersectionAnswer.size();
                        
                    for(int j =0; j < possibleIntersectionAnswer.size(); j++)
                    {
                        int randomSolution = randomize(0,PIASize);

                        //int choosenAnswer = checkSetWordWorks(B,A[random],possibleIntersectionAnswers[j]); // collision && boundBox
                        //choosenAnswer is the word in B 
                       	if(checkSetWordWorks(B,random,possibleIntersectionAnswers[j]))
                        {
                               // string tempS = A[random];
                               // A[random] = A.back();
                               // A.pop_back();
                                //Maninpulate tempS's position (which should be cell/word)
                                B.push_back(random);
                                if(rec(A,B)) //tempAdd - adds List A and List B returns some List C
                                {
                                        return true;
                                }else{
                                        B.pop_back();

                                }
                        }

                    }
                    A.push_back(random);
        }
	return false;
}


}*/

