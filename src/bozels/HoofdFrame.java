/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Zal pas afsluiten nadat er op Ja wordt geklikt bij een windowClosing event.
 * 
 * @author Frederic Everaert
 */
public class HoofdFrame extends JFrame implements WindowListener {

    private static final String[] OPTIONS = {"Ja", "Nee"};

    public HoofdFrame() throws HeadlessException {
        super();
        addWindowListener(this);
    }

    public HoofdFrame(String title) throws HeadlessException {
        super(title);
        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();
        int answer = JOptionPane.showOptionDialog(window,
                "Wilt u Bozels echt afsluiten?",
                "Afsluiten",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                OPTIONS,
                OPTIONS[0]);
        if (answer == 0) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
