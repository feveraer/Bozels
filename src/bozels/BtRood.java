/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class BtRood extends BozelType{

    private PolygonShape driehoek;
    
    public BtRood(WereldModel model) {
        super(model);
        driehoek = new PolygonShape();
        float z = (float) (zijde * Math.sqrt(3.0)/3.0);
        driehoek.set(new Vec2[]{
            new Vec2(0,z),
            new Vec2(-zijde/2,-z/2),
            new Vec2(zijde/2,-z/2)
        }, 3);
        fDef.shape = driehoek;
    }
    
}
