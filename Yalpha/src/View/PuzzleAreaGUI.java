/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public class PuzzleAreaGUI extends JPanel {

    char[][] puzzle;
    char[][] solutionMatrix;
    boolean wordsearch;
    boolean solution;

    public PuzzleAreaGUI() {
        puzzle = new char[0][0];
        solution = false;
        solutionMatrix = new char[0][0];
        wordsearch = true;
    }

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

    private void paintCrossword(Graphics g) {
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[i].length; ++j) {
                if (puzzle[i][j] == '~') {
                    g.fillRect(j * 20, i * 20, 20, 20);
                } else {
                    g.drawRect(j * 20, i * 20, 20, 20);
                    if (solution) {
                        g.drawString("" + puzzle[i][j], j * 20 + 5, i * 20 + 15);
                    }
                }
            }
        }
    }

    private void paintWordsearch(Graphics g) {
        for (int i = 0; i < puzzle.length; ++i) {
            for (int j = 0; j < puzzle[i].length; ++j) {
                if (solution) {
                    if (solutionMatrix[i][j] != '~') {
                        g.setColor(Color.YELLOW);
                        g.fillRect(j * 20, i * 20, 20, 20);
                        g.setColor(Color.BLACK);
                    }
                    g.drawString("" + puzzle[i][j], j * 20 + 5, i * 20 + 15);
                } else {
                    g.drawString("" + puzzle[i][j], j * 20 + 5, i * 20 + 15);
                }
            }
        }
    }

    public void clear() {
        puzzle = new char[0][0];
        solutionMatrix = new char[0][0];
        super.repaint();
    }

    public void setPuzzle(char[][] puzzle) {
        this.puzzle = puzzle;
        solution = false;
        super.repaint();
    }

    public void setPuzzleType(boolean isWordsearch) {
        wordsearch = isWordsearch;
    }

    public void setSolution(boolean isSolution) {
        solution = isSolution;
        super.repaint();
    }

    public void setSolutionMatrix(char[][] solution) {
        solutionMatrix = solution;
    }
}
