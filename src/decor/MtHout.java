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
public class MtHout extends MateriaalType{

    public MtHout(WereldModel model) {
        super(model);
        dichtheid = 0.75f;
        fDef.density = 0.75f;
        restitutie = 0.3f;
        fDef.restitution = 0.3f;
        wrijving = 0.8f;
        fDef.friction = 0.8f;
        kleur = new Color(128,64,0);
        krachtdrempel = 40.0f;
        sterkte = 48.0f;
    }
    
}
