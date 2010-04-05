package Model;
import java.util.*;
/**
 * Generates a crossword puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle through an exausting recursive function.
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */
public class Crossword extends Puzzle {
        
        WordMap FinishedList = new WordMap(false);
        @Override
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
                if(!rec(mList,SolvingPuzzle))
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

            System.out.println("CROSSWORD - GENERATE");
        }

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

            if(random.checkCollison(B.get(randomSolution.getX())))
            {
                return false;
            }

            return true;
            //return OneStepAwayTest();
        }
        
        public boolean rec(WordMap A, WordMap B)
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
                                if(/*checkCrosswordBound(B) && */rec(A.clone(),B)) //tempAdd - adds List A and List B returns some List C
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

        //boolean

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

        private Point3D randomizeAnswers(ArrayList<Point3D> tempALP, int end)
        {
            int tempi = myRandom.nextInt(end);
            return tempALP.remove(tempi);
        }

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

