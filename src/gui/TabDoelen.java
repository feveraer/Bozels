/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.KleurenKiezer;
import hulpklassen.ColorIcon;
import hulpklassen.IconListRenderer;
import hulpklassen.Verifier;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;
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
import targets.TargetType;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TabDoelen extends JPanel implements ListSelectionListener, PropertyChangeListener,
        ActionListener, ChangeListener, ItemListener {

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
    private TargetType targetType;
    private WereldModel model;
    private JLabel krachtdrLbl;
    private JLabel sterkteLbl;
    private JTextField krachtdrTxt;
    private JTextField sterkteTxt;
    private JCheckBox breekCheck;
    private Verifier verifier;

    public TabDoelen(WereldModel model) {

        this.model = model;

        verifier = new Verifier();

        icons = new HashMap<Object, Icon>();

        icons.put("Klein",
                new ColorIcon(model.getTtKlein().getKleur()));
        icons.put("Groot",
                new ColorIcon(model.getTtGroot().getKleur()));

        lijst = new JList(
                new Object[]{
                    "Klein", "Groot"});
        lijst.setCellRenderer(new IconListRenderer(icons));

        lijst.setBorder(BorderFactory.createLoweredBevelBorder());
        lijst.setSelectedIndex(0);
        lijst.addListSelectionListener(this);

        dichtheidLbl = new JLabel("Dichtheid:");
        restitutieLbl = new JLabel("Restitutie:");
        wrijvingLbl = new JLabel("Wrijving:");
        kleurLbl = new JLabel("Kleur:");
        breekCheck = new JCheckBox("Breekbaar");
        breekCheck.setSelected(model.getTtKlein().isBreekbaar());
        breekCheck.addItemListener(this);
        krachtdrLbl = new JLabel("Krachtdrempel:");
        sterkteLbl = new JLabel("Sterkte:");

        dichtheidTxt = new JTextField(String.valueOf(model.getTtKlein().getDichtheid()));

        Set set = new HashSet(dichtheidTxt.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        set.clear();
        set.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

        dichtheidTxt.setInputVerifier(verifier);
        dichtheidTxt.addFocusListener(verifier);
        dichtheidTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        dichtheidTxt.addPropertyChangeListener(this);

        restitutieTxt = new JTextField(String.valueOf(model.getTtKlein().getRestitutie()));
        restitutieTxt.setInputVerifier(verifier);
        restitutieTxt.addFocusListener(verifier);
        restitutieTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        restitutieTxt.addPropertyChangeListener(this);

        wrijvingTxt = new JTextField(String.valueOf(model.getTtKlein().getWrijving()));
        wrijvingTxt.setInputVerifier(verifier);
        wrijvingTxt.addFocusListener(verifier);
        wrijvingTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        wrijvingTxt.addPropertyChangeListener(this);

        krachtdrTxt = new JTextField(String.valueOf(model.getTtKlein().getKrachtdrempel()));
        krachtdrTxt.setInputVerifier(verifier);
        krachtdrTxt.addFocusListener(verifier);
        krachtdrTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        krachtdrTxt.addPropertyChangeListener(this);

        sterkteTxt = new JTextField(String.valueOf(model.getTtKlein().getSterkte()));
        sterkteTxt.setInputVerifier(verifier);
        sterkteTxt.addFocusListener(verifier);
        sterkteTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        sterkteTxt.addPropertyChangeListener(this);

        targetType = model.getTtKlein();
        kleurBtn = new JButton();
        kleurBtn.setBackground(targetType.getKleur());
        kleurBtn.addActionListener(this);

        eigenschappenPnl = new JPanel();

        GroupLayout layout = new GroupLayout(eigenschappenPnl);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setVerticalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(dichtheidLbl).addComponent(dichtheidTxt).addComponent(breekCheck)).addGroup(layout.createParallelGroup().addComponent(restitutieLbl).addComponent(restitutieTxt).addComponent(krachtdrLbl).addComponent(krachtdrTxt)).addGroup(layout.createParallelGroup().addComponent(wrijvingLbl).addComponent(wrijvingTxt).addComponent(sterkteLbl).addComponent(sterkteTxt)).addGroup(layout.createParallelGroup().addComponent(kleurLbl).addComponent(kleurBtn)));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(dichtheidLbl).addComponent(restitutieLbl).addComponent(wrijvingLbl).addComponent(kleurLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(dichtheidTxt).addComponent(restitutieTxt).addComponent(wrijvingTxt).addComponent(kleurBtn)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(krachtdrLbl).addComponent(sterkteLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(breekCheck).addComponent(krachtdrTxt).addComponent(sterkteTxt)));
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

        if (lijst.getSelectedIndex() == 0) {
            dichtheidTxt.setText(String.valueOf(model.getTtKlein().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getTtKlein().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getTtKlein().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getTtKlein().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getTtKlein().getSterkte()));
            breekCheck.setSelected(model.getTtKlein().isBreekbaar());
            targetType = model.getTtKlein();
        } else if (lijst.getSelectedIndex() == 1) {
            dichtheidTxt.setText(String.valueOf(model.getTtGroot().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getTtGroot().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getTtGroot().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getTtGroot().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getTtGroot().getSterkte()));
            breekCheck.setSelected(model.getTtGroot().isBreekbaar());
            targetType = model.getTtGroot();
        }
        kleurBtn.setBackground(targetType.getKleur());

    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        try {
            Object source = e.getSource();
            if (source == dichtheidTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getTtKlein().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getTtGroot().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                }
            }
            if (source == restitutieTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getTtKlein().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getTtGroot().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                }
            }
            if (source == wrijvingTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getTtKlein().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getTtGroot().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                }
            }
            if (source == krachtdrTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getTtKlein().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getTtGroot().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                }
            }
            if (source == sterkteTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getTtKlein().setSterkte(Float.valueOf(sterkteTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getTtGroot().setSterkte(Float.valueOf(sterkteTxt.getText()));
                }
            }
        } catch (NumberFormatException ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new KleurenKiezer(targetType, model);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (lijst.getSelectedIndex() == 0) {
            kleurBtn.setBackground(model.getTtKlein().getKleur());
            icons.remove("Klein");
            icons.put("Klein", new ColorIcon(model.getTtKlein().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 1) {
            kleurBtn.setBackground(model.getTtGroot().getKleur());
            icons.remove("Groot");
            icons.put("Groot", new ColorIcon(model.getTtGroot().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (lijst.getSelectedIndex() == 0) {
            model.getTtKlein().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        } else {
            model.getTtGroot().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        }

    }
}
