/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wereld;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Raycastcallback zoals in de opgave.
 * Enkele extra methoden om raakpunten, krachtvectoren op te kunnen halen
 * en daarna te kunnen tekenen.
 * 
 * @author Frederic Everaert
 */
public class Explosie implements RayCastCallback {

    private Vec2 bom;
    private Vec2 raakpunt;
    private Fixture dichtste;
    private float minf = 1.0f;
    private final float maxKracht = 20000f;

    public Explosie(Vec2 bom) {
        this.bom = bom.clone();
    }

    @Override
    public float reportFixture(Fixture fix, Vec2 p, Vec2 n, float f) {
        if (f < minf) {
            minf = f;
            dichtste = fix;
            raakpunt = p.clone();
        }

        return f;

    }

    public void applyForce() {
        if (raakpunt != null) {
            Vec2 fRicht = new Vec2(raakpunt.sub(bom));

            float afst = fRicht.normalize();

            dichtste.getBody().applyForce(fRicht.mul(maxKracht / (afst + 1)), raakpunt);
        }
    }
    
    public Vec2 getKrachtVector(){
        Vec2 krachtv = null;
        if (raakpunt != null) {
            Vec2 fRicht = new Vec2(raakpunt.sub(bom));
            float afst = fRicht.normalize();
            float kracht = maxKracht / (afst + 1);
            krachtv = fRicht.mul(kracht);
        }
        return krachtv;
    }
    
    public Vec2 getRaakPunt(){
        return raakpunt;
    }
    
    public float getMaxKracht(){
        return maxKracht;
    }
}
