
package wereld;

import bozels.*;
import decor.*;
import hulpklassen.CompareID;
import java.awt.Image;
import java.io.File;
import java.util.*;
import javax.swing.ImageIcon;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import targets.Target;
import targets.TargetType;
import targets.TtGroot;
import targets.TtKlein;

/**
 * Heel groot model.
 * 
 * @author Frederic Everaert
 */
public class WereldModel extends Model {

    private String level;
    private String dir;
    private HoofdFrame hoofdFrame;
    private Image achtergrond;
    private File levelBestand;
    
    //Kleinere "modellen" voor objecten
    private MtVast mtVast;
    private MtBeton mtBeton;
    private MtHout mtHout;
    private MtMetaal mtMetaal;
    private MtIjs mtIjs;
    private TtKlein ttKlein;
    private TtGroot ttGroot;
    private BtBlauw btBlauw;
    private BtGeel btGeel;
    private BtRood btRood;
    private BtWit btWit;
    
    private boolean simulatieLoopt;
    private boolean released;
    private boolean dragged;
    private boolean clicked;
    private boolean toonSnelheid;
    private boolean toonRays;
    private boolean toonZwaartepunt;
    private boolean slapend;
    private boolean herstartEnabled;
    private boolean pauze;
    
    private float zwaartekracht;
    private Vec2 zkVector;
    private World wereld;
    private Body grond;
    private float lanceerkracht;
    private float tijdstap;
    private int wereldSnelheid;
    private List<Bozel> bozels;
    private List<Decor> blocks;
    private List<Target> targets;
    private List<Decor> ellipsen;
    
    //Sets die gebroken objecten bij zullen houden
    private Set<Decor> gebrokenBlocks;
    private Set<Decor> gebrokenEllipsen;
    private Set<Target> gebrokenTargets;
    
    private float vuurPosX;
    private float vuurPosY;
    private List<Vec2> raysBegin;
    private List<Vec2> rayKrachten;
    private Vec2 rayBegin;

    public WereldModel(HoofdFrame hoofdFrame) {
        this.hoofdFrame = hoofdFrame;
        dir = "etc/levels";
        pauze = false;
        herstartEnabled = true;
        level = "";
        
        raysBegin = new ArrayList<Vec2>();
        rayKrachten = new ArrayList<Vec2>();
        
        gebrokenBlocks = new HashSet<Decor>();
        gebrokenEllipsen = new HashSet<Decor>();
        gebrokenTargets = new HashSet<Target>();

        mtVast = new MtVast(this);
        mtBeton = new MtBeton(this);
        mtHout = new MtHout(this);
        mtMetaal = new MtMetaal(this);
        mtIjs = new MtIjs(this);
        ttKlein = new TtKlein(this);
        ttGroot = new TtGroot(this);
        btBlauw = new BtBlauw(this);
        btGeel = new BtGeel(this);
        btRood = new BtRood(this);
        btWit = new BtWit(this);
        bozels = new ArrayList<Bozel>();
        blocks = new ArrayList<Decor>();
        ellipsen = new ArrayList<Decor>();
        targets = new ArrayList<Target>();

        //Wereld aanmaken
        zwaartekracht = -9.81f;
        tijdstap = 1.0f / 60.0f;

        wereldSnelheid = 8;
        zkVector = new Vec2(0f, zwaartekracht);
        lanceerkracht = 35f;

        wereld = new World(zkVector, true);
        BodyDef grondBD = new BodyDef();
        grondBD.position.set(new Vec2(0.0f, -0.5f));
        grondBD.type = BodyType.STATIC;

        grond = wereld.createBody(grondBD);

        PolygonShape grondShape = new PolygonShape();
        grondShape.setAsBox(Short.MAX_VALUE, 0.5f);

        FixtureDef grondFD = new FixtureDef();
        grondFD.friction = 1f;
        grondFD.shape = grondShape;

        grond.createFixture(grondFD);

        //SchadeLuisteraar
        wereld.setContactListener(new ContactListener() {

            protected boolean show;
            protected Fixture fixA;
            protected Fixture fixB;
            protected float kracht;

            @Override
            public void beginContact(Contact cnt) {
                show = true;
                fixA = cnt.getFixtureA();
                fixB = cnt.getFixtureB();
            }

            @Override
            public void endContact(Contact cnt) {
            }

            @Override
            public void preSolve(Contact cnt, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact cnt, ContactImpulse imp) {
                if (show) {
                    WereldObject wo1 = (WereldObject) fixA.getBody().getUserData();
                    WereldObject wo2 = (WereldObject) fixB.getBody().getUserData();
                    kracht = imp.normalImpulses[0] + imp.normalImpulses[1];
                    if (wo1 != null && kracht > wo1.getType().getKrachtdrempel()) {
                        if (wo1.getType().isBreekbaar()) {
                            wo1.setSchade(wo1.getSchade() + kracht);
                            show = false;
                        }
                    }
                    if (wo2 != null && kracht > wo2.getType().getKrachtdrempel()) {
                        if (wo2.getType().isBreekbaar()) {
                            wo2.setSchade(wo2.getSchade() + kracht);
                            show = false;
                        }
                    }
                }
            }
        });
    }

