/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import wereld.WereldModel;

/**
 * 
 * @author Frederic Everaert
 */
public class HerstartAction extends AbstractAction {

    private WereldModel model;

    public HerstartAction(WereldModel model, String opschrift) {
        super(opschrift);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setHerstartEnabled(false);

        Timer timer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = (Timer) e.getSource();
                model.herstart(model.getLevel());
                model.setHerstartEnabled(true);
                timer.stop();
            }
        });
        if (model.getSimulatieLoopt()) {
            model.stopSimulatie();
        }
        timer.start();
    }
}
