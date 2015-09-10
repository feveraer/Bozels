/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decor;

import java.awt.Color;
import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class MtBeton extends MateriaalType{

    public MtBeton(WereldModel model) {
        super(model);
        dichtheid = 4.0f;
        fDef.density = 4.0f;
        restitutie = 0.0f;
        fDef.restitution = 0.0f;
        wrijving = 0.9f;
        fDef.friction = 0.9f;
        kleur = Color.DARK_GRAY;
        krachtdrempel = 80.0f;
        sterkte = 200.0f;
    }
    
}
