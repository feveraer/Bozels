/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hulpklassen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * Kleuricoontje nodig voor te gebruiken in een JList.
 * 
 * @author Frederic Everaert
 */
public class ColorIcon implements Icon {
    private final static int HEIGHT = 14;
    private final static int WIDTH = 14;

    private Color color;

    public ColorIcon(Color color)
    {
        this.color = color;
    }

    @Override
    public int getIconHeight()
    {
        return HEIGHT;
    }

    @Override
    public int getIconWidth()
    {
        return WIDTH;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        g.setColor(color);
        g.fillRect(x, y, WIDTH - 1, HEIGHT - 1);

        
    }
}