    public boolean getPauze() {
        return pauze;
    }

    //Als pauze true is, dan moet simulatieLoopt op false.
    public void setPauze(boolean pauze) {
        this.pauze = pauze;
        simulatieLoopt = !pauze;
        fireStateChanged();
    }
    
    //Om tijdelijk de herstartactie te disabelen (veiligheid).
    public boolean isHerstartEnabled(){
        return herstartEnabled;
    }
    public void setHerstartEnabled(boolean e){
        herstartEnabled = e;
        fireStateChanged();
    }

    //Maak lijsten, en huidig level leeg. Lees nieuw level in en laat de simulatie
    //opnieuw starten.
    public void herstart(String level) {
        if (!level.isEmpty()) {
            raysBegin.clear();
            rayKrachten.clear();
            gebrokenBlocks.clear();
            gebrokenEllipsen.clear();
            gebrokenTargets.clear();
            stopSimulatie();
            clearLevel();
            levelBestand = new File(level);
            hoofdFrame.setTitle("Bozels - Frederic Everaert - Level " + levelBestand.getName());
            //setAchtergrond(new ImageIcon(Bozels.class.getResource("/images/bozels_achtergrond.jpeg")).getImage());
            this.level = level;
            setLevel();
            dragged = false;
            released = false;
            clicked = true;
            startSimulatie();
        }
        pauze = false;
        fireStateChanged();
    }

    public String getLevel() {
        return level;
    }

