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
public class BtGeel extends BozelType{

    private PolygonShape rechthoek;
    
    public BtGeel(WereldModel model) {
        super(model);
        restitutie = 0.1f;
        fDef.restitution = 0.1f;
        kleur = Color.YELLOW;
        zijde = 0.0f;
        hoogte = 1.0f;
        breedte = 2.0f;
        rechthoek = new PolygonShape();
        rechthoek.setAsBox(breedte/2, hoogte/2);
        fDef.shape = rechthoek;
    }
  
}
