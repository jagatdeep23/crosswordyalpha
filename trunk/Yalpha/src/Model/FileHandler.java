/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

 */

package Model;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Jordan Hollinger
 */
public class FileHandler
{
    public static WordList loadWordList(String fileName)
            throws IOException
    {
        FileReader reader;
        Scanner fileScan;

        WordList words = new WordList();

        try
        {
            reader = new FileReader (new File (fileName));
            fileScan = new Scanner(reader);
        }
        catch (Exception e)
        {
            throw new IOException ("Could not open the word list file.");
        }
        
        words = readWordList (fileScan);

        reader.close();

        return words;
    }

    public static void saveWordList (String fileName, WordList words)
            throws IOException
    {
        FileWriter writer;

        try
        {
            writer = new FileWriter (new File (fileName));
        }
        catch (Exception e)
        {
            throw new IOException ("Could not create the word list file.");
        }
        
        writeWordList(writer, words);

        writer.close();
    }

    public static Pair<WordList, char[][]> loadPuzzleText (String fileName)
            throws IOException
    {
        FileReader reader;
        Scanner fileScan;

        char [][] puzzleArray;
        WordList words = new WordList();

        Pair<WordList, char[][]> result;

        try
        {
            reader = new FileReader (new File (fileName));
            fileScan = new Scanner(reader);
        }
        catch (Exception e)
        {
            throw new IOException ("Could not open the word list file.");
        }

        //try
        //{
            int h = fileScan.nextInt(), w = fileScan.nextInt();

            fileScan.nextLine();

            puzzleArray = new char[h][w];

            for (int i = 0; i < h; i++)
            {
                String line = fileScan.nextLine();

                for (int j = 0; j < w; j++)
                {
                    puzzleArray[i][j] = line.charAt(j * 2);
                }
            }
        //}
        //catch (Exception e)
        //{
        //    throw new IOException ("Could not read the puzzle from the file.");
        //}

        words = readWordList (fileScan);

        reader.close();

        result = new Pair<WordList, char[][]> (words, puzzleArray);

        return result;
    }
    
    public static void savePuzzleText (String fileName, WordList words, char[][] puzzleArray)
            throws IOException
    {
        FileWriter writer;

        try
        {
            writer = new FileWriter (new File (fileName));
        }
        catch (Exception e)
        {
            throw new IOException ("Could not create the puzzle file.");
        }
        
        try
        {
            int h = puzzleArray.length, w = puzzleArray[0].length;

            System.out.println("" + h + ' ' + w + '\n');

            writer.write(String.valueOf(h) + ' ' + String.valueOf(w) + '\n');

            for (int i = 0; i < puzzleArray.length; i++)
            {
                for (int j = 0; j < puzzleArray[i].length; j++)
                {
                    writer.write(puzzleArray[i][j]);
                    writer.write(' ');
                }
                writer.write('\n');
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not write the puzzle to the file.");
        }

        writeWordList (writer, words);

        writer.close();
    }

    private static WordList readWordList (Scanner fileScan)
            throws IOException
    {
        WordList words = new WordList();

        try
        {
            while (fileScan.hasNext())
            {
                words.add(fileScan.next());
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not read the word list from the file.");
        }

        return words;
    }

    private static void writeWordList (FileWriter writer, WordList words)
            throws IOException
    {
        try
        {
            for (int i = 0; i < words.size(); i++)
            {
                writer.write(words.get(i) + '\n');
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not write the word list to file.");
        }
    }
}
