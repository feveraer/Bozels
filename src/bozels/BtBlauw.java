/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import java.awt.Color;
import org.jbox2d.collision.shapes.CircleShape;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class BtBlauw extends BozelType{

    private CircleShape cirkel;
    
    public BtBlauw(WereldModel model) {
        super(model);
        dichtheid = 8.0f;
        fDef.density = 8.0f;
        restitutie = 0.7f;
        fDef.restitution = 0.7f;
        wrijving = 1.0f;
        fDef.friction = 1.0f;
        kleur = Color.BLUE;
        zijde = 0.0f;
        straal = 1.0f;
        cirkel = new CircleShape();
        cirkel.m_radius = straal;
        fDef.shape = cirkel;
    }
    
}