    //Level wordt ingelezen met JDOM.
    public void setLevel() {
        try {    
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(levelBestand);
            
            Element root = doc.getRootElement();
            List list = root.getChildren("bozel");

            for (Object obj : list) {
                bozels.add(new Bozel((Element) obj, this));
            }

            sortBozelsID(bozels);
            vuurPosX = bozels.get(0).getBody().getPosition().x;
            vuurPosY = bozels.get(0).getBody().getPosition().y + 7;
            bozels.get(0).setActive();

            list = root.getChildren("block");
            for (Object obj : list) {
                blocks.add(new Block((Element) obj, this));
            }

            list = root.getChildren("ellipse");
            for (Object obj : list) {
                ellipsen.add(new Ellips((Element) obj, this));
            }

            list = root.getChildren("target");
            for (Object obj : list) {
                targets.add(new Target((Element) obj, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fireStateChanged();
    }
    
    public float getZwaartekracht() {
        return zwaartekracht;
    }

    public void setZwaartekracht(float zwaartekracht) {
        this.zwaartekracht = zwaartekracht;
        zkVector.set(0f, zwaartekracht);
        wereld.setGravity(zkVector);
        for (Body b = wereld.getBodyList(); b != null; b = b.getNext()) {
            b.setAwake(true);
        }
        fireStateChanged();
    }

    public float getLanceerkracht() {
        return lanceerkracht;
    }

    public void setLanceerkracht(float lanceerkracht) {
        this.lanceerkracht = lanceerkracht;
        fireStateChanged();
    }

    public float getTijdstap() {
        return tijdstap;
    }

    public void setTijdstap(float ts) {
        tijdstap = ts;
        fireStateChanged();
    }

    public int getWereldSnelheid() {
        return wereldSnelheid;
    }

    public void setWereldSnelheid(int ws) {
        wereldSnelheid = ws;
        fireStateChanged();
    }

    public World getWereld() {
        return wereld;
    }

    //Bodies van objecten vernietigen, als ook lijst met die objecten en de lijst
    //met de fixtures.
    public void clearLevel() {
        for (Bozel bozel : bozels) {
            wereld.destroyBody(bozel.getBody());
            bozel.getBozelType().verwijder(bozel);
        }
        bozels.clear();
        for (Decor block : blocks) {
            wereld.destroyBody(block.getBody());
            block.getType().verwijder(block);
        }
        blocks.clear();
        for (Decor ellips : ellipsen) {
            wereld.destroyBody(ellips.getBody());
            ellips.getType().verwijder(ellips);
        }
        ellipsen.clear();
        for (Target target : targets) {
            wereld.destroyBody(target.getBody());
            target.getType().verwijder(target);
        }
        targets.clear();
        fireStateChanged();
    }

    public String getDir() {
        return dir;
    }

    public TargetType getTtKlein() {
        return ttKlein;
    }

    public TargetType getTtGroot() {
        return ttGroot;
    }

    public MateriaalType getMtVast() {
        return mtVast;
    }

    public MateriaalType getMtBeton() {
        return mtBeton;
    }

    public MateriaalType getMtHout() {
        return mtHout;
    }

    public MateriaalType getMtMetaal() {
        return mtMetaal;
    }

    public MateriaalType getMtIjs() {
        return mtIjs;
    }

    public BozelType getBtRood() {
        return btRood;
    }

    public BozelType getBtBlauw() {
        return btBlauw;
    }

    public BozelType getBtWit() {
        return btWit;
    }

    public BozelType getBtGeel() {
        return btGeel;
    }

    public List<Bozel> getBozelLijst() {
        return bozels;
    }

    public List<Target> getTargetLijst() {
        return targets;
    }

    public List<Decor> getBlockLijst() {
        return blocks;
    }

    public List<Decor> getEllipsenLijst() {
        return ellipsen;
    }

    public void stopSimulatie() {
        simulatieLoopt = false;
    }

    public void startSimulatie() {
        simulatieLoopt = true;
    }

    public boolean getSimulatieLoopt() {
        return simulatieLoopt;
    }

    public void sortBozelsID(List<Bozel> bozels) {
        Collections.sort(bozels, new CompareID());
    }

    public float getVuurPosX() {
        return vuurPosX;
    }

    public float getVuurPosY() {
        return vuurPosY;
    }
    
    public boolean isSlapend(){
        return slapend;
    }
    
    public void setSlapend(boolean s){
        slapend = s;
        fireStateChanged();
    }
    
    public boolean isToonSnelheid(){
        return toonSnelheid;
    }
    
    public void setToonSnelheid(boolean ts){
        toonSnelheid = ts;
        fireStateChanged();
    }
    
    public boolean isToonZwaartepunt(){
        return toonZwaartepunt;
    }
    
    public void setToonZwaartepunt(boolean tz){
        toonZwaartepunt = tz;
        fireStateChanged();
    }
    
    public boolean isToonRays(){
        return toonRays;
    }
    
    public void setToonRays(boolean tr){
        toonRays = tr;
        fireStateChanged();
    }
    
    public void setRayBegin(Vec2 bom) {
        rayBegin = bom;
    }
    
    public Vec2 getRayBegin(){
        return rayBegin;
    }
    
    public void addRayRaakPunt(Vec2 ray){
        raysBegin.add(ray);
    }

    public List<Vec2> getRayRaakPunt(){
        return raysBegin;
    }
    
    public void addRayKracht(Vec2 kracht){
        rayKrachten.add(kracht);
    }
    
    public List<Vec2> getRayKracht(){
        return rayKrachten;
    }
    
    public Set<Decor> getGebrokenBlocks(){
        return gebrokenBlocks;
    }
    
    public void addGebrokenBlock(Decor block){
        gebrokenBlocks.add(block);
    }
    
    public Set<Decor> getGebrokenEllipsen(){
        return gebrokenEllipsen;
    }
    
    public void addGebrokenEllips(Decor ellips){
        gebrokenEllipsen.add(ellips);
    }
    
    public Set<Target> getGebrokenTargets(){
        return gebrokenTargets;
    }
    
    public void addGebrokenTarget(Target target){
        gebrokenTargets.add(target);
    }
    
    public boolean isMouseReleased() {
        return released;
    }

    public void setMouseReleased(boolean r) {
        released = r;
    }

    public boolean isMouseDragged() {
        return dragged;
    }

    public void setMouseDragged(boolean d) {
        dragged = d;
    }

    public boolean isMouseClicked() {
        return clicked;
    }

    public void setMouseClicked(boolean c) {
        clicked = c;
    }
    
    public Image getAchtergrond(){
        return achtergrond;
    }
    
    public void setAchtergrond(Image a){
        achtergrond = a;
        fireStateChanged();
    }
}
