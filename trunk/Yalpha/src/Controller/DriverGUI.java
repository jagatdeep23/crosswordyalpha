package Controller;

import javax.swing.UIManager;

/**
 * Insertion point for the GUI.
 *
 * @author Team Yalpha, specifically Bob Grube and Matt Maze
 * @version 1.0
 */
public class DriverGUI {

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            
        }
        ControllerGUI controller = new ControllerGUI();
    }
}
