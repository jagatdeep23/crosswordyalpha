package View;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * A JPanel that can display graphics and is used to represent the puzzle
 * area.
 *
 * @author Team Yalpha, specifically Matt Maze
 * @version 1.0
 */
public class PuzzleAreaGUI extends JPanel {

    char[][] puzzle;
    char[][] solutionMatrix;
    boolean wordsearch;
    boolean solution;

    /**
     * Initializes a new PuzzleAreaGUI.
     */
    public PuzzleAreaGUI() {
        puzzle = new char[0][0];
        solution = false;
        solutionMatrix = new char[0][0];
        wordsearch = true;
    }

    /**
     * The components to be painted including the graphical and text areas of the panel.
     * 
     * @param g     Graphics to draw the graphic components of the panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        g.setColor(Color.BLACK);
        if (puzzle.length > 0) {
            if (wordsearch) {
                paintWordsearch(g);
            } else {
                paintCrossword(g);
            }
        }
    }

    /**
     * Paints the crossword to the panel. Needs a redraw afterwards.
     *
     * @param g     Graphics to draw the graphic components of the panel.
     */
    private void paintCrossword(Graphics g) {
        int xBuffer = super.getWidth() / 2 - puzzle.length * 10;
        int yBuffer = super.getHeight() / 2 - puzzle.length * 10;
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[i].length; ++j) {
                if (puzzle[i][j] == '~') {
                    g.fillRect(j * 20 + xBuffer, i * 20 + yBuffer, 20, 20);
                } else {
                    g.drawRect(j * 20 + xBuffer, i * 20 + yBuffer, 20, 20);
                    if (solution) {
                        g.drawString("" + puzzle[i][j], j * 20 + 5 + xBuffer, i * 20 + 15 + yBuffer);
                    }
                }
            }
        }
    }

    /**
     * Paints the word search to the panel. Needs a redraw afterwards.
     *
     * @param g     Graphics to draw the graphic components of the panel.
     */
    private void paintWordsearch(Graphics g) {
        int xBuffer = super.getWidth() / 2 - puzzle.length * 10;
        int yBuffer = super.getHeight() / 2 - puzzle.length * 10;
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[i].length; ++j) {
                if (solution) {
                    if (solutionMatrix[i][j] != '~') {
                        g.setColor(Color.YELLOW);
                        g.fillRect(j * 20 + xBuffer, i * 20 + yBuffer, 20, 20);
                        g.setColor(Color.BLACK);
                    }
                    g.drawString("" + puzzle[i][j], j * 20 + 5 + xBuffer, i * 20 + 15 + yBuffer);
                } else {
                    g.drawString("" + puzzle[i][j], j * 20 + 5 + xBuffer, i * 20 + 15 + yBuffer);
                }
            }
        }
    }

    /**
     * Clears everything from the panel, and resets the puzzle.
     */
    public void clear() {
        puzzle = new char[0][0];
        solutionMatrix = new char[0][0];
    }

    /**
     *  Set the puzzle matrix to be printed to the panel.
     * @param puzzle    The puzzle matrix.
     */
    public void setPuzzle(char[][] puzzle) {
        this.puzzle = puzzle;
        solution = false;
    }

    /**
     * Sets the type of puzzle that is to be printed.
     * @param isWordsearch  Whether the type is a word search (true) or not.
     */
    public void setPuzzleType(boolean isWordsearch) {
        wordsearch = isWordsearch;
    }

    /**
     * Sets whether to show the solution of the puzzle or not.
     * @param isSolution    Whether the solution should be shown (true) or not.
     */
    public void setSolution(boolean isSolution) {
        solution = isSolution;
    }

    /**
     * Sets the solution matrix to be printed to panel if solution is active.
     * @param solution  The solution matrix.
     */
    public void setSolutionMatrix(char[][] solution) {
        solutionMatrix = solution;
    }
}
