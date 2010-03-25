package Model;

/**
 * Generates a crossword puzzle that attempts to elimnate any chance
 * that a word can't be used in the puzzle through an exausting recursive function.
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */
public class Crossword extends Puzzle {
        public void generate(final WordList words)
        {
            WordMap mList = new WordMap(words, 10);
            System.out.println("CROSSWORD - GENERATE");



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

