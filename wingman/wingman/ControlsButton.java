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
public class ControlsButton extends Wingman implements MenuButton {

    private String label = "Controls";
    private Font f = new Font("impact", Font.PLAIN, 24);
    private Rectangle2D bounds;
    private int x, y;
    
    //displaying controls variables
     private String p1Label = "Player 1", p2Label = "Player 2";
     private Font controlsFont = new Font("helvetica", Font.BOLD, 16);
     private String up1 = "Up: W", up2 = "Up: \u2191";
     private String down1 = "Down: S", down2 = "Down: \u2193";
     private String left1 = "Left: A", left2 = "Left: \u2190";
     private String right1 = "Right: D", right2 = "Right: \u2192";
     private String fire1 = "Fire: SPACEBAR", fire2 = "Fire: Enter";
     

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
        y = (int) (height - bounds.getHeight())* 4/5;

        //Now, draw the centered, styled message
        g.drawString(label, (int) x, (int) y);
    }

    @Override
    public void update(Observable obj, Object event) {
        GameEvents gameE = (GameEvents) event;
        if(gameE.eventType == 4) {
            if((gameE.x > x) && (gameE.x < x + bounds.getWidth()) 
                    && (gameE.y < y) && (gameE.y >  y - bounds.getHeight())) {
                displayControls = true;
                gE.addObserver(eCtrlsB);
                gE.deleteObserver(this);
            }
            System.out.println("gameE.x: " + gameE.x + "\ngameE.y: " + gameE.y + "\n1 x: " + x + "\n1 y: " + y);
        }
    }
    
    public void drawControlsInfo(Graphics2D g, ImageObserver obs, int width, int height) {
        // Drawing the Score in an aligned box:
        g.setFont(f);
        g.setPaint(Color.red);

        //measure the label
        FontRenderContext context = g.getFontRenderContext(); //gets font characteristics specific to screen res.
        bounds = f.getStringBounds(label, context);
        
        int leftX = (int) (width - bounds.getWidth()) / 4;
        int leftY = (int) (height - bounds.getHeight())/8;
        g.drawString(p1Label, leftX, leftY);
        
        int rightX = (int) (width - bounds.getWidth()) *3/4;
        int rightY = (int) (height - bounds.getHeight())/8;
        g.drawString(p2Label, rightX, rightY);
        
        
        g.setFont(controlsFont);
        g.setPaint(Color.white);
        //drawing controls info
        leftY = rightY = (int) (height - bounds.getHeight())*2/8;
        g.drawString(up1, leftX, leftY);
        g.drawString(up2, rightX, rightY);
        
        leftY = rightY = (int) (height - bounds.getHeight())*3/8;
        g.drawString(down1, leftX, leftY);
        g.drawString(down2, rightX, rightY);
        
        leftY = rightY = (int) (height - bounds.getHeight())*4/8;
        g.drawString(left1, leftX, leftY);
        g.drawString(left2, rightX, rightY);
        
        leftY = rightY = (int) (height - bounds.getHeight())*5/8;
        g.drawString(right1, leftX, leftY);
        g.drawString(right2, rightX, rightY);
        
        leftY = rightY = (int) (height - bounds.getHeight())*6/8;
        g.drawString(fire1, leftX, leftY);
        g.drawString(fire2, rightX, rightY);
        
        
    }
}
