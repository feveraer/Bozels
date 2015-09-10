/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decor;

import gui.LevelVenster;
import java.awt.*;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jdom.Element;
import wereld.WereldModel;

/**
 *Object block.
 * 
 * @author Frederic Everaert
 */
public class Block extends Decor {

    private PolygonShape block;
    private WereldModel model;

    public Block(Element decorxml, WereldModel model) {
        super(decorxml, model);
        this.model = model;
        block = new PolygonShape();
        block.setAsBox(width / 2, height / 2);

        materiaalType.getFixtureDef().shape = block;
        body.createFixture(materiaalType.getFixtureDef());
    }

    public PolygonShape getShape() {
        return block;
    }

    @Override
    public void setSchade(float schade) {
        this.schade = schade;
        if (schade > (materiaalType.getSterkte() * this.getBody().getMass())) {
            model.addGebrokenBlock(this);
        }
    }

    @Override
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
            g2.fillRect((int) (-this.getWidth() * 7 / 2),
                    (int) (-this.getHeight() * 7 / 2),
                    (int) (this.getWidth() * 7),
                    (int) (this.getHeight() * 7));
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
