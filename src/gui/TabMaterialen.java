/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.KleurenKiezer;
import decor.MateriaalType;
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
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TabMaterialen extends JPanel implements ListSelectionListener, PropertyChangeListener,
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
    private MateriaalType matType;
    private WereldModel model;
    private JLabel krachtdrLbl;
    private JLabel sterkteLbl;
    private JTextField krachtdrTxt;
    private JTextField sterkteTxt;
    private JCheckBox breekCheck;
    private Verifier verifier;

    public TabMaterialen(WereldModel model) {
        this.model = model;

        verifier = new Verifier();



        icons = new HashMap<Object, Icon>();

        icons.put("Vast",
                new ColorIcon(model.getMtVast().getKleur()));
        icons.put("Beton",
                new ColorIcon(model.getMtBeton().getKleur()));
        icons.put("Hout",
                new ColorIcon(model.getMtHout().getKleur()));
        icons.put("Metaal",
                new ColorIcon(model.getMtMetaal().getKleur()));
        icons.put("Ijs",
                new ColorIcon(model.getMtIjs().getKleur()));

        lijst = new JList(
                new Object[]{
                    "Vast", "Beton", "Hout", "Metaal", "Ijs"});
        lijst.setCellRenderer(new IconListRenderer(icons));

        lijst.setBorder(BorderFactory.createLoweredBevelBorder());
        lijst.setSelectedIndex(0);
        lijst.addListSelectionListener(this);

        dichtheidLbl = new JLabel("Dichtheid:");
        restitutieLbl = new JLabel("Restitutie:");
        wrijvingLbl = new JLabel("Wrijving:");
        kleurLbl = new JLabel("Kleur:");
        breekCheck = new JCheckBox("Breekbaar");
        breekCheck.setSelected(model.getMtVast().isBreekbaar());
        breekCheck.addItemListener(this);
        krachtdrLbl = new JLabel("Krachtdrempel:");
        sterkteLbl = new JLabel("Sterkte:");

        dichtheidTxt = new JTextField(String.valueOf(model.getMtVast().getDichtheid()));

        Set set = new HashSet(dichtheidTxt.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        set.clear();
        set.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

        dichtheidTxt.setInputVerifier(verifier);
        dichtheidTxt.addFocusListener(verifier);
        dichtheidTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        dichtheidTxt.addPropertyChangeListener(this);

        restitutieTxt = new JTextField(String.valueOf(model.getMtVast().getRestitutie()));
        restitutieTxt.setInputVerifier(verifier);
        restitutieTxt.addFocusListener(verifier);
        restitutieTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        restitutieTxt.addPropertyChangeListener(this);

        wrijvingTxt = new JTextField(String.valueOf(model.getMtVast().getWrijving()));
        wrijvingTxt.setInputVerifier(verifier);
        wrijvingTxt.addFocusListener(verifier);
        wrijvingTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        wrijvingTxt.addPropertyChangeListener(this);

        krachtdrTxt = new JTextField(String.valueOf(model.getMtVast().getKrachtdrempel()));
        krachtdrTxt.setInputVerifier(verifier);
        krachtdrTxt.addFocusListener(verifier);
        krachtdrTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        krachtdrTxt.addPropertyChangeListener(this);

        sterkteTxt = new JTextField(String.valueOf(model.getMtVast().getSterkte()));
        sterkteTxt.setInputVerifier(verifier);
        sterkteTxt.addFocusListener(verifier);
        sterkteTxt.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
        sterkteTxt.addPropertyChangeListener(this);

        matType = model.getMtVast();
        kleurBtn = new JButton();
        kleurBtn.setBackground(matType.getKleur());
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
            dichtheidTxt.setText(String.valueOf(model.getMtVast().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getMtVast().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getMtVast().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getMtVast().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getMtVast().getSterkte()));
            breekCheck.setSelected(model.getMtVast().isBreekbaar());
            matType = model.getMtVast();
        } else if (lijst.getSelectedIndex() == 1) {
            dichtheidTxt.setText(String.valueOf(model.getMtBeton().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getMtBeton().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getMtBeton().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getMtBeton().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getMtBeton().getSterkte()));
            breekCheck.setSelected(model.getMtBeton().isBreekbaar());
            matType = model.getMtBeton();
        } else if (lijst.getSelectedIndex() == 2) {
            dichtheidTxt.setText(String.valueOf(model.getMtHout().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getMtHout().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getMtHout().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getMtHout().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getMtHout().getSterkte()));
            breekCheck.setSelected(model.getMtHout().isBreekbaar());
            matType = model.getMtHout();
        } else if (lijst.getSelectedIndex() == 3) {
            dichtheidTxt.setText(String.valueOf(model.getMtMetaal().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getMtMetaal().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getMtMetaal().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getMtMetaal().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getMtMetaal().getSterkte()));
            breekCheck.setSelected(model.getMtMetaal().isBreekbaar());
            matType = model.getMtMetaal();
        } else if (lijst.getSelectedIndex() == 4) {
            dichtheidTxt.setText(String.valueOf(model.getMtIjs().getDichtheid()));
            restitutieTxt.setText(String.valueOf(model.getMtIjs().getRestitutie()));
            wrijvingTxt.setText(String.valueOf(model.getMtIjs().getWrijving()));
            krachtdrTxt.setText(String.valueOf(model.getMtIjs().getKrachtdrempel()));
            sterkteTxt.setText(String.valueOf(model.getMtIjs().getSterkte()));
            breekCheck.setSelected(model.getMtIjs().isBreekbaar());
            matType = model.getMtIjs();
        }
        kleurBtn.setBackground(matType.getKleur());

    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        try {
            Object source = e.getSource();
            if (source == dichtheidTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getMtVast().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getMtBeton().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getMtHout().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getMtMetaal().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                } else if (lijst.getSelectedIndex() == 4) {
                    model.getMtIjs().setDichtheid(Float.valueOf(dichtheidTxt.getText()));
                }
            }
            if (source == restitutieTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getMtVast().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getMtBeton().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getMtHout().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getMtMetaal().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                } else if (lijst.getSelectedIndex() == 4) {
                    model.getMtIjs().setRestitutie(Float.valueOf(restitutieTxt.getText()));
                }
            }
            if (source == wrijvingTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getMtVast().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getMtBeton().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getMtHout().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getMtMetaal().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                } else if (lijst.getSelectedIndex() == 4) {
                    model.getMtIjs().setWrijving(Float.valueOf(wrijvingTxt.getText()));
                }
            }
            if (source == krachtdrTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getMtVast().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getMtBeton().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getMtHout().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getMtMetaal().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                } else if (lijst.getSelectedIndex() == 4) {
                    model.getMtIjs().setKrachtdrempel(Float.valueOf(krachtdrTxt.getText()));
                }
            }
            if (source == sterkteTxt) {
                if (lijst.getSelectedIndex() == 0) {
                    model.getMtVast().setSterkte(Float.valueOf(sterkteTxt.getText()));
                } else if (lijst.getSelectedIndex() == 1) {
                    model.getMtBeton().setSterkte(Float.valueOf(sterkteTxt.getText()));
                } else if (lijst.getSelectedIndex() == 2) {
                    model.getMtHout().setSterkte(Float.valueOf(sterkteTxt.getText()));
                } else if (lijst.getSelectedIndex() == 3) {
                    model.getMtMetaal().setSterkte(Float.valueOf(sterkteTxt.getText()));
                } else if (lijst.getSelectedIndex() == 4) {
                    model.getMtIjs().setSterkte(Float.valueOf(sterkteTxt.getText()));
                }
            }
        } catch (NumberFormatException ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new KleurenKiezer(matType, model);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (lijst.getSelectedIndex() == 0) {
            kleurBtn.setBackground(model.getMtVast().getKleur());
            icons.remove("Vast");
            icons.put("Vast", new ColorIcon(model.getMtVast().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 1) {
            kleurBtn.setBackground(model.getMtBeton().getKleur());
            icons.remove("Beton");
            icons.put("Beton", new ColorIcon(model.getMtBeton().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 2) {
            kleurBtn.setBackground(model.getMtHout().getKleur());
            icons.remove("Hout");
            icons.put("Hout", new ColorIcon(model.getMtHout().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 3) {
            kleurBtn.setBackground(model.getMtMetaal().getKleur());
            icons.remove("Metaal");
            icons.put("Metaal", new ColorIcon(model.getMtMetaal().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        } else if (lijst.getSelectedIndex() == 4) {
            kleurBtn.setBackground(model.getMtIjs().getKleur());
            icons.remove("Ijs");
            icons.put("Ijs", new ColorIcon(model.getMtIjs().getKleur()));
            lijst.setCellRenderer(new IconListRenderer(icons));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (lijst.getSelectedIndex() == 0) {
            model.getMtVast().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        } else if (lijst.getSelectedIndex() == 1) {
            model.getMtBeton().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        } else if (lijst.getSelectedIndex() == 2) {
            model.getMtHout().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        } else if (lijst.getSelectedIndex() == 3) {
            model.getMtMetaal().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        } else if (lijst.getSelectedIndex() == 4) {
            model.getMtIjs().setBreekbaar(e.getStateChange() == ItemEvent.SELECTED);
        }
    }
}
