/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package targets;

import gui.LevelVenster;
import java.awt.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jdom.Element;
import wereld.WereldModel;
import wereld.WereldObject;

/**
 * Object target.
 * 
 * @author Frederic Everaert
 */
public class Target extends WereldObject {

    private float y;
    private float x;
    private String stringType;
    private TargetType targetType;
    private Body body;
    private BodyDef bDef;
    private WereldModel model;

    public Target(Element targetxml, WereldModel model) {
        this.model = model;
        String tmp = targetxml.getAttributeValue("x");
        x = Float.parseFloat(tmp);
        tmp = targetxml.getAttributeValue("y");
        y = Float.parseFloat(tmp);
        stringType = targetxml.getAttributeValue("type");
        if ("big".equals(stringType)) {
            targetType = model.getTtGroot();
        } else if ("small".equals(stringType)) {
            targetType = model.getTtKlein();
        } else {
            throw new IllegalArgumentException();
        }
        targetType.voegtoe(this);

        bDef = new BodyDef();
        bDef.type = BodyType.DYNAMIC;
        bDef.position.set(x, y);
        body = model.getWereld().createBody(bDef);
        body.setUserData(this);
        body.createFixture(targetType.getFixtureDef());

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setType(String type) {
        this.stringType = type;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public TargetType getType() {
        return targetType;
    }

    public void changeFixture() {
        body.destroyFixture(body.getFixtureList());
        body.createFixture(targetType.getFixtureDef());
    }

    @Override
    public void setSchade(float schade) {
        this.schade = schade;
        if (schade > (targetType.getSterkte() * this.getBody().getMass())){
            model.addGebrokenTarget(this);
        }
    }

//    public void print(){
//        System.out.println("y   : " + y);
//        System.out.println("x   : " + x);
//        System.out.println("type: " + Stringtype);
//        targettype.print();
//    }
    public void draw(Graphics g, LevelVenster view) {
        Graphics2D g2 = (Graphics2D) g.create();
        Vec2 position = this.getBody().getPosition();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (position != null) {
            g2.translate(position.x * 7, view.getHeight() - position.y * 7);
            if (model.isSlapend() && !this.getBody().isAwake()) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if (model.isToonSnelheid()) {
                g2.drawLine((int) this.getBody().getLocalCenter().x,
                        (int) this.getBody().getLocalCenter().y,
                        (int) (this.getBody().getLinearVelocityFromLocalPoint(this.getBody().getLocalCenter()).x * 7),
                        (int) (-this.getBody().getLinearVelocityFromLocalPoint(this.getBody().getLocalCenter()).y * 7));
            }
            g2.setColor(this.getType().getKleur());
            g2.rotate(-this.getBody().getAngle());
            g2.fillRect((int) (-this.getType().getZijde() * 7 / 2),
                    (int) (-this.getType().getZijde() * 7 / 2),
                    (int) (this.getType().getZijde() * 7),
                    (int) (this.getType().getZijde() * 7));
            if (model.isToonZwaartepunt()) {
                int rood = this.getType().getKleur().getRed();
                int groen = this.getType().getKleur().getGreen();
                int blauw = this.getType().getKleur().getBlue();
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
