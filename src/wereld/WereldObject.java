/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wereld;

/**
 * Klasse om schade bij te houden of het nu om een block, ellips of target gaat.
 * 
 * @author Frederic Everaert
 */
public class WereldObject {

    protected float schade;
    protected MijnType type;
    
    public void setSchade(float schade){
        this.schade = schade;
    }
    
    public float getSchade(){
        return schade;
    }
    
    public MijnType getType() {
        return type;
    }
}
