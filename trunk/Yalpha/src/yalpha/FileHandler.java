/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

 */

package yalpha;

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
    public static void saveWordList (String fileName, WordList wl)
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
        
        try
        {
            for (int i = 0; i < wl.size(); i++)
            {
                writer.write(wl.get(i) + '\n');
            }
        }
        catch (IOException e)
        {
            throw new IOException ("Could not write the word list to file.");
        }
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
    
    public static void savePuzzleText (String fileName)
    {
        
    }
}
