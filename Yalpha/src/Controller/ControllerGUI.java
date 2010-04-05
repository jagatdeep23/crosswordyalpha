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

        public void actionPerformed(ActionEvent e){
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

    private class ExportButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.createJOptionPane("Export will be functioning after iteration 3.", "Export", 0);
        }
    }

    private class GenerateButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.generate();
            solutionDisplayed = false;
            if (view.getPuzzleType().equals("Crossword")){
                view.printCrossword(model.getMatrix());
            }
            else {
                view.printWordsearch(model.getMatrix());
            }
        }
    }

    private class OpenPuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialog();
            if (filePath != null && !filePath.equals("")) {
                model.loadPuzzle(filePath);
                view.printWordsearch(model.getMatrix());
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class OpenWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialog();
            if (filePath != null && !filePath.equals("")) {
                model.loadWordList(filePath);
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //if (model.remove(view.getWord())) {
            model.remove(view.getWord());
            view.updateWordArea(model.getwordList());
            //}
        }
    }

    private class SavePuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileSaveDialog();
            if (filePath != null && !filePath.equals("")) {
                model.savePuzzle(filePath);
            }
        }
    }

    private class SaveWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileSaveDialog();
            if (filePath != null && !filePath.equals("")) {
                model.saveWordList(filePath);
            }
        }
    }

    private class SolutionButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            solutionDisplayed = !solutionDisplayed;
            if (solutionDisplayed) {
                if (view.getPuzzleType().equals("Crossword")){
                    view.printCrosswordSolution(model.getMatrix());
                }
                else {
                    view.printWordsearch(model.getMatrixSolution());
                }
            } else {
                if (view.getPuzzleType().equals("Crossword")){
                    view.printCrossword(model.getMatrix());
                }
                else {
                    view.printWordsearch(model.getMatrix());
                }
            }
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
