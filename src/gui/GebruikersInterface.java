/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.HoofdDraad;
import java.awt.BorderLayout;
import javax.swing.Action;
import javax.swing.JPanel;
import wereld.WereldModel;

/**
 * Maakt bovenste en onderste paneel aan en start daarna de HoofdDraad met daarin
 * o.a. world.step.
 * 
 * @author Frederic Everaert
 */
public class GebruikersInterface extends JPanel {

    private LevelVenster levelVenster;

    public GebruikersInterface(WereldModel model, Action[] actions) {
        super(new BorderLayout());

        levelVenster = new LevelVenster(model);
        Editor editor = new Editor(model, actions);

        add(levelVenster, BorderLayout.NORTH);
        add(editor, BorderLayout.SOUTH);
        new Thread(new HoofdDraad(model, levelVenster)).start();

    }

    
}
