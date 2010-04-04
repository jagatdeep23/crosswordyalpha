package View;

import Model.WordMap;
import Model.Word;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileFilter;

/**
 * Graphically sets and displays buttons and text field for puzzles and word
 * list. Also includes a menu to quit.
 * 
 * @author Team Yalpha, specificially Bob Grube and Matt Maze
 * @version 1.0
 */
public class ViewGUI {

    Font defaultFont = new Font("Courier New", Font.PLAIN, 12);
    ImageIcon aboutImage;
    JButton addButton;
    JButton clearButton;
    JButton exportButton;
    JButton generateButton;
    JButton removeButton;
    JButton solutionButton;
    JComboBox changePuzzle;
    JEditorPane puzzleArea;
    JFrame frame;
    JLabel aboutLabel;
    JMenu menuFile;
    JMenu menuHelp;
    JMenuBar menuBar;
    JMenuItem aboutMenuItem;
    JMenuItem exitMenuItem;
    JMenuItem openPuzzle;
    JMenuItem openWordList;
    JMenuItem savePuzzle;
    JMenuItem saveWordList;
    JTextArea wordArea;
    JTextField wordBox;

    /**
     * Creates and formats the graphical user interface.
     */
    public ViewGUI() {
        frame = new JFrame("Team Yalpha Word Search Iteration 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setLocation((screenSize.width - 800) / 2,
                (screenSize.height - 600) / 2);

        aboutImage = new ImageIcon("src\\about.gif");

        menuBar = new JMenuBar();

        menuFile = new JMenu("File");
        exitMenuItem = new JMenuItem("Exit");
        openPuzzle = new JMenuItem("Open Puzzle");
        openWordList = new JMenuItem("Open Word List");
        savePuzzle = new JMenuItem("Save Puzzle");
        saveWordList = new JMenuItem("Save Word List");
        menuFile.add(openPuzzle);
        menuFile.add(openWordList);
        menuFile.add(savePuzzle);
        menuFile.add(saveWordList);
        menuFile.add(exitMenuItem);

        menuHelp = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");
        menuHelp.add(aboutMenuItem);

        addButton = new JButton("Add");
        addButton.setLocation(10, 500);
        addButton.setSize(80, 30);

        clearButton = new JButton("Clear");
        clearButton.setLocation(170, 500);
        clearButton.setSize(80, 30);

        exportButton = new JButton("Export");
        exportButton.setLocation(570, 500);
        exportButton.setSize(100, 30);

        generateButton = new JButton("Generate");
        generateButton.setLocation(370, 500);
        generateButton.setSize(100, 30);

        removeButton = new JButton("Remove");
        removeButton.setLocation(90, 500);
        removeButton.setSize(80, 30);

        solutionButton = new JButton("Solution");
        solutionButton.setLocation(470, 500);
        solutionButton.setSize(100, 30);

        changePuzzle = new JComboBox();
        changePuzzle.setLocation(450, 10);
        changePuzzle.setSize(100, 20);
        changePuzzle.addItem("Word Search");
        changePuzzle.addItem("Crossword");

        Border lineBorder = BorderFactory.createLineBorder(new Color(100, 100, 255));
        wordBox = new JTextField("Enter Word");
        wordBox.setLocation(10, 470);
        wordBox.setSize(200, 25);
        wordBox.setBorder(lineBorder);

        wordArea = new JTextArea();
        wordArea.setLocation(10, 10);
        wordArea.setSize(200, 450);
        wordArea.setEditable(false);
        wordArea.setBorder(lineBorder);

        puzzleArea = new JEditorPane();
        puzzleArea.setLocation(250, 40);
        puzzleArea.setSize(500, 450);
        puzzleArea.setEditable(false);
        puzzleArea.setBorder(lineBorder);
        puzzleArea.setFont(defaultFont);

        menuBar.add(menuFile);
        menuBar.add(menuHelp);


        //frame.getContentPane().add(aboutLabel);
        frame.getContentPane().add(puzzleArea);
        frame.getContentPane().add(wordArea);
        frame.getContentPane().add(addButton);
        frame.getContentPane().add(clearButton);
        frame.getContentPane().add(exportButton);
        frame.getContentPane().add(generateButton);
        frame.getContentPane().add(removeButton);
        frame.getContentPane().add(solutionButton);
        frame.getContentPane().add(changePuzzle);
        frame.getContentPane().add(wordBox);
        //frame.add(aboutLabel);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * The following methods add all the ActionListeners necessary for the GUI.
     *
     * @param click- The ActionListener class from the Controller.
     */
    public void addAboutMenuListener(ActionListener click) {
        aboutMenuItem.addActionListener(click);
    }

    public void addAddButtonListener(ActionListener click) {
        addButton.addActionListener(click);
    }

    public void addChangePuzzleListener(ActionListener click) {
        changePuzzle.addActionListener(click);
    }

    public void addClearButtonListener(ActionListener click) {
        clearButton.addActionListener(click);
    }

    public void addExportButtonListener(ActionListener click) {
        exportButton.addActionListener(click);
    }

    public void addExitMenuListener(ActionListener click) {
        exitMenuItem.addActionListener(click);
    }

    public void addGenerateButtonListener(ActionListener click) {
        generateButton.addActionListener(click);
    }

    public void addOpenPuzzleListener(ActionListener click) {
        openPuzzle.addActionListener(click);
    }

    public void addOpenWordListListener(ActionListener click) {
        openWordList.addActionListener(click);
    }

    public void addRemoveButtonListener(ActionListener click) {
        removeButton.addActionListener(click);
    }

    public void addSavePuzzleListener(ActionListener click) {
        savePuzzle.addActionListener(click);
    }

    public void addSaveWordListListener(ActionListener click) {
        saveWordList.addActionListener(click);
    }

    public void addSolutionButtonListener(ActionListener click) {
        solutionButton.addActionListener(click);
    }

    public void addWordBoxListener(ActionListener click) {
        wordBox.addActionListener(click);
    }

    public void addWordBoxMouseListener(MouseListener click) {
        wordBox.addMouseListener(click);
    }

    public void clearPuzzle() {
        puzzleArea.setText("");
    }

    public void createJOptionPane(String Description, String Title, int Symbol) {
        JOptionPane.showMessageDialog(null, Description, Title, Symbol);
    }

    public char[][] createSolution(WordMap map, int numRows, int numCols) {
        Word[] arrayMap = new Word[map.size()];
        map.toArray(arrayMap);

        char[][] puzzle = new char[numRows][numCols];

        for (int word = 0; word <= arrayMap.length; ++word) {
            for (int letter = 0; letter < arrayMap[word].size(); ++letter) {
                puzzle[arrayMap[word].getCharPosX(letter)][arrayMap[word].getCharPosY(letter)] = arrayMap[word].getCharAt(letter);
            }
        }
        return puzzle;
    }

    public void displayAbout() {
        JOptionPane.showMessageDialog(frame, "", "", 0, aboutImage);
    }

    public String getPuzzleType() {
        return (String) changePuzzle.getSelectedItem();
    }

    public String getWord() {
        String word = wordBox.getText();
        wordBox.setText("");
        return word;
    }

    public String fileOpenDialog() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(frame) == fc.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public String fileSaveDialog() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(frame) == fc.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public void printCrossword(char[][] puzzle) {
        String crossword = "";
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[0].length; ++j) {
                if (puzzle[i][j] == '~') {
                    crossword += "   ";
                } else {
                    crossword += "[ ]";
                }
            }
            crossword += "\n";
        }
        puzzleArea.setText(crossword);
    }

    public void printCrosswordSolution(char[][] puzzle) {
        String crossword = "";
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[0].length; ++j) {
                if (puzzle[i][j] == '~') {
                    crossword += "   ";
                } else {
                    crossword += "[" + puzzle[i][j] + "]";
                }
            }
            crossword += "\n";
        }
        puzzleArea.setText(crossword);
    }

    public void printGreeting() {
        JOptionPane.showMessageDialog(null, "Welcome to Team Yalpha's puzzle generator! "
                + "\nEvery word needs to be over 1 and under 20 letters in length.", "Greetings!", 1);
    }

    public void printWordsearch(char[][] puzzle) {
        String puzzleString = "";
        if (puzzle != null && puzzle.length > 1) {
            for (int i = 0; i < puzzle.length; ++i) {
                for (int j = 0; j < puzzle[i].length; ++j) {
                    puzzleString += puzzle[i][j] + "  ";
                }
                puzzleString += "\n";
            }
        }
        puzzleArea.setText(puzzleString);
    }

    public void printWordsearchSolution(char[][] puzzle) {
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[0].length; ++j) {
                if (puzzle[i][j] == ' ') {
                    puzzle[i][j] = '-';
                }
            }
        }
        printWordsearch(puzzle);
    }

    public void refreshWordList() {
    }

    public void selectText() {
        wordBox.selectAll();
    }

    public void updateWordArea(String[] words) {
        String puzzleList = "";
        if (words != null) {
            for (int i = 0; i < words.length; ++i) {
                puzzleList += words[i] + "\n";
            }
        }
        wordArea.setText(puzzleList);
    }
}