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

        WordMap FinishedList = new WordMap();
        
        public void generate(final WordList words)
        {
            WordMap mList = new WordMap(words, 10);
            WordMap SolvingPuzzle = new WordMap();

            int size = mList.size();
            for(; 0 < size; --size)
            {
                Word random = randomize(0, size, mList);
                random.setFirstCharPos(0,0);
                SolvingPuzzle.add(random);
                if(!rec(mList,SolvingPuzzle))
                {
                    SolvingPuzzle.remove(0); // randomly picks word
                }
                else
                {
                    return;
                }
                mList.add(random);
            }

            if(!rec(mList,SolvingPuzzle))
            {
                
            }


            rec(mList,SolvingPuzzle);

            System.out.println("CROSSWORD - GENERATE");
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

                        Word random = randomize(0, Size, A); // randomly picks word


                        ArrayList<Point3D>  possibleIntersectionAnswer = getAnswers(B, random);
                        int PIASize = possibleIntersectionAnswer.size();

                    for(; PIASize > 0; PIASize--)
                    {
                        Point3D randomSolution = randomize(0,PIASize, possibleIntersectionAnswer);

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
                                if(rec(A.clone(),B)) //tempAdd - adds List A and List B returns some List C
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

