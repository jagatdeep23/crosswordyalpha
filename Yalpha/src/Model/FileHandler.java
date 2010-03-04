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
 * @author jordan
 */
public class FileHandler
{
    public static void saveWordList (String fileName, WordList words)
            throws IOException
    {
        FileWriter writer;

        try
        {
            writer = new FileWriter (new File (fileName));
        }
        catch (IOException e)
        {
            throw new IOException ("Could not create the word list file.");
        }
        
        writeWordList(writer, words);

        writer.close();
    }

    public static WordList loadWordList(String fileName)
            throws IOException
    {
        FileReader reader;
        Scanner fileScan;

        WordList wl = new WordList();

        try
        {
            reader = new FileReader (new File (fileName));
            fileScan = new Scanner(reader);
        }
        catch (IOException e)
        {
            throw new IOException ("Could not open the word list file.");
        }
        
        try
        {
            while (fileScan.hasNext())
            {
                wl.add(fileScan.next());
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not read the word list from the file.");
        }
        
        return wl;
    }
    
    public static void savePuzzleText (String fileName, WordList words, char[][] puzzleArray)
    {
        
    }

    private static void readWordList (Scanner fileScan, WordList words)
    {

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
        catch (IOException e)
        {
            throw new IOException ("Could not write the word list to file.");
        }
    }
}
