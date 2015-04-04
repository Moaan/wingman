/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wingman;

import CombatGame.GameEvents;
import CombatGame.MenuButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.Observable;
import static wingman.Wingman.gameStart;

/**
 *
 * @author Moaan
 */
public class TwoPlayerButton extends Wingman implements MenuButton {

    private String label = "Two Player Mode";
    private Font f = new Font("impact", Font.PLAIN, 24);
    private Rectangle2D bounds;
    private int x, y;

    @Override
    public void draw(Graphics2D g, ImageObserver obs, int width, int height) {
        // Drawing the Score in an aligned box:
        g.setFont(f);
        g.setPaint(Color.red);

        //measure the label
        FontRenderContext context = g.getFontRenderContext(); //gets font characteristics specific to screen res.
        bounds = f.getStringBounds(label, context);
        
        //set (x,y) = top left corner of text rectangle
        x = (int) (width - bounds.getWidth()) / 2;
        y = (int) (height - bounds.getHeight())* 3/5;

        //Now, draw the centered, styled message
        g.drawString(label, (int) x, (int) y);
    }

    @Override
    public void update(Observable obj, Object event) {
        GameEvents gameE = (GameEvents) event;
        if(gameE.eventType == 4) {
            if((gameE.x > x) && (gameE.x < x + bounds.getWidth()) 
                    && (gameE.y < y) && (gameE.y >  y - bounds.getHeight())) {
                gameStart = true;
                initializePlayers(2);
            }
            System.out.println("gameE.x: " + gameE.x + "\ngameE.y: " + gameE.y + "\n2 x: " + x + "\n2 y: " + y);
        }
    }
}
