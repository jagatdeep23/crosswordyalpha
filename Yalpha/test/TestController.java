/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import Model.FileHandler;
import Model.WordList;
import Model.Pair;
import java.io.IOException;

import Model.*;

/**
 *
 * @author jordan
 */
public class TestController
{
    public static void main(String[] args)
            throws IOException
    {
        char array[][] = {{'a', 'b', 'c', 'd'},
                          {'e', 'f', 'g', 'h'},
                          {'i', 'j', 'k', 'l'},
                          {'m', 'n', 'o', 'p'}};

        WordList words = new WordList();
        words.add("apple");
        words.add("banana");
        words.add("carrot");
        words.add("door");
        words.add("epsilon");

        WordList words2 = new WordList();
        words2.add("forest");
        words2.add("gokart");
        words2.add("hammer");
        words2.add("igloo");
        words2.add("kiwi");
        
        FileHandler.saveWordList("wordList.txt", words);
        FileHandler.savePuzzleText("puzzle.txt", words2, array);
        
        WordList loadedWords = FileHandler.loadWordList("wordList.txt");

        printWordList(loadedWords);

        Pair<WordList, char[][]> result = FileHandler.loadPuzzleText("puzzle.txt");

        WordList loadedWords2 = result.getFirst();
        char[][] loadedArray = result.getSecond();

        printWordList(loadedWords2);
        printArray(loadedArray);
        
    }
    
    private static void printWordList(WordList words)
    {
        for (int i = 0; i < words.size(); i++)
        {
            System.out.println(words.get(i));
        }
        System.out.println();
    }

    private static void printArray(char[][] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array[i].length; j++)
            {
                System.out.print(array[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
