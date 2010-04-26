
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
        FileWriter writer = createFileWriter(fileName, "word list");
        
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
        FileWriter writer = createFileWriter(fileName, "puzzle");
        
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
    
    public static void exportCrossword(String fileName, char[][] puzzleMatrix, WordList words)
            throws IOException
    {
        // Create an HTML File
        FileWriter writer = createFileWriter(fileName, "HTML");

        try
        {
            // Write Crossword HTML
            writeHTMLCrossword(writer, puzzleMatrix, false);

            // Write word list
            writer.write("</table>\n<h4>Words:</h4>\n");

            for (String word : words)
            {
                writer.write(word + "<br>\n");
            }

            writer.write("</html>\n");
        }
        catch (Exception ex)
        {
            throw new IOException("Could not write the HTML.");
        }

        // Close the file
        writer.close();
    }

    public static void exportCrosswordSolution(String fileName, char[][] puzzleMatrix)
            throws IOException
    {
        // Create an HTML File
        FileWriter writer = createFileWriter(fileName, "HTML");
        
        try
        {

            // Write Crossword HTML
            writeHTMLCrossword(writer, puzzleMatrix, true);

            writer.write("</html>\n");
        
        }
        catch (Exception ex)
        {
            throw new IOException("Could not write the HTML.");
        }

        // Close the file
        writer.close();
    }

    private static void writeHTMLCrossword(FileWriter writer, char[][] puzzleMatrix, boolean isSolution)
            throws IOException
    {
        // write HTML Header
        String titlePart = (isSolution ? "Solution" : "");
        writer.write("<html>\n<head>\n");
        writer.write("<title>Yalpha Generated Crossword " + titlePart + "</title>\n");
        writer.write("<style type=\"text/css\">\n");
        writer.write("table.crossword { ");
        writer.write("border: 0px none;");
        writer.write(" }\ntd.puzzleCell {\n");
        writer.write("  text-align: center;\n");
        writer.write("  width: 30px;\n");
        writer.write("  height: 30px;\n");
        writer.write("}\ntd.borderCellX {\n");
        writer.write("  border: 0px none;\n");
        writer.write("  width: 5px;\n");
        writer.write("}\ntd.borderCellY {\n");
        writer.write("  border: 0px none;\n");
        writer.write("  height: 5px;\n");
        writer.write("}\ntd.open { ");
        writer.write("border: 0px none;");
        writer.write(" }\ntd.closed { ");
        writer.write("border: 1px solid black;");
        writer.write(" }\ntd.BT { ");
        writer.write("border-top: 1px solid black;");
        writer.write(" }\ntd.BL { ");
        writer.write("border-left: 1px solid black;");
        writer.write(" }\ntd.BB { ");
        writer.write("border-bottom: 1px solid black;");
        writer.write(" }\ntd.BR { ");
        writer.write("border-right: 1px solid black;");
        writer.write(" }\n</style>\n<body>\n<h3>Crossword " + titlePart + "</h3>\n");

        // Write Crossword table
        writer.write("<table class=\"crossword\" cellspacing=\"0\" cellpadding=\"5\">\n");

        // Get puzzle dimensions
        int height = puzzleMatrix.length, width = puzzleMatrix[0].length;
        int lastRow = height - 1, lastCol = width - 1;
        
        // top row of border cells
        writer.write("<tr>\n<td class=\"borderCellY\">&nbsp;</td>");
        for (int col = 0; col < width; col++)
        {
            writer.write("<td class=\"borderCellY");
            if (puzzleMatrix[0][col] != '~')
                writer.write(" BB");
            writer.write("\">&nbsp;</td>");
        }
        writer.write("\n</tr>\n");
        
        // puzzle rows
        for (int row = 0; row < puzzleMatrix.length; row++)
        {
            // start row
            writer.write("<tr>\n");

            // left border cell
            writer.write("<td class=\"borderCellX ");
            if (puzzleMatrix[row][0] != '~')
                writer.write(" BR");
            writer.write("\">&nbsp;</td>");

            // puzzle row
            for (int col = 0; col < height; col++)
            {
                // puzzle cell
                char c = puzzleMatrix[row][col];

                if (c == '~')
                {
                    // blank cell
                    writer.write("<td class=\"puzzleCell open");

                    if (col > 0 && puzzleMatrix[row][col - 1] != '~')
                        writer.write(" BL");
                    if (row > 0 && puzzleMatrix[row - 1][col] != '~')
                        writer.write(" BT");
                    if (col < lastCol && puzzleMatrix[row][col + 1] != '~')
                        writer.write(" BR");
                    if (row < lastRow && puzzleMatrix[row + 1][col] != '~')
                        writer.write(" BB");

                    writer.write("\">&nbsp;</td>");
                }
                else
                {
                    // letter cell
                    writer.write("<td class=\"puzzleCell closed\">");

                    if (isSolution)
                        writer.write(c);
                    else
                        writer.write("&nbsp;");

                    writer.write("</td>");
                }
            }

            // right border cell
            writer.write("<td class=\"borderCellX ");
            if (puzzleMatrix[row][lastCol] != '~')
                writer.write(" BL");
            writer.write("\">&nbsp;</td>");

            // end row
            writer.write("\n</tr>\n");
        }

        // bottom row of border cells
        writer.write("<tr>\n<td class=\"borderCellY\">&nbsp;</td>");
        for (int col = 0; col < width; col++)
        {
            writer.write("<td class=\"borderCellY");
            if (puzzleMatrix[lastRow][col] != '~')
                writer.write(" BT");
            writer.write("\">&nbsp;</td>");
        }
        writer.write("\n</tr>\n");

        // end table
        writer.write("</table>\n");
    }

    public static void exportWordSearch(String fileName, char[][] puzzleMatrix, WordList words)
            throws IOException
    {
        // Create an HTML File
        FileWriter writer = createFileWriter(fileName, "HTML");

        // write HTML
        try
        {
            // write HTML Header
            writer.write("<html>\n<head>\n");
            writer.write("<title>Yalpha Generated Word Search</title>\n");
            writer.write("<style type=\"text/css\">\n");
            writer.write("td.wordSearch {\n");
            writer.write("  border: 0px none;\n");
            writer.write("  text-align: center;\n");
            writer.write("  width: 30px;\n");
            writer.write("  height: 30px;\n");
            writer.write("}\ntable.wordSearch {\n");
            writer.write("  border: 2px solid black;\n");
            writer.write("}\n</style>\n<body>\n<h3>Word Search</h3>\n");

            // Write word search table
            writeHTMLWordSearch(writer, puzzleMatrix);

            // Write word list
            writer.write("<h4>Words:</h4>\n");

            for (String word : words)
            {
                writer.write(word + "<br>\n");
            }

            writer.write("</html>\n");
        }
        catch (Exception ex)
        {
            throw new IOException("Could not write the HTML.");
        }

        // Close the file
        writer.close();
    }

    public static void exportWordSearchSolution(String fileName, char[][] puzzleMatrix)
            throws IOException
    {
        // Create an HTML File
        FileWriter writer = createFileWriter(fileName, "HTML");

        // write HTML
        try
        {
            // write HTML Header
            writer.write("<html>\n<head>\n");
            writer.write("<title>Yalpha Generated Word Search</title>\n");
            writer.write("<style type=\"text/css\">\n");
            writer.write("td.wordSearch {\n");
            writer.write("  border: 1px solid black;\n");
            writer.write("  text-align: center;\n");
            writer.write("  width: 30px;\n");
            writer.write("  height: 30px;\n");
            writer.write("}\ntable.wordSearch {\n");
            writer.write("  border: 1px solid black;\n");
            writer.write("}\n</style>\n<body>\n<h3>Word Search</h3>\n");

            // Write word search table
            writeHTMLWordSearch(writer, puzzleMatrix);

            writer.write("</html>\n");
        }
        catch (Exception ex)
        {
            throw new IOException("Could not write the HTML.");
        }

        // Close the file
        writer.close();
    }

    private static void writeHTMLWordSearch(FileWriter writer, char[][] puzzleMatrix)
            throws IOException
    {
        // Get puzzle dimensions
        int height = puzzleMatrix.length, width = puzzleMatrix[0].length;

        // Write word search table
        writer.write("<table class=\"wordSearch\" cellspacing=\"0\" cellpadding=\"5\">\n");

        // Puzzle rows
        for (int row = 0; row < height; row++)
        {
            // start row
            writer.write("<tr>\n");

            // Puzzle cells
            for (int col = 0; col < width; col++)
            {
                writer.write("<td class=\"wordSearch\">");
                char c = puzzleMatrix[row][col];
                if (c == '~')
                {
                    writer.write("&nbsp;");
                }
                else
                {
                    writer.write(c);
                }
                writer.write("</td>");
            }

            // end row
            writer.write("\n</tr>\n");
        }

        // end table
        writer.write("</table>\n");
    }

    private static FileWriter createFileWriter(String fileName, String type)
            throws IOException
    {
        try
        {
            return new FileWriter (new File (fileName));
        }
        catch (Exception e)
        {
            throw new IOException ("Could not create the " + type + " file.");
        }
    }
}
