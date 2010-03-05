package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class ViewGUI {

    JButton addButton;
    JButton clearButton;
    JButton generateButton;
    JFrame frame;
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem exitMenuItem;
    JTextField wordBox;
    JTextArea wordArea;
    JTextArea puzzleArea;

    public ViewGUI() {
        frame = new JFrame("Team Yalpha Word Search Iteration 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setLocation((screenSize.width - 800) / 2,
                (screenSize.height - 600) / 2);

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);

        addButton = new JButton("Add");
        addButton.setLocation(10, 500);
        addButton.setSize(100, 30);

        clearButton = new JButton("Clear List");
        clearButton.setLocation(110, 500);
        clearButton.setSize(100, 30);

        generateButton = new JButton("Generate");
        generateButton.setLocation(470, 470);
        generateButton.setSize(100, 30);

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
        puzzleArea.setLocation(250, 10);
        puzzleArea.setSize(500, 450);
        puzzleArea.setEditable(false);
        puzzleArea.setBorder(lineBorder);
        puzzleArea.setFont(new Font("Courier New", Font.PLAIN, 12));

        menuBar.add(menu);

        frame.getContentPane().add(wordBox);
        frame.getContentPane().add(wordArea);
        frame.getContentPane().add(addButton);
        frame.getContentPane().add(clearButton);
        frame.getContentPane().add(puzzleArea);
        frame.getContentPane().add(generateButton);
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

    public void addExitMenuListener(ActionListener click) {
        exitMenuItem.addActionListener(click);
    }

    public void addGenerateButtonListener(ActionListener click) {
        generateButton.addActionListener(click);
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

    public void printPuzzle(char[][] puzzle) {
        String puzzleString = "";
        if (puzzle != null) {
            for (int i = 0; i < puzzle.length; ++i) {
                for (int j = 0; j < puzzle[i].length; ++j) {
                    puzzleString += puzzle[i][j] + "  ";
                }
                puzzleString += "\n";
            }
        }
        puzzleArea.setText(puzzleString);
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
