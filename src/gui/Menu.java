/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.LevelKiezer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wereld.WereldModel;

/**
 * 
 * @author Frederic Everaert
 */
public class Menu extends JMenuBar implements ActionListener, ChangeListener {

    private WereldModel model;
    private JMenu bestand;
    private JMenu spel;
    private JMenuItem openlevel;
    private JMenuItem afsluiten;
    private JCheckBoxMenuItem pauzeren;
    private JMenuItem herstarten;

    
    public Menu(WereldModel model, Action[] actions) {

        this.model = model;
        bestand = new JMenu("Bestand");
        bestand.setMnemonic(KeyEvent.VK_B);
        add(bestand);
        spel = new JMenu("Spel");
        spel.setMnemonic(KeyEvent.VK_S);
        add(spel);

        openlevel = new JMenuItem("Open level");
        openlevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        bestand.add(openlevel);
        afsluiten = new JMenuItem("Afsluiten");
        afsluiten.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        bestand.add(afsluiten);
        pauzeren = new JCheckBoxMenuItem(actions[0]);
        pauzeren.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,0));
        spel.add(pauzeren);
        herstarten = new JMenuItem(actions[1]);
        //herstarten.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
        herstarten.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        spel.add(herstarten);

        openlevel.addActionListener(this);
        afsluiten.addActionListener(this);

        model.addChangeListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (openlevel == e.getSource()) {
            new LevelKiezer(new File(model.getDir()), model);
        }

        if (afsluiten == e.getSource()) {
            Object[] options = {"Ja", "Nee"};
            JFrame sluitFrame = new JFrame();
            int n = JOptionPane.showOptionDialog(sluitFrame, "Wilt u Bozels echt afsluiten?", "Afsluiten", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if(n == 0){
                System.exit(0);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        pauzeren.setState(((WereldModel) e.getSource()).getPauze());
//        System.out.println(((BozelModel) e.getSource()).getLevel());
//        ((BozelModel) e.getSource()).printLvl();
        herstarten.setEnabled(model.isHerstartEnabled());
    }
}
