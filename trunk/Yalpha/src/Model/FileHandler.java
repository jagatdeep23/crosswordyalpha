
package Model;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Contains static methods that handle saving and loading from data files.
 * Can load and save both word lists and puzzle arrays
 *
 * @author Team Yalpha, specifically Jordan Hollinger
 * @version 1.0
 */
public class FileHandler
{
    /**
     * Loads a word list from a data file.
     *
     * @param fileName the name of the file to load from
     * @return list of the words from the file
     * @throws IOException if there is an error with the file
     */
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

    /**
     * Saves a word list to a data file.
     *
     * @param fileName the name of the file to load from
     * @param words the list of words to be saved
     * @throws IOException if there is an error with the file
     */
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

    /**
     * Loads both a word list and a puzzel array from a data file.
     *
     * @param fileName the name of the file to load from
     * @return Pair object containing the word list and the puzzle array from the file
     * @throws IOException if there is an error with the file
     */
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

        try
        {
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
        }
        catch (Exception e)
        {
            throw new IOException ("Could not read the puzzle from the file.");
        }

        words = readWordList (fileScan);

        reader.close();

        result = new Pair<WordList, char[][]> (words, puzzleArray);

        return result;
    }



    /**
     * Saves both a word list and a puzzle array to a data file.
     *
     * @param fileName the name of the file to load from
     * @param words the list of words to be saved
     * @param puzzleArray the character array containing the puzzle
     * @throws IOException if there is an error with the file
     */
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
