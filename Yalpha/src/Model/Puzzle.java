package Model;

import java.util.Random;
/**
 * An abstract class that has an arcinal of protected classs that help subdue problems
 * that other more general wordPuzzles may have. 
 * 
 * The point class just keeps track of the (x,y) positions
 *
 * The Word class obtains a string and keeps track of each character's (x,y) position through
 * the use of the point class. The word also keeps track of the direction of the word.
 *
 * The WordMap class is an arraylist of words
 *
 * @author Team Yalpha, specifically Patrick Martin
 * @version 1.0
 */
public abstract class Puzzle {


    protected Random myRandom = new Random();

    private char [][] map = null;

    // Function should create puzzle
    public abstract void generate(final WordList words);

    Puzzle()
    {
    }

    public char [][] getMatrix()
    {
         return map;
    }

    //puts all the words into the char matrix(MxM)
    protected void populateWordMatrix(final WordMap tempMap)
    {
        //map = new char [(tempMap.getLargestY() +1) ][(tempMap.getLargestX() +1)];
          map = new char [tempMap.getBound() ][tempMap.getBound()];

        //Random randGen = new Random();
        for(int i=0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++ )
            {
               //map[i][j] = (char) (randGen.nextInt(24) + 97); //function should generate random letters from a(97) to z(122)
               map[i][j] = '~'; //function should generate random letters from a(97) to z(122)
            }
        }

        for(int i=0; i < tempMap.size(); i++)
        {
            Word tempW = tempMap.get(i);
         for(int j = 0; j < tempW.size(); j++ )
            {
                int y = tempW.getCharPosY(j);
                int x = tempW.getCharPosX(j);
                map[y][x] = tempW.getCharAt(j);
            }
        }
 
    }

        public boolean check_correctBounds(Word tempW, WordMap tempMap)
        {
            if(tempW.checkBounds(tempMap.getBound()))
            {
                if(tempW.moveBounds(tempMap.getBound()))
                {
                    return true;
                }
            }
            return false;
        }



    /** removes word from tempMap
         *  gives word random direction and position
         *  then returns the randomized word
         *  Largest: the largest word in tempMap before any removes/changes are made to the first fully populated instance of tempMap
         *  Assuming that user wants to go from "zero" to "end"
         */
        protected Word randomizeWord(WordMap tempMap, int end)
        {
            int index = myRandom.nextInt(end);

            Word tempW = tempMap.remove(index);

            int rX = myRandom.nextInt(tempMap.getBound());
            int rY = myRandom.nextInt(tempMap.getBound());

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

    /*public void printW(Word tempW)
        {
            System.out.println(tempW);

            System.out.println("PosX[0]: " + tempW.getCharPosX(0));
            System.out.println("PosY[0]: " + tempW.getCharPosY(0));

            System.out.println("PosX[LAST]: " + tempW.getCharPosX(tempW.size() -1));
            System.out.println("PosY[LAST]: " + tempW.getCharPosY(tempW.size() -1));

        }*/

    //puts all the words into the char matrix(MxM)
    public void populateWordMatrix(char [][] tempMap)
    {
        map = new char[tempMap.length][tempMap[0].length];
        
        for(int i=0; i < tempMap.length; i++)
        {
            for(int j = 0; j < tempMap[i].length; j++ )
            {
                map[i][j] = tempMap[i][j];
            }
        }

    }
}

