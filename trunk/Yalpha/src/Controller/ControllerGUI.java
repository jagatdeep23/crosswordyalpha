package Controller;

import Model.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import Model.*;
import View.ViewGUI;

/**
 * Interprets user input from the GUI. Creates action listeners for all
 * graphical items. Updates the model's state and data.
 *
 * @author Team Yalpha, specifically Bob Grube and Matt Maze
 * @version 1.0
 */
public class ControllerGUI {

    private ViewGUI view;
    private boolean solutionDisplayed = false;
    private Model model;

    /**
     * Creates a Model and ViewGUI. Also adds all ActionListeners to the ViewGUI.
     */
    public ControllerGUI() {
        //Set the look and feel (for Macs too).
        if (System.getProperty("mrj.version") != null) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        model = new Model();
        view = new ViewGUI();
        view.printGreeting();
        view.addAboutMenuListener(new AboutMenuListener());
        view.addAddButtonListener(new AddButtonListener());
        view.addChangePuzzleListener(new ChangePuzzleListener());
        view.addClearButtonListener(new ClearButtonListener());
        view.addExitMenuListener(new ExitMenuItemListener());
        view.addExportButtonListener(new ExportButtonListener());
        view.addGenerateButtonListener(new GenerateButtonListener());
        view.addHelpMenuItemListener(new HelpMenuItemListener());
        view.addOpenPuzzleListener(new OpenPuzzleListener());
        view.addOpenWordListListener(new OpenWordListListener());
        view.addRemoveButtonListener(new RemoveButtonListener());
        view.addSavePuzzleListener(new SavePuzzleListener());
        view.addSaveWordListListener(new SaveWordListListener());
        view.addSolutionButtonListener(new SolutionButtonListener());
        view.addWordBoxListener(new WordBoxListener());
        view.addWordBoxMouseListener(new WordBoxMouseListener());
    }

    /**
     * The following classes create all the ActionListeners necessary for the
     * GUI.
     */
    private class AboutMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.displayAbout();
        }
    }

    private class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (model.add(view.getWord())) {
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class ChangePuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.clearPuzzle();
            if (view.getPuzzleType().equals("Crossword")) {
                model.choosePuzzle(Model.PuzzleType.CROSSWORD);
            } else {
                model.choosePuzzle(Model.PuzzleType.WORDSEARCH);
            }
        }
    }

    private class ClearButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.removeAll();
            view.updateWordArea(model.getwordList());
        }
    }

    private class ExitMenuItemListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    //I'm tipsy sand writing this code, I hope its' right
    private class ExportButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileSaveDialog();
            if (filePath != null && !filePath.equals("")) {
                try {
                    if (filePath.length() > 5
                            && filePath.substring(filePath.length() - 5, filePath.length()).equals(".html")) {
                        model.export(filePath, solutionDisplayed);
                    } else {
                        model.export(filePath + ".html", solutionDisplayed);
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    private class GenerateButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int puzzleSize = view.getPuzzleSize();
            model.generate(puzzleSize);
            solutionDisplayed = false;
            if (view.getPuzzleType().equals("Crossword")) {
                view.printCrossword(model.getMatrix());
            } else {
                view.printWordsearch(model.getMatrix());
            }
            view.setSolutionMatrix(model.getMatrixSolution());
        }
    }

    private class HelpMenuItemListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.createJOptionPane("Add words by typing words into the \"Enter "
                    + "Word\" area and clicking the add button.\n"
                    + "Generate the puzzle by clicking the generate button.\n"
                    + "Switch puzzle type by clicking the drop down menu and "
                    + "selecting a puzzle type.\n"
                    + "Save/load puzzles/word lists through the file menu.",
                    "Help", 1);
        }
    }

    private class OpenPuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialog();
            if (filePath != null && !filePath.equals("")) {
                try {
                    model.loadPuzzle(filePath);
                    view.updateWordArea(model.getwordList());
                    if (model.getPuzzleType().equals(Model.PuzzleType.WORDSEARCH)) {
                        view.printWordsearch(model.getMatrix());
                    } else {
                        view.printCrossword(model.getMatrix());
                    }
                    view.setSolutionMatrix(model.getMatrixSolution());
                } catch (Exception ex) {
                }
            }
        }
    }

    private class OpenWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialog();
            if (filePath != null && !filePath.equals("")) {
                try {
                    model.loadWordList(filePath);
                } catch (Exception ex) {
                }
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.remove(view.getWord());
            view.updateWordArea(model.getwordList());
        }
    }

    private class SavePuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileSaveDialog();
            if (filePath != null && !filePath.equals("")) {
                try {
                    if (filePath.length() > 4
                            && filePath.substring(filePath.length() - 4, filePath.length()).equals(".txt")) {
                        model.savePuzzle(filePath);
                    } else {
                        model.savePuzzle(filePath + ".txt");
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    private class SaveWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileSaveDialog();
            if (filePath != null && !filePath.equals("")) {
                try {
                    if (filePath.length() > 4
                            && filePath.substring(filePath.length() - 4, filePath.length()).equals(".txt")) {
                        model.saveWordList(filePath);
                    } else {
                        model.saveWordList(filePath + ".txt");
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    private class SolutionButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            solutionDisplayed = !solutionDisplayed;
            view.setSolution(solutionDisplayed);
        }
    }

    private class WordBoxListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.add(view.getWord());
            view.updateWordArea(model.getwordList());
        }
    }

    private class WordBoxMouseListener implements MouseListener {

        public void mousePressed(MouseEvent e) {
            view.selectText();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
    }
}
