package CombatGame;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Observable;

/**
 *
 * @author Moaan
 */
public class GameEvents extends Observable {

    public int eventType; // 1 = user input,  2 = in-game events, 3 = timed events, 4 = menu user input
    public Object event;  //this is made an object so it can be int, string etc
    public int x, y; //used by MenuControl

    public void setValue(KeyEvent k, int keyEventType) {
        eventType = keyEventType;
        event = k;
        setChanged();
        notifyObservers(this);
    }
    
    public void setValue(MouseEvent m, int mouseEventType, int x, int y) {
        eventType = mouseEventType; //it's just 4...
        event = m;
        this.x = x;
        this.y = y;
        setChanged();
        notifyObservers(this);
    }

    public void setValue(String msg) {
        eventType = 2;
        event = msg;
        setChanged();
        notifyObservers(this);
    }

    public void setValue(int timedEvent) {
        eventType = 3;
        event = timedEvent;
        setChanged();
        notifyObservers(this);
    }
}
