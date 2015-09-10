package bozels;

import controllers.HerstartAction;
import controllers.PauzeAction;
import gui.GebruikersInterface;
import gui.Menu;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import wereld.WereldModel;

/**
 * Start het project op.
 * 
 * @author Frederic Everaert
 */
public class Bozels {

    private static final int PAUZE_ACTION = 0;
    private static final int HERSTART_ACTION = 1;
    private static final int MAX_ACTIONS = 2;
    private static HoofdFrame frame;
    
    public static void main(String[] args) {
//        try {
//            // Set System L&F
//            UIManager.setLookAndFeel(
//                    UIManager.getSystemLookAndFeelClassName());
//        } catch (UnsupportedLookAndFeelException e) {
//            // handle exception
//        } catch (ClassNotFoundException e) {
//            // handle exception
//        } catch (InstantiationException e) {
//            // handle exception
//        } catch (IllegalAccessException e) {
//            // handle exception
//        }
        setup();     
    }

    public static void setup() {
        frame = new HoofdFrame();
        frame.setTitle("Bozels - Frederic Everaert - Open een level om te beginnen");
        frame.setIconImage(new ImageIcon(Bozels.class.getResource("/images/bozel_icon.jpg")).getImage());
        frame.setResizable(false);
        WereldModel bozelModel = new WereldModel(frame);
        Action[] actions = new Action[MAX_ACTIONS];
        actions[PAUZE_ACTION] = new PauzeAction(bozelModel, "Pauzeren");
        actions[HERSTART_ACTION] = new HerstartAction(bozelModel, "Herstarten");
        GebruikersInterface gebruikersInterface = new GebruikersInterface(bozelModel, actions);
        frame.setContentPane(gebruikersInterface);
        frame.setJMenuBar(new Menu(bozelModel, actions));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

}
