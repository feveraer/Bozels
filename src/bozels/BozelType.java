/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bozels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.FixtureDef;
import wereld.MijnType;
import wereld.WereldModel;

/**
 * Superklasse van BtBlauw, BtRood, BtBlauw en BtGeel.
 * Defaultwaarden in constructor voor BtRood.
 * 
 * @author Frederic Everaert
 */
public class BozelType extends MijnType {
    
    protected float dichtheid;
    protected float restitutie;
    protected float wrijving;
    protected float zijde;
    protected float straal;
    protected float breedte;
    protected float hoogte;
    
    protected List<Bozel> bozelLijst;
    
    protected FixtureDef fDef;
    
    public BozelType(WereldModel model) {
        super(model);
        dichtheid = 10.0f;
        restitutie = 0.3f;
        wrijving = 0.9f;
        kleur = Color.RED;
        zijde = 2.0f;

        fDef = new FixtureDef();
        fDef.density = dichtheid;
        fDef.friction = wrijving;
        fDef.restitution = restitutie;
        
        bozelLijst = new ArrayList<Bozel>();
    }
    
    public float getDichtheid(){
        return dichtheid;
    }
    
    public void setDichtheid(float dichtheid){
        this.dichtheid = dichtheid;
        fDef.density = dichtheid;
        for(Bozel bozel : bozelLijst){
            bozel.changeFixture();
        }
        model.fireStateChanged();
        
    }
    
    public float getRestitutie(){
        return restitutie;
    }
    
    public void setRestitutie(float restitutie){
        this.restitutie = restitutie;
        fDef.restitution = restitutie;
        for(Bozel bozel : bozelLijst){
            bozel.changeFixture();
        }
        model.fireStateChanged();
    }
    
    public float getWrijving(){
        return wrijving;
    }
    
    public void setWrijving(float wrijving){
        this.wrijving = wrijving;
        fDef.friction = wrijving;
        for(Bozel bozel : bozelLijst){
            bozel.changeFixture();
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
    
    public float getStraal(){
        return straal;
    }
    
    public void setStraal(float straal){
        this.straal = straal;
        model.fireStateChanged();
    }
    
    public float getBreedte(){
        return breedte;
    }
    
    public void setBreedte(float breedte){
        this.breedte = breedte;
        model.fireStateChanged();
    }
    
    public float getHoogte(){
        return hoogte;
    }
    
    public void setHoogte(float hoogte){
        this.hoogte = hoogte;
        model.fireStateChanged();
    }
    
    public FixtureDef getFixtureDef(){
        return fDef;
    }
    
    public void voegtoe(Bozel bozel){
        bozelLijst.add(bozel);
    }
    
    public void verwijder(Bozel bozel){
        bozelLijst.remove(bozel);
    }
    
    public void verwijder(int id){
        for(int i=0; i < bozelLijst.size(); i++){
            if (bozelLijst.get(i).getId() == id){
                bozelLijst.remove(i);
            }
        }
    }
}
