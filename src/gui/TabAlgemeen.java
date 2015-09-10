/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import hulpklassen.Verifier;
import java.awt.KeyboardFocusManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TabAlgemeen extends JPanel implements PropertyChangeListener,
        ItemListener {

    private WereldModel model;
    private JLabel zwaartekrachtLabel;
    private JLabel tijdsstapLabel;
    private JLabel snelheidLabel;
    private JLabel lanceerkrachtLabel;
    private JTextField zwaartekrachtTxt;
    private JTextField tijdstapTxt;
    private JTextField snelheidTxt;
    private JTextField lanceerkrachtTxt;
    private JCheckBox zwaartepuntCheck;
    private JCheckBox toonSnelheidCheck;
    private JCheckBox slapendeCheck;
    private JCheckBox raysCheck;
    private Verifier verifier;

    public TabAlgemeen(WereldModel model) {

        this.model = model;

        verifier = new Verifier();

        zwaartekrachtLabel = new JLabel("Zwaartekracht:");
        tijdsstapLabel = new JLabel("Tijdsstap:");
        snelheidLabel = new JLabel("Snelheid:");
        lanceerkrachtLabel = new JLabel("Lanceerkracht:");
        zwaartekrachtTxt = new JTextField(String.valueOf(model.getZwaartekracht()));

        Set set = new HashSet(zwaartekrachtTxt.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        set.clear();
        set.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        zwaartekrachtTxt.setInputVerifier(verifier);
        zwaartekrachtTxt.addFocusListener(verifier);
        zwaartekrachtTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        zwaartekrachtTxt.addPropertyChangeListener(this);

        tijdstapTxt = new JTextField(String.valueOf(model.getTijdstap()));
        tijdstapTxt.setInputVerifier(verifier);
        tijdstapTxt.addFocusListener(verifier);
        tijdstapTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        tijdstapTxt.addPropertyChangeListener(this);

        snelheidTxt = new JTextField(String.valueOf(model.getWereldSnelheid()));
        snelheidTxt.setInputVerifier(verifier);
        snelheidTxt.addFocusListener(verifier);
        snelheidTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        snelheidTxt.addPropertyChangeListener(this);

        lanceerkrachtTxt = new JTextField(String.valueOf(model.getLanceerkracht()));
        lanceerkrachtTxt.setInputVerifier(verifier);
        lanceerkrachtTxt.addFocusListener(verifier);
        lanceerkrachtTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        lanceerkrachtTxt.addPropertyChangeListener(this);

        zwaartepuntCheck = new JCheckBox("Toon zwaartepunt");
        zwaartepuntCheck.addItemListener(this);
        zwaartepuntCheck.setSelected(model.isToonZwaartepunt());
        toonSnelheidCheck = new JCheckBox("Toon snelheid");
        toonSnelheidCheck.addItemListener(this);
        toonSnelheidCheck.setSelected(model.isToonSnelheid());
        slapendeCheck = new JCheckBox("Markeer slapende objecten");
        slapendeCheck.addItemListener(this);
        slapendeCheck.setSelected(model.isSlapend());
        raysCheck = new JCheckBox("Toon rays");
        raysCheck.addItemListener(this);
        raysCheck.setSelected(model.isToonRays());

        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setVerticalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(zwaartekrachtLabel).addComponent(zwaartekrachtTxt).addComponent(zwaartepuntCheck)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(tijdsstapLabel).addComponent(tijdstapTxt).addComponent(toonSnelheidCheck)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(snelheidLabel).addComponent(snelheidTxt).addComponent(slapendeCheck)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lanceerkrachtLabel).addComponent(lanceerkrachtTxt).addComponent(raysCheck)));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(zwaartekrachtLabel).addComponent(tijdsstapLabel).addComponent(snelheidLabel).addComponent(lanceerkrachtLabel)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(zwaartekrachtTxt).addComponent(tijdstapTxt).addComponent(snelheidTxt).addComponent(lanceerkrachtTxt)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(zwaartepuntCheck).addComponent(toonSnelheidCheck).addComponent(slapendeCheck).addComponent(raysCheck)));
        this.setLayout(layout);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        try{
        Object source = e.getSource();
        if (source == zwaartekrachtTxt) {
            model.setZwaartekracht(Float.valueOf(zwaartekrachtTxt.getText()));
        } else if (source == tijdstapTxt) {
            model.setTijdstap(Float.valueOf(tijdstapTxt.getText()));
        } else if (source == snelheidTxt) {
            model.setWereldSnelheid(Math.round(Float.valueOf(snelheidTxt.getText())));
            snelheidTxt.setText(String.valueOf(model.getWereldSnelheid()));
        } else if (source == lanceerkrachtTxt) {
            model.setLanceerkracht(Float.valueOf(lanceerkrachtTxt.getText()));
        }
        } catch(NumberFormatException ex){
            //Foute invoer wordt rood gemarkeerd en niet doorgestuurd naar model
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source == zwaartepuntCheck) {
            model.setToonZwaartepunt(e.getStateChange() == ItemEvent.SELECTED);
        } else if (source == toonSnelheidCheck) {
            model.setToonSnelheid(e.getStateChange() == ItemEvent.SELECTED);
        } else if (source == slapendeCheck) {
            model.setSlapend(e.getStateChange() == ItemEvent.SELECTED);
        } else if (source == raysCheck) {
            model.setToonRays(e.getStateChange() == ItemEvent.SELECTED);
        }
    }
}
