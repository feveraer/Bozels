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
public class MtMetaal extends MateriaalType{

    public MtMetaal(WereldModel model) {
        super(model);
        dichtheid = 3.0f;
        fDef.density = 3.0f;
        restitutie = 0.2f;
        fDef.restitution = 0.2f;
        wrijving = 0.3f;
        fDef.friction = 0.3f;
        kleur = Color.LIGHT_GRAY;
        krachtdrempel = 72.0f;
        sterkte = 208.0f;
    }
    
}
