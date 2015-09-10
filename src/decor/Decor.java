/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decor;

import gui.LevelVenster;
import java.awt.Graphics;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jdom.Element;
import wereld.WereldModel;
import wereld.WereldObject;

/**
 * Superklasse van Block en Ellips.
 * 
 * @author Frederic Everaert
 */
public class Decor extends WereldObject {

    protected float height;
    protected float width;
    protected double angleInRad;
    protected String materiaal;
    protected float y;
    protected float x;
    protected Body body;
    protected BodyDef bDef;
    protected MateriaalType materiaalType;
    private WereldModel model;

    public Decor(Element decorxml, WereldModel model) {

        this.model = model;

        String tmp = decorxml.getAttributeValue("height");
        height = Float.parseFloat(tmp);
        tmp = decorxml.getAttributeValue("width");
        width = Float.parseFloat(tmp);
        tmp = decorxml.getAttributeValue("angle");
        angleInRad = Math.toRadians(Float.parseFloat(tmp));
        materiaal = decorxml.getAttributeValue("material");
        tmp = decorxml.getAttributeValue("x");
        x = Float.parseFloat(tmp);
        tmp = decorxml.getAttributeValue("y");
        y = Float.parseFloat(tmp);

        if ("solid".equals(materiaal)) {
            materiaalType = model.getMtVast();
        } else if ("wood".equals(materiaal)) {
            materiaalType = model.getMtHout();
        } else if ("stone".equals(materiaal)) {
            materiaalType = model.getMtBeton();
        } else if ("metal".equals(materiaal)) {
            materiaalType = model.getMtMetaal();
        } else if ("ice".equals(materiaal)) {
            materiaalType = model.getMtIjs();
        } else {
            throw new IllegalArgumentException();
        }
        materiaalType.voegtoe(this);

        bDef = new BodyDef();
        bDef.angle = -(float) angleInRad;
        if (materiaalType.vast) {
            bDef.type = BodyType.STATIC;
        } else {
            bDef.type = BodyType.DYNAMIC;
        }
        bDef.position.set(x, y);
        body = model.getWereld().createBody(bDef);

        body.setUserData(this);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public double getAngleInRad() {
        return angleInRad;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public String getMaterial() {
        return materiaal;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public MateriaalType getType() {
        return materiaalType;
    }

    public void changeFixture() {
        try {
            Shape shape = body.getFixtureList().getShape();
            body.destroyFixture(body.getFixtureList());
            materiaalType.getFixtureDef().shape = shape;
            body.createFixture(materiaalType.getFixtureDef());
        } catch (NullPointerException ex) {
            System.out.println("changeFixture decor error");
        }
    }

    public void draw(Graphics g, LevelVenster view) {
    }
}
