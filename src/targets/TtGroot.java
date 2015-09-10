/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package targets;

import java.awt.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class TtGroot extends TargetType {

    private PolygonShape vierkant;
    
    public TtGroot(WereldModel model) {
        super(model);
        kleur = Color.PINK;
        zijde = 4.0f;
        krachtdrempel = 15.0f;
        vierkant = new PolygonShape();
        vierkant.setAsBox(zijde/2, zijde/2);
        fDef.shape = vierkant;
    }
    

}
