/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decor;

import wereld.WereldModel;

/**
 *
 * @author Frederic Everaert
 */
public class MtVast extends MateriaalType {
    
    public MtVast(WereldModel model) {
        super(model);
        vast = true;
        super.setBreekbaar(false);
    }
    
}
