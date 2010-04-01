package View;

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

    JButton addButton;
    JButton clearButton;
    JButton exportButton;
    JButton generateButton;
    JButton removeButton;
    JButton solutionButton;
    JComboBox changePuzzle;
    JFrame frame;
    JMenu menuFile;
    JMenu menuHelp;
    JMenuBar menuBar;
    JMenuItem aboutMenuItem;
    JMenuItem exitMenuItem;
    JMenuItem openPuzzle;
    JMenuItem openWordList;
    JMenuItem savePuzzle;
    JMenuItem saveWordList;
    JTextField wordBox;
    //JScrollPane wordArea;
    JTextArea wordArea;
    JTextArea puzzleArea;

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

        puzzleArea = new JTextArea();
        puzzleArea.setLocation(250, 40);
        puzzleArea.setSize(500, 450);
        puzzleArea.setEditable(false);
        puzzleArea.setBorder(lineBorder);
        puzzleArea.setFont(new Font("Courier New", Font.PLAIN, 12));

        menuBar.add(menuFile);
        menuBar.add(menuHelp);

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
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
    }

    public void addAddButtonListener(ActionListener click) {
        addButton.addActionListener(click);
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

    public void addRemoveButtonListener(ActionListener click){
        removeButton.addActionListener(click);
    }

    public void addSavePuzzleListener(ActionListener click) {
        savePuzzle.addActionListener(click);
    }

    public void addSaveWordListListener(ActionListener click) {
        saveWordList.addActionListener(click);
    }

    public void addWordBoxListener(ActionListener click) {
        wordBox.addActionListener(click);
    }

    public void addWordBoxMouseListener(MouseListener click) {
        wordBox.addMouseListener(click);
    }

    public String getWord() {
        String word = wordBox.getText();
        wordBox.setText("");
        return word;
    }

    public String fileOpenDialogue() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(frame);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }

    public String fileSaveDialogue() {
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(frame);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }

    public void printGreeting() {
        JOptionPane.showMessageDialog(null, "Welcome to Team Yalpha's puzzle generator! "
                + "\nEvery word needs to be over 1 and under 20 letters in length.", "Greetings!", 1);
    }

    public void printPuzzle(char[][] puzzle) {
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
