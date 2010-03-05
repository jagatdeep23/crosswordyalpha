package Controller;

import Model.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import Model.*;
import View.ViewGUI;

public class ControllerGUI {
	private ViewGUI view;
	Model model;

	public ControllerGUI() {
                JOptionPane.showMessageDialog(null, "Welcome to Yalpha's puzzle! \nThe rules are:\nEvery word needs to be over 3 and under 12 letters in length.");

                model = new Model();
		view = new ViewGUI();
		view.addAddButtonListener(new AddButtonListener());
		view.addClearButtonListener(new ClearButtonListener());
		view.addExitMenuListener(new ExitMenuItemListener());
		view.addGenerateButtonListener(new GenerateButtonListener());
		view.addWordBoxListener(new WordBoxListener());
		view.addWordBoxMouseListener(new WordBoxMouseListener());
	}

	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (model.add(view.getWord())){
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
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	private class GenerateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
                        model.generate();
			view.printPuzzle(model.getMatrix());
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
