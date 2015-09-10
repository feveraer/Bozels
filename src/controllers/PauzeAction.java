/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class PauzeAction extends AbstractAction {

    private WereldModel model;

    public PauzeAction(WereldModel model, String opschrift) {
        super(opschrift);
        this.model = model;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.getPauze()){
            model.setPauze(false);
        }
        else{
            model.setPauze(true);
        }
    }
    
}
