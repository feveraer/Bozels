/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class LevelKiezer extends JFileChooser {

    private FileNameExtensionFilter filter;
    private String gekozenLevel;
    private WereldModel model;

    public LevelKiezer(File dir, WereldModel model) {
        this.model = model;
        this.setCurrentDirectory(dir);
        filter = new FileNameExtensionFilter(
                "XML bestanden", "xml");
        this.setFileFilter(filter);
        this.setAcceptAllFileFilterUsed(false);
        int returnVal = this.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            gekozenLevel = this.getSelectedFile().getPath();
            nieuwLevel();
        }

    }

    public void nieuwLevel() {
        Timer timer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = (Timer) e.getSource();
                model.herstart(gekozenLevel);
                timer.stop();
            }
        });
        if (model.getSimulatieLoopt()) {
            model.stopSimulatie();
        }
        timer.start();
    }
}
