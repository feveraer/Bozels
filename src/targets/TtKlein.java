/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package targets;

import org.jbox2d.collision.shapes.PolygonShape;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TtKlein extends TargetType {

    private PolygonShape vierkant;
    
    public TtKlein(WereldModel model) {
        super(model);
        vierkant = new PolygonShape();
        vierkant.setAsBox(zijde/2, zijde/2);
        fDef.shape = vierkant;
    }
    
}
