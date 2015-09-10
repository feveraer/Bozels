/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.LevelVenster;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.jbox2d.common.Vec2;
import wereld.Explosie;
import wereld.WereldModel;

/**
 * Om bozels af te schieten.
 * Formules voor vectoren gebruikt als in de opgave.
 * 
 * @author Frederic Everaert
 */
public class Katapult implements MouseMotionListener, MouseListener {

    private WereldModel model;
    private LevelVenster view;
    private float x0;
    private float y0;
    private float x1;
    private float y1;
    private Vec2 r0;
    private Vec2 r1;
    private Vec2 rd;
    private float lengte;
    private float minLengte;
    private Vec2 eVec;
    private Vec2 krachtVec;

    public Katapult(WereldModel model, LevelVenster view) {
        this.model = model;
        this.view = view;
    }

    public void setVectoren(int xMouse, int yMouse) {
        y0 = model.getVuurPosY();
        x0 = model.getVuurPosX();
        x1 = ((float) xMouse) / 7f;
        y1 = ((float) view.getHeight() - yMouse) / 7f;
        r0 = new Vec2(x0, y0);
        r1 = new Vec2(x1, y1);
        rd = r0.sub(r1);
        lengte = rd.length();
        minLengte = Math.min(lengte, 7);
        eVec = new Vec2(minLengte * (rd.x / lengte), minLengte * (rd.y / lengte));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!model.isMouseClicked() && !model.isMouseReleased() && !model.getPauze()) {
            if (!model.getBozelLijst().isEmpty()) {
                setVectoren(e.getX(), e.getY());
                krachtVec = new Vec2(model.getLanceerkracht() * (eVec.length() / 7) * (rd.x / rd.normalize()), model.getLanceerkracht() * (eVec.length() / 7) * (rd.y / rd.normalize()));
                model.getBozelLijst().get(0).getBody().setActive(true);
                model.getBozelLijst().get(0).getBody().setLinearVelocity(krachtVec);
                model.setMouseDragged(true);
                model.setMouseReleased(true);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!model.isMouseDragged() && !model.getPauze()) {
            if (!model.getBozelLijst().isEmpty()) {
                setVectoren(e.getX(), e.getY());
                model.getBozelLijst().get(0).getBody().setTransform(r0.sub(eVec), 0.0f);
            }
            model.setMouseClicked(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (!model.isMouseClicked() && !model.getPauze()) {
                if ("yellow".equals(model.getBozelLijst().get(0).getType())) {
                    model.getBozelLijst().get(0).getBody().setLinearVelocity(model.getBozelLijst().get(0).getBody().getLinearVelocity().mul(3));
                    model.setMouseClicked(true);
                }
                if ("white".equals(model.getBozelLijst().get(0).getType())) {
                    model.getRayRaakPunt().clear();
                    model.getRayKracht().clear();
                    int bomLengte = 50;
                    Vec2 bom = new Vec2(model.getBozelLijst().get(0).getBody().getPosition().x,
                            model.getBozelLijst().get(0).getBody().getPosition().y);
                    model.setRayBegin(bom);
                    for (double i = 0; i < Math.PI * 2; i += (Math.PI / 36)) {
                        Vec2 eindPunt = bom.add(new Vec2((float) (bomLengte * Math.cos(i)), (float) (bomLengte * Math.sin(i))));
                        Explosie explosie = new Explosie(bom);
                        model.getWereld().raycast(explosie, bom, eindPunt);
                        explosie.applyForce();
                        if (explosie.getRaakPunt() != null) {
                            model.addRayRaakPunt(explosie.getRaakPunt());
                            model.addRayKracht(explosie.getKrachtVector());
                        }

                    }
                    //Als bozel slaapt, wordt hij verwijderd, dus:
                    model.getBozelLijst().get(0).getBody().setActive(false);
                    model.getBozelLijst().get(0).getBody().setAwake(false);
                }

            }
        } catch (NullPointerException ex) {
            //geen dichtste raakpunt gevonden
        } catch (IndexOutOfBoundsException ex) {
            //level nog niet ingeladen
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
