package Model;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Contains static methods that handle saving and loading from data files.
 * Can load and save both word lists and puzzle arrays
 *
 * @author Team Yalpha, specifically Jordan Hollinger
 * @version 1.0
 */
public class FileHandler
{
    public static ArrayList<String> loadProfanityList()
    {
        ArrayList<String> profanityList = new ArrayList<String>();
        try
        {
            Scanner fileScan = FileHandler.createFileScanner("proFill.txt", "profanity list");
            while (fileScan.hasNext())
            {
                profanityList.add(fileScan.next());
            }
            fileScan.close();
        }
        catch (Exception e)
        {
            
        }
        
        return profanityList;
    }
    
    /**
     * Loads a word list from a data file.
     *
     * @param fileName the path of the file to load from
     * @return list of the words from the file
     * @throws IOException error opening or reading from the file
     */
    public static WordList loadWordList(String fileName)
            throws IOException
    {
        Scanner fileScan = createFileScanner(fileName, "word list");

        WordList words = new WordList();
        
        words = readWordList (fileScan);

        fileScan.close();

        return words;
    }

    /**
     * Saves a word list to a data file.
     *
     * @param fileName the path of the file to save to
     * @param words the list of words to be saved
     * @throws IOException error creating or writing to the file
     */
    public static void saveWordList (String fileName, WordList words)
            throws IOException
    {
        FileWriter writer = createFileWriter(fileName, "word list");
        
        writeWordList(writer, words);

        writer.close();
    }

