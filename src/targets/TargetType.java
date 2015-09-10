/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package targets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.FixtureDef;
import wereld.MijnType;
import wereld.WereldModel;

/**
 * Superklasse van TtGroot en TtKlein
 * Default waarden van TtKlein in constructor.
 * 
 * @author Frederic Everaert
 */
public class TargetType extends MijnType {

    private float dichtheid;
    private float restitutie;
    private float wrijving;
    protected float zijde;
    protected List<Target> targetLijst;
    protected FixtureDef fDef;
    
    public TargetType(WereldModel model) {
        super(model);
        dichtheid = 1.0f;
        restitutie = 0.1f;
        wrijving = 0.9f;
        kleur = Color.GREEN;
        zijde = 2.5f;
        krachtdrempel = 21.0f;
        sterkte = 30.0f;
        
        fDef = new FixtureDef();
        fDef.density = dichtheid;
        fDef.friction = wrijving;
        fDef.restitution = restitutie;
        
        targetLijst = new ArrayList<Target>();
    }
    
    public float getDichtheid(){
        return dichtheid;
    }
    
    public void setDichtheid(float dichtheid){
        this.dichtheid = dichtheid;
        fDef.density = dichtheid;
        for(Target target : targetLijst){
            target.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getRestitutie(){
        return restitutie;
    }
    
    public void setRestitutie(float restitutie){
        this.restitutie = restitutie;
        fDef.restitution = restitutie;
        for(Target target : targetLijst){
            target.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getWrijving(){
        return wrijving;
    }
    
    public void setWrijving(float wrijving){
        this.wrijving = wrijving;
        fDef.friction = wrijving;
        for(Target target : targetLijst){
            target.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getZijde(){
        return zijde;
    }
    
    public void setZijde(float zijde){
        this.zijde = zijde;
        model.fireStateChanged();
    }
    
    public FixtureDef getFixtureDef(){
        return fDef;
    }
    
    public void voegtoe(Target target){
        targetLijst.add(target);
    }
    
    public void verwijder(Target target){
        targetLijst.remove(target);
    }
    
}
