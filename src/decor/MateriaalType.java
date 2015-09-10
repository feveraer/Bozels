/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.FixtureDef;
import wereld.MijnType;
import wereld.WereldModel;

/**
 * Superklasse van MtHout, MtVast, MtBeton, MtIjs en MtMetaal.
 * Defaultwaarden in constructor voor MtVast.
 * 
 * @author Frederic Everaert
 */
public class MateriaalType extends MijnType {

    protected float dichtheid;
    protected float restitutie;
    protected float wrijving;
    protected List<Decor> decorLijst;
    protected FixtureDef fDef;
    protected boolean vast;
    
    public MateriaalType(WereldModel model) {
        super(model);
        dichtheid = 0.0f;
        restitutie = 0.1f;
        wrijving = 1.0f;
        kleur = Color.BLACK;
        krachtdrempel = 0f;
        sterkte = 0f;
        
        fDef = new FixtureDef();
        fDef.density = dichtheid;
        fDef.friction = wrijving;
        fDef.restitution = restitutie;
        
        decorLijst = new ArrayList<Decor>();
        
        vast = false;
    }
    
    public float getDichtheid(){
        return dichtheid;
    }
    
    public void setDichtheid(float dichtheid){

        this.dichtheid = dichtheid;
        fDef.density = dichtheid;
        for(Decor decor : decorLijst){
            decor.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getRestitutie(){
        return restitutie;
    }
    
    public void setRestitutie(float restitutie){
        this.restitutie = restitutie;
        fDef.restitution = restitutie;
        for(Decor decor : decorLijst){
            decor.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getWrijving(){
        return wrijving;
    }
    
    public void setWrijving(float wrijving){
        this.wrijving = wrijving;
        fDef.friction = wrijving;
        for(Decor decor : decorLijst){
            decor.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public FixtureDef getFixtureDef(){
        return fDef;
    }
    
    public void voegtoe(Decor decor){
        decorLijst.add(decor);
    }
    
    public void verwijder(Decor decor){
        decorLijst.remove(decor);
    }

}
