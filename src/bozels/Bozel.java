/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import gui.LevelVenster;
import java.awt.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jdom.Element;
import wereld.WereldModel;

/**
 * Object bozel.
 * 
 * @author Frederic Everaert
 */
public class Bozel {

    private float y;
    private float x;
    private String type;
    private BozelType bozelType;
    private int id;
    private Body body;
    private BodyDef bDef;
    private WereldModel model;

    public Bozel(Element bozelxml, WereldModel model) {

        this.model = model;
        
        String tmp = bozelxml.getAttributeValue("x");
        x = Float.parseFloat(tmp);
        tmp = bozelxml.getAttributeValue("y");
        y = Float.parseFloat(tmp);
        type = bozelxml.getAttributeValue("type");
        tmp = bozelxml.getAttributeValue("id");
        id = Integer.parseInt(tmp);
        
        //Bepaal welk "model" er bij dit object hoort.
        if ("red".equals(type)) {
            bozelType = model.getBtRood();
        } else if ("blue".equals(type)) {
            bozelType = model.getBtBlauw();
        } else if ("yellow".equals(type)) {
            bozelType = model.getBtGeel();
        } else if ("white".equals(type)) {
            bozelType = model.getBtWit();
        } else if ("black".equals(type)) {
            bozelType = model.getBtWit();
            type = "white";
        } else {
            throw new IllegalArgumentException();
        }

        //Voeg dit object toe aan lijst in dit "model".
        bozelType.voegtoe(this);

        bDef = new BodyDef();
        bDef.type = BodyType.DYNAMIC;
        bDef.position.set(x, y);
        body = model.getWereld().createBody(bDef);
        body.setActive(false);

        body.createFixture(bozelType.getFixtureDef());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void changeFixture() {
        body.destroyFixture(body.getFixtureList());
        body.createFixture(bozelType.getFixtureDef());
    }

    public Body getBody() {
        return body;
    }

    //Verander de fixture van dit object.
    public void changeBody() {
        try {
            model.getWereld().destroyBody(body);
            body = model.getWereld().createBody(bDef);
            body.createFixture(bozelType.getFixtureDef());
            body.setActive(false);
        } catch (NullPointerException ex) {
            System.out.println("changeBody bozel error");
        }
    }

    public BozelType getBozelType() {
        return bozelType;
    }

    //Zet de volgende bozel klaar. Zonder setTransform, want dat deed het soms niet.
    public void setActive() {
        bDef.position.set(model.getVuurPosX(), model.getVuurPosY());
        changeBody();
    }

    //Tekenfunctie voor dit object.
    public void draw(Graphics g, LevelVenster view) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Vec2 position = this.getBody().getPosition();
        
        if (position != null) {
            g2.translate(position.x * 7, view.getHeight() - position.y * 7);
            if (model.isToonSnelheid()) {
                g2.drawLine((int) this.getBody().getLocalCenter().x,
                        (int) this.getBody().getLocalCenter().y,
                        (int) (this.getBody().getLinearVelocityFromLocalPoint(this.getBody().getLocalCenter()).x * 7),
                        (int) (-this.getBody().getLinearVelocityFromLocalPoint(this.getBody().getLocalCenter()).y * 7));
            }
            if ("white".equals(this.getType())) {
                g2.rotate(-this.getBody().getAngle());
                g2.setColor(this.getBozelType().getKleur());
                g2.fillRect((int) (-this.getBozelType().getZijde() * 7 / 2),
                        (int) (-this.getBozelType().getZijde() * 7 / 2),
                        (int) (this.getBozelType().getZijde() * 7),
                        (int) (this.getBozelType().getZijde() * 7));
            } else if ("yellow".equals(this.getType())) {
                g2.rotate(-this.getBody().getAngle());
                g2.setColor(this.getBozelType().getKleur());
                g2.fillRect((int) (-this.getBozelType().getBreedte() * 7 / 2),
                        (int) (-this.getBozelType().getHoogte() * 7 / 2),
                        (int) (this.getBozelType().getBreedte() * 7),
                        (int) (this.getBozelType().getHoogte() * 7));
            } else if ("blue".equals(this.getType())) {
                g2.setColor(this.getBozelType().getKleur());
                g2.fillOval((int) (-this.getBozelType().getStraal() * 7),
                        (int) (-this.getBozelType().getStraal() * 7),
                        (int) (this.getBozelType().getStraal() * 2 * 7),
                        (int) (this.getBozelType().getStraal() * 2 * 7));
            } else if ("red".equals(this.getType())) {
                g2.rotate(-this.getBody().getAngle());
                int[] x = new int[3];
                int[] y = new int[3];
                x[0] = (int) 0;
                x[1] = (int) -(this.getBozelType().getZijde() * 7 / 2);
                x[2] = (int) (this.getBozelType().getZijde() * 7 / 2);
                y[0] = (int) -(this.getBozelType().getZijde() * 7 / 2 - 1);
                y[1] = (int) (this.getBozelType().getZijde() * 7 / 2 - 1);
                y[2] = (int) (this.getBozelType().getZijde() * 7 / 2 - 1);
                Polygon driehoek = new Polygon(x, y, 3);
                g2.setColor(this.getBozelType().getKleur());
                g2.drawPolygon(driehoek);
            }
            if (model.isToonZwaartepunt()) {
                int rood = this.getBozelType().getKleur().getRed();
                int groen = this.getBozelType().getKleur().getGreen();
                int blauw = this.getBozelType().getKleur().getBlue();
                if (rood == 128 && groen == 128 && blauw == 128) {
                    g2.setColor(Color.BLACK);
                } else {
                    g2.setColor(new Color(255 - rood, 255 - groen, 255 - blauw));
                }
                g2.fillOval((int) (this.getBody().getLocalCenter().x - 1), (int) (this.getBody().getLocalCenter().y - 1), 3, 3);
            }
        }
    }
}
