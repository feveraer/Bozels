/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wereld;

import java.awt.Color;

/**
 * Superklasse voor BozelType, TargetType en MateriaalType.
 * 
 * @author Frederic Everaert
 */
public class MijnType {
    
    protected float krachtdrempel;
    protected float sterkte;
    protected Color kleur;
    protected WereldModel model;
    private boolean breekbaar;

    public MijnType(WereldModel model) {
        this.model = model;
        breekbaar = true;
    }
    
    public float getKrachtdrempel(){
        return krachtdrempel;
    }
    
    public void setKrachtdrempel(float krachtdrempel){
        this.krachtdrempel = krachtdrempel;
        model.fireStateChanged();
    }
    
    public float getSterkte(){
        return sterkte;
    }
    
    public void setSterkte(float sterkte){
        this.sterkte = sterkte;
        model.fireStateChanged();
    }
    
    public Color getKleur(){
        return kleur;
    }
    
    public void setKleur(Color kleur){
        this.kleur = kleur;
        model.fireStateChanged();
    }
    
    public boolean isBreekbaar(){
        return breekbaar;
    }
    
    public void setBreekbaar(boolean b){
        breekbaar = b;
        model.fireStateChanged();
    }
}
