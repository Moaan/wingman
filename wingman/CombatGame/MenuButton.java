package CombatGame;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Moaan
 */
public interface MenuButton extends Observer {
    public void draw(Graphics2D g, ImageObserver obs, int width, int height);
    @Override
    public void update(Observable obj, Object arg);    
}
