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
    Model model;

    public ControllerGUI() {
        model = new Model();
        view = new ViewGUI();
        view.printGreeting();
        view.addAddButtonListener(new AddButtonListener());
        view.addClearButtonListener(new ClearButtonListener());
        view.addExitMenuListener(new ExitMenuItemListener());
        view.addExportButtonListener(new ExportButtonListener());
        view.addGenerateButtonListener(new GenerateButtonListener());
        view.addOpenPuzzleListener(new OpenPuzzleListener());
        view.addOpenWordListListener(new OpenWordListListener());
        view.addRemoveButtonListener(new RemoveButtonListener());
        view.addSavePuzzleListener(new SavePuzzleListener());
        view.addSaveWordListListener(new SaveWordListListener());
        view.addWordBoxListener(new WordBoxListener());
        view.addWordBoxMouseListener(new WordBoxMouseListener());
    }

    private class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (model.add(view.getWord())) {
                view.updateWordArea(model.getwordList());
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
            view.fileSaveDialogue();
        }
    }

    private class GenerateButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.generate();
            view.printPuzzle(model.getMatrix());
        }
    }

    private class OpenPuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialogue();
            if (filePath != null) {
                model.loadPuzzle(filePath);
                view.printPuzzle(model.getMatrix());
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class OpenWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String filePath = view.fileOpenDialogue();
            if (filePath != null) {
                model.loadWordList(filePath);
                view.updateWordArea(model.getwordList());
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //if (model.remove(view.getWord())) {
            model.remove(view.getWord());
            view.updateWordArea(model.getwordList());
            //}
        }
    }
    private class SavePuzzleListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.savePuzzle(view.fileSaveDialogue());
        }
    }

    private class SaveWordListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.saveWordList(view.fileSaveDialogue());
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
