/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bozels.BozelType;
import controllers.KleurenKiezer;
import hulpklassen.ColorIcon;
import hulpklassen.IconListRenderer;
import hulpklassen.Verifier;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TabBozels extends JPanel implements ListSelectionListener, PropertyChangeListener, ActionListener, ChangeListener {

    private JList lijst;
    private Map<Object, Icon> icons;
    private JPanel eigenschappenPnl;
    private JPanel lijstPnl;
    private JLabel dichtheidLbl;
    private JLabel restitutieLbl;
    private JLabel wrijvingLbl;
    private JLabel kleurLbl;
    private JTextField dichtheidTxt;
    private JTextField restitutieTxt;
    private JTextField wrijvingTxt;
    private JButton kleurBtn;
    private BozelType bozelType;
    private WereldModel model;
    private Verifier verifier;

    public TabBozels(WereldModel model) {

        this.model = model;

        verifier = new Verifier();

        icons = new HashMap<Object, Icon>();
        icons.put("Rood",
                new ColorIcon(model.getBtRood().getKleur()));
        icons.put("Blauw",
                new ColorIcon(model.getBtBlauw().getKleur()));
        icons.put("Wit",
                new ColorIcon(model.getBtWit().getKleur()));
        icons.put("Geel",
                new ColorIcon(model.getBtGeel().getKleur()));

        lijst = new JList(
                new Object[]{
                    "Rood", "Blauw", "Wit", "Geel"});
        lijst.setCellRenderer(new IconListRenderer(icons));

        lijst.setBorder(BorderFactory.createLoweredBevelBorder());
        lijst.setSelectedIndex(0);
        lijst.addListSelectionListener(this);

        dichtheidLbl = new JLabel("Dichtheid:");
        restitutieLbl = new JLabel("Restitutie:");
        wrijvingLbl = new JLabel("Wrijving:");
        kleurLbl = new JLabel("Kleur:");

        dichtheidTxt = new JTextField(String.valueOf(model.getBtRood().getDichtheid()));

        Set set = new HashSet(dichtheidTxt.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        set.clear();
        set.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

        dichtheidTxt.setInputVerifier(verifier);
        dichtheidTxt.addFocusListener(verifier);
        dichtheidTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        dichtheidTxt.addPropertyChangeListener(this);

        restitutieTxt = new JTextField(String.valueOf(model.getBtRood().getRestitutie()));
        restitutieTxt.setInputVerifier(verifier);
        restitutieTxt.addFocusListener(verifier);
        restitutieTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        restitutieTxt.addPropertyChangeListener(this);

        wrijvingTxt = new JTextField(String.valueOf(model.getBtRood().getWrijving()));
        wrijvingTxt.setInputVerifier(verifier);
        wrijvingTxt.addFocusListener(verifier);
        wrijvingTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        wrijvingTxt.addPropertyChangeListener(this);

        bozelType = model.getBtRood();
        kleurBtn = new JButton();
        kleurBtn.setBackground(bozelType.getKleur());
        kleurBtn.addActionListener(this);

        eigenschappenPnl = new JPanel();
        lijstPnl = new JPanel();


        GroupLayout layout = new GroupLayout(eigenschappenPnl);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setVerticalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(dichtheidLbl).addComponent(dichtheidTxt)).addGroup(layout.createParallelGroup().addComponent(restitutieLbl).addComponent(restitutieTxt)).addGroup(layout.createParallelGroup().addComponent(wrijvingLbl).addComponent(wrijvingTxt)).addGroup(layout.createParallelGroup().addComponent(kleurLbl).addComponent(kleurBtn)));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(dichtheidLbl).addComponent(restitutieLbl).addComponent(wrijvingLbl).addComponent(kleurLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(dichtheidTxt).addComponent(restitutieTxt).addComponent(wrijvingTxt).addComponent(kleurBtn)));
        layout.linkSize(SwingConstants.VERTICAL, kleurLbl, kleurBtn);


        eigenschappenPnl.setLayout(layout);
        eigenschappenPnl.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout layout2 = new GroupLayout(this);
        layout2.setAutoCreateGaps(true);
        layout2.setAutoCreateContainerGaps(true);

        layout2.setVerticalGroup(
                layout2.createParallelGroup().addComponent(lijst).addComponent(eigenschappenPnl));

        layout2.setHorizontalGroup(
                layout2.createSequentialGroup().addComponent(lijst).addComponent(eigenschappenPnl));
        layout2.linkSize(SwingConstants.VERTICAL, eigenschappenPnl, lijst);
        this.setLayout(layout2);

        model.addChangeListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (lijst.getSelectedIndex() == 1) {
            dichtheidTxt.setText(String.valueOf(model.getBtBlauw().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getBtBlauw().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getBtBlauw().getWrijving()));
            bozelType = model.getBtBlauw();
        } else if (lijst.getSelectedIndex() == 2) {
            dichtheidTxt.setText(String.valueOf(model.getBtWit().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getBtWit().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getBtWit().getWrijving()));
            bozelType = model.getBtWit();
        } else if (lijst.getSelectedIndex() == 3) {
            dichtheidTxt.setText(String.valueOf(model.getBtGeel().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getBtGeel().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getBtGeel().getWrijving()));
            bozelType = model.getBtGeel();
        } else if (lijst.getSelectedIndex() == 0) {
            dichtheidTxt.setText(String.valueOf(model.getBtRood().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getBtRood().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getBtRood().getWrijving()));
            bozelType = model.getBtRood();
        }
        kleurBtn.setBackground(bozelType.getKleur());
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        try {
            Object source = e.getSource();
            if (source == dichtheidTxt) {
                if (lijst.getSelectedIndex() == 1) {
                    model.getBtBlauw().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getBtWit().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getBtGeel().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 0) {
                    model.getBtRood().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                }
            }
            if (source == restitutieTxt) {
                if (lijst.getSelectedIndex() == 1) {
                    model.getBtBlauw().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getBtWit().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getBtGeel().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 0) {
                    model.getBtRood().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                }
            }
            if (source == wrijvingTxt) {
                if (lijst.getSelectedIndex() == 1) {
                    model.getBtBlauw().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getBtWit().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getBtGeel().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 0) {
                    model.getBtRood().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                }
            }
        } catch (NumberFormatException ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new KleurenKiezer(bozelType, model);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (lijst.getSelectedIndex() == 1) {
            kleurBtn.setBackground(model.getBtBlauw().getKleur());
            icons.remove("Blauw");
            icons.put("Blauw", new ColorIcon(model.getBtBlauw().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 2) {
            kleurBtn.setBackground(model.getBtWit().getKleur());
            icons.remove("Wit");
            icons.put("Wit", new ColorIcon(model.getBtWit().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 3) {
            kleurBtn.setBackground(model.getBtGeel().getKleur());
            icons.remove("Geel");
            icons.put("Geel", new ColorIcon(model.getBtGeel().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else {
            kleurBtn.setBackground(model.getBtRood().getKleur());
            icons.remove("Rood");
            icons.put("Rood", new ColorIcon(model.getBtRood().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        }
    }
}
