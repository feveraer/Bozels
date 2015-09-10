/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hulpklassen;

import bozels.Bozel;
import java.util.Comparator;

/**
 * Bozels sorteren volgens ID.
 * 
 * @author Frederic Everaert
 */
public class CompareID implements Comparator<Bozel> {

    @Override
    public int compare(Bozel a, Bozel b) {
        if (a.getId() < b.getId()) {
            return -1;
        } else if (a.getId() > b.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
