/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import bozels.Bozels;
import gui.LevelVenster;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jbox2d.dynamics.Body;
import wereld.WereldModel;

/**
 * Dit zorgt voor de world.step als ook veschillende controles wanneer bepaalde
 * objecten verwijderd mogen worden.
 * 
 * @author Frederic Everaert
 */
public class HoofdDraad implements Runnable {

    private WereldModel model;
    private LevelVenster levelVenster;

    public HoofdDraad(WereldModel model, LevelVenster levelVenster) {
        this.model = model;
        this.levelVenster = levelVenster;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Mag enkel controle doen als simulatieLoopt true is en er een level ingeladen is.
                if (model.getSimulatieLoopt() && !model.getLevel().isEmpty()) {
                    model.getWereld().step(model.getTijdstap(), model.getWereldSnelheid(), 3);

                    // Bozel mag verwijderd worden als hij slaapt of als hij out of bounds is.
                    if (!model.getBozelLijst().isEmpty()) {
                        if (!model.getBozelLijst().get(0).getBody().isAwake()
                                || (model.getBozelLijst().get(0).getBody().getPosition().x * 7 > levelVenster.getWidth()
                                || model.getBozelLijst().get(0).getBody().getPosition().x * 7 < 0)) {
                            model.getWereld().destroyBody(model.getBozelLijst().get(0).getBody());
                            model.getBozelLijst().get(0).getBozelType().verwijder(model.getBozelLijst().get(0).getId());
                            model.getBozelLijst().remove(0);
                            model.setMouseDragged(false);
                            model.setMouseReleased(false);
                            model.setMouseClicked(true);
                            if (!model.getBozelLijst().isEmpty()) {
                                model.getBozelLijst().get(0).setActive();
                            }
                        }
                        // Blauwe bozel valt anders te traag stil.
                        if (!model.getBozelLijst().isEmpty() && "blue".equals(model.getBozelLijst().get(0).getType())
                                && model.getBozelLijst().get(0).getBody().getLinearVelocity().length() < 0.5f
                                && model.isMouseReleased()) {
                            model.getBozelLijst().get(0).getBody().setLinearDamping(0.5f);
                        }
                    }

                    //Blocks verwijderd als ze zich bevinden in de Set gebrokenBlocks.
                    for (int i = model.getBlockLijst().size() - 1; i >= 0; i--) {
                        if (model.getGebrokenBlocks().size() > 0 && model.getGebrokenBlocks().contains(model.getBlockLijst().get(i))) {
                            model.getWereld().destroyBody(model.getBlockLijst().get(i).getBody());
                            model.getBlockLijst().get(i).getType().verwijder(model.getBlockLijst().get(i));
                            model.getBlockLijst().remove(model.getBlockLijst().get(i));
                        }
                    }
                    //Ellipsen verwijderd als ze zich bevinden in de Set gebrokenEllipsen.
                    //Ook ellipsen afremmen met lineairDamping.
                    for (int i = model.getEllipsenLijst().size() - 1; i >= 0; i--) {
                        if(model.getEllipsenLijst().get(i).getBody().getLinearVelocity().length() < 0.5f){
                            model.getEllipsenLijst().get(i).getBody().setLinearDamping(0.5f);
                        }
                        if (model.getGebrokenEllipsen().size() > 0 && model.getGebrokenEllipsen().contains(model.getEllipsenLijst().get(i))) {
                            model.getWereld().destroyBody(model.getEllipsenLijst().get(i).getBody());
                            model.getEllipsenLijst().get(i).getType().verwijder(model.getEllipsenLijst().get(i));
                            model.getEllipsenLijst().remove(model.getEllipsenLijst().get(i));
                        }
                    }
                    //Targets verwijderen als ze zich bevinden in de set gebrokenTargets of als ze buiten
                    //beeld gaan.
                    for (int i = model.getTargetLijst().size() - 1; i >= 0; i--) {
                        if ((model.getGebrokenTargets().size() > 0 && model.getGebrokenTargets().contains(model.getTargetLijst().get(i)))
                                || (model.getTargetLijst().get(i).getBody().getPosition().x * 7 > levelVenster.getWidth()
                                || model.getTargetLijst().get(i).getBody().getPosition().x * 7 < 0)) {
                            model.getWereld().destroyBody(model.getTargetLijst().get(i).getBody());
                            model.getTargetLijst().get(i).getType().verwijder(model.getTargetLijst().get(i));
                            model.getTargetLijst().remove(model.getTargetLijst().get(i));
                        }
                    }
                    
                    levelVenster.repaint();

                    //targets = vernietigd? Gewonnen!
                    if (model.getTargetLijst().isEmpty()) {
                        JFrame winstFrame = new JFrame();
                        Icon winIcon = new ImageIcon(Bozels.class.getResource("/images/winst_icon.png"));
                        JOptionPane.showMessageDialog(winstFrame, "Yippee-ki-yay!", "Gewonnen!", JOptionPane.INFORMATION_MESSAGE, winIcon);
                        winstFrame.dispose();
                        model.herstart(model.getLevel());
                    }

                    //Als alles bijna stilligt heb je pas verloren.
                    boolean allSleeping = true;
                    for (Body body = model.getWereld().getBodyList(); body != null && allSleeping; body = body.getNext()) {
                        allSleeping = (body.getLinearVelocity().length() < 0.5f);
                    }

                    //Er is nog minstens 1 target? Verloren!
                    if (!model.getTargetLijst().isEmpty() && model.getBozelLijst().isEmpty() && allSleeping) {
                        JFrame verliesFrame = new JFrame();
                        Icon verliesIcon = new ImageIcon(Bozels.class.getResource("/images/verlies_icon.png"));
                        JOptionPane.showMessageDialog(verliesFrame, "Who's the king?!", "Verloren!", JOptionPane.INFORMATION_MESSAGE, verliesIcon);
                        verliesFrame.dispose();
                        model.herstart(model.getLevel());
                    }
                }
            } catch (NullPointerException ex) {
            }
            try {
                Thread.sleep((long) (model.getTijdstap() * 600));
            } catch (InterruptedException ex) {
                System.out.println("Interrupt");
            }
        }
    }
}