    /**
     * Loads a puzzle from a data file.
     * @param fileName the path of the file to load from
     * @return the puzzle loaded from the file
     * @throws IOException error opening or reading from the file
     */
    public static Puzzle loadPuzzleText (String fileName)
            throws IOException
    {
        Scanner fileScan = createFileScanner(fileName, "word list");

        char [][] puzzleArray;
        Model.PuzzleType type;
        WordList words = new WordList();

        Puzzle resultPuzzle;

        try
        {
            // Read type
            String typeStr = fileScan.next();

            if (typeStr.equals("CROSSWORD"))
            {
                resultPuzzle = new Crossword();
            }
            else
            {
                resultPuzzle = new Wordsearch();
            }

            // Read puzzle matrix size
            int size = fileScan.nextInt();
            fileScan.nextLine();

            // Read puzzle matrix
            puzzleArray = readPuzzleMatrix(fileScan, size);
            resultPuzzle.populateWordMatrix(puzzleArray);

            // For a word search, read solution matrix
            if (resultPuzzle.getPuzzleType() == Model.PuzzleType.WORDSEARCH)
            {
                char[][] solutionArray = readPuzzleMatrix(fileScan, size);
                resultPuzzle.populateSolutionMatrix(solutionArray);
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not read the puzzle from the file.");
        }

        words = readWordList (fileScan);
        resultPuzzle.setWordsUsed(new WordMap(words));

        fileScan.close();

        return resultPuzzle;
    }

    /**
     * Saves a puzzle to a data file to a data file.
     *
     * @param fileName the path of the file to save to
     * @param puzzle the puzzle to write to the file
     * @throws IOException error creating or writing to the file
     */
    public static void savePuzzleText (String fileName, Puzzle puzzle)
            throws IOException
    {
        FileWriter writer = createFileWriter(fileName, "puzzle");

        try
        {
            // Write Puzzle Type
            if (puzzle.getPuzzleType() == Model.PuzzleType.CROSSWORD)
            {
                writer.write("CROSSWORD\n");
            }
            else
            {
                writer.write("WORDSEARCH\n");
            }

            char[][] puzzleArray = puzzle.getMatrixRandomize();

            // Write Puzzle Size
            writer.write(String.valueOf(puzzleArray.length) + '\n');

            // Write Puzzle Matrix
            writePuzzleMatrix(writer, puzzleArray);

            if (puzzle.getPuzzleType() == Model.PuzzleType.WORDSEARCH)
            {
                char [][] solutionArray = puzzle.getMatrixSolution();

                writePuzzleMatrix(writer, solutionArray);
            }
        }
        catch (Exception e)
        {
            throw new IOException ("Could not write the puzzle to the file.");
        }

        writeWordList (writer, puzzle.getWordsUsed().toWordList());

        writer.close();
    }

    /**
     * Exports a crossword in HTML to a specified file.
     *
     * @param fileName the destination file
     * @param puzzleMatrix the matrix containing the puzzle
     * @param words the list of words in the puzzle
     * @throws IOException error writing to file
     */
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

    /**
     * Exports a crossword solution in HTML to a specified file.
     *
     * @param fileName the destination file
     * @param puzzleMatrix the matrix containing the puzzle solution
     * @throws IOException error writing to file
     */
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

    /**
     * Exports a word search in HTML to a specified file.
     *
     * @param fileName the destination file
     * @param puzzleMatrix the matrix containing the puzzle
     * @param words the list of words in the puzzle
     * @throws IOException error writing to file
     */
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

    /**
     * Exports a word search solution in HTML to a specified file.
     *
     * @param fileName the destination file
     * @param puzzleMatrix the matrix containing the puzzle solution
     * @throws IOException error writing to file
     */
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
            writer.write("<title>Yalpha Generated Word Search Solution</title>\n");
            writer.write("<style type=\"text/css\">\n");
            writer.write("td.wordSearch {\n");
            writer.write("  border: 1px solid black;\n");
            writer.write("  text-align: center;\n");
            writer.write("  width: 30px;\n");
            writer.write("  height: 30px;\n");
            writer.write("}\ntable.wordSearch {\n");
            writer.write("  border: 1px solid black;\n");
            writer.write("}\n</style>\n<body>\n<h3>Word Search Solution</h3>\n");

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

    /**
     * Reads a word list from the specified scanner.
     *
     * @param fileScan scanner for the file
     * @return the read WordList
     * @throws IOException error reading from scanner
     */
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
    
    /**
     * Writes a word list to the given file writer.
     *
     * @param writer writer for the destination file
     * @param words the word list to be written
     * @throws IOException error writing to file
     */
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

    /**
     * Reads a puzzle matrix from a given file scanner
     * @param fileScan scanner for the data file
     * @param size dimensions of the puzzle (always square)
     * @return the character array representing the puzzle
     * @throws IOException error reading from the file
     */
    private static char[][] readPuzzleMatrix(Scanner fileScan, int size)
            throws IOException
    {
        char[][] puzzleArray = new char[size][size];

        for (int i = 0; i < size; i++)
        {
            String line = fileScan.nextLine();

            for (int j = 0; j < size; j++)
            {
                puzzleArray[i][j] = line.charAt(j * 2);
            }
        }

        return puzzleArray;
    }

    /**
     * Writes a puzzle matrix into a given file writer
     * @param writer writer for the data file
     * @param puzzleArray character array representing the puzzle
     * @throws IOException error writing to the file
     */
    private static void writePuzzleMatrix(FileWriter writer, char[][] puzzleArray)
            throws IOException
    {
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

    /**
     * Writes HTML for a crossword puzzle into a given file writer
     * @param writer the writer for the destination file
     * @param puzzleMatrix the matrix containing the puzzle
     * @param isSolution specifies if output should include the words
     * @throws IOException error writing to the file
     */
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

    /**
     * Writes the HTML for a word search into given file writer
     * @param writer the writer for the destination file
     * @param puzzleMatrix matrix containing the puzzle data
     * @throws IOException error writing to the file
     */
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

    /**
     * Creates a Scanner for a specified file
     * @param fileName the path of the file
     * @param type name of the type of file, used for error message in exceptions
     * @return the Scanner for the given file
     * @throws IOException error creating Scanner
     */
    private static Scanner createFileScanner(String fileName, String type)
            throws IOException
    {
        try
        {
            FileReader reader = new FileReader (new File (fileName));
            return new Scanner(reader);
        }
        catch (Exception e)
        {
            throw new IOException ("Could not open the " + type + " file.");
        }
    }

    /**
     * Creates a writer for the specified file
     * @param fileName the path of the file
     * @param type name of the type of file, used for error message in exceptions
     * @return the FileWriter for the given file
     * @throws IOException error creating FileWriter
     */
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
