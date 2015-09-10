/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hulpklassen;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Kijk of input juiste waarde is of niet en pas tekstkleur aan naargelang.
 * 
 * @author Frederic Everaert
 */
public class Verifier extends InputVerifier implements FocusListener {

    @Override
    public boolean shouldYieldFocus(JComponent comp) {
        JTextField field = (JTextField) comp;
        if (!verify(comp)) {
            field.setBackground(Color.RED);
            field.setForeground(Color.WHITE);
            return false;
        } else {
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
            return true;
        }
    }

    @Override
    public boolean verify(JComponent comp) {
        JTextField field = (JTextField) comp;
        try {
            float waarde = Float.valueOf(field.getText());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (!e.isTemporary()) {
            JTextField field = (JTextField) e.getSource();
            field.setCaretPosition(field.getText().length());
            field.setBackground(Color.YELLOW);
            field.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (!e.isTemporary()) {
            JTextField field = (JTextField) e.getSource();
            field.setCaretPosition(field.getText().length());
            field.moveCaretPosition(0);
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
        }
    }
}
