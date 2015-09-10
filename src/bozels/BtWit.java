/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import java.awt.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class BtWit extends BozelType{

    private PolygonShape vierkant;
    
    public BtWit(WereldModel model) {
        super(model);
        dichtheid = 5.0f;
        fDef.density = 5.0f;
        restitutie = 0.0f;
        fDef.restitution = 0.0f;
        wrijving = 0.2f;
        fDef.friction = 0.2f;
        kleur = Color.GRAY;
        vierkant = new PolygonShape();
        vierkant.setAsBox(zijde/2, zijde/2);
        fDef.shape = vierkant;
    }
}
