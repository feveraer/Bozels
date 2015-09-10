/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bozels.Bozel;
import controllers.Katapult;
import decor.Decor;
import java.awt.*;
import java.util.ConcurrentModificationException;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jbox2d.common.Vec2;
import targets.Target;
import wereld.WereldModel;

/**
 * View van het spel.
 * 
 * @author Frederic Everaert
 */
public class LevelVenster extends JPanel implements ChangeListener {

    private WereldModel model;

    public LevelVenster(WereldModel model) {

        this.model = model;
        this.setPreferredSize(new Dimension(1024, 450));
        this.setBackground(Color.WHITE);
        model.addChangeListener(this);
        Katapult katapult = new Katapult(model, this);
        this.addMouseListener(katapult);
        this.addMouseMotionListener(katapult);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            //Achtergrond tekenen.
            g.drawImage(model.getAchtergrond(), 0, 0, null);

            //Tekenen van de rays.
            if (model.isToonRays()) {
                Graphics2D g2 = (Graphics2D) g.create();
                for (int i = 0; i < model.getRayRaakPunt().size(); i++) {
                    g2.setColor(Color.RED);
                    g2.drawLine((int) (model.getRayRaakPunt().get(i).x * 7), (int) (getHeight() - model.getRayRaakPunt().get(i).y * 7),
                            (int) ((model.getRayRaakPunt().get(i).x + model.getRayKracht().get(i).x / 200) * 7),
                            (int) ((getHeight() - (model.getRayRaakPunt().get(i).y + model.getRayKracht().get(i).y / 200) * 7)));
                }
            }

            for (Bozel bozel : model.getBozelLijst()) {
                bozel.draw(g, this);
            }

            for (Target target : model.getTargetLijst()) {
                target.draw(g, this);
            }
            for (Decor block : model.getBlockLijst()) {
                block.draw(g, this);
            }

            for (Decor ellips : model.getEllipsenLijst()) {
                ellips.draw(g, this);
            }
            
            //Tekenen van de elastiek
            if (!model.getBozelLijst().isEmpty()) {
                Graphics2D g3 = (Graphics2D) g.create();
                Vec2 position = model.getBozelLijst().get(0).getBody().getPosition();
                if (!model.getBozelLijst().get(0).getBody().isActive() && !model.isMouseDragged()) {
                    g3.setColor(Color.BLUE);
                    g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g3.setStroke(new BasicStroke(2));
                    g3.drawLine((int) (model.getVuurPosX() * 7), (int) (getHeight() - model.getVuurPosY() * 7), (int) (position.x * 7), (int) (getHeight() - position.y * 7));
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("draw ConcurrentModificationException");

        } catch (NullPointerException ex) {
            System.out.println("draw NullPointerException");
        }

    }
}
