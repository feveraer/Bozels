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
public class MtIjs extends MateriaalType{

    public MtIjs(WereldModel model) {
        super(model);
        dichtheid = 1.0f;
        fDef.density = 1.0f;
        restitutie = 0.0f;
        fDef.restitution = 0.0f;
        wrijving = 0.1f;
        fDef.friction = 0.1f;
        kleur = Color.CYAN.brighter();
        krachtdrempel = 60.0f;
        sterkte = 40.0f;
    }
    
}
