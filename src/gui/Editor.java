/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wereld.WereldModel;

/**
 * Onderste paneel met aanpasbare instellingen en knoppen.
 * 
 * @author Frederic Everaert
 */
public class Editor extends JPanel implements ChangeListener {
    
    private WereldModel model;
    private JToggleButton pauzeknop;
    private JButton herstartknop;
    private JTabbedPane tabBladen;
    
    public Editor(WereldModel model, Action[] actions) {

        this.model = model;
        this.setPreferredSize(new Dimension(1024,200));
        
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        pauzeknop = new JToggleButton(actions[0]);
        herstartknop = new JButton(actions[1]);
        tabBladen = new JTabbedPane();
        tabBladen.addTab("Algemeen", new TabAlgemeen(model));
        tabBladen.addTab("Materialen", new TabMaterialen(model));
        tabBladen.addTab("Bozels", new TabBozels(model));
        tabBladen.addTab("Doelen", new TabDoelen(model));
        
        layout.setVerticalGroup(
                layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(pauzeknop)
                            .addComponent(herstartknop)
                    )
                    .addComponent(tabBladen)
        );
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(pauzeknop)
                        .addComponent(herstartknop)
                )
                .addComponent(tabBladen)
        );
        
        layout.linkSize(SwingConstants.HORIZONTAL, pauzeknop, herstartknop);
        
        this.setLayout(layout);
        model.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        pauzeknop.setSelected(((WereldModel) e.getSource()).getPauze());
        herstartknop.setEnabled(model.isHerstartEnabled());
    }
    
}
