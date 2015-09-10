/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wereld.MijnType;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class KleurenKiezer extends JFrame implements ActionListener {
    
    private JFrame window;
    private JColorChooser kleurenKiezer;
    private JPanel panel;
    private JButton okKnop;
    private MijnType type;
    private WereldModel model;
            
    public KleurenKiezer(MijnType type, WereldModel model){
        this.type = type;
        this.model = model;
        window = new JFrame ("Kies een kleur");
        panel = new JPanel(new BorderLayout());
        okKnop = new JButton("OK");
        okKnop.addActionListener(this);
        kleurenKiezer = new JColorChooser(type.getKleur());
        panel.add(kleurenKiezer,BorderLayout.NORTH);
        panel.add(okKnop, BorderLayout.SOUTH);
        window.setContentPane(panel);
        window.pack();
        window.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        type.setKleur(kleurenKiezer.getColor());
        window.dispose();
    }
    
    
}
