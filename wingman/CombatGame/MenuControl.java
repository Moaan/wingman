package CombatGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Moaan
 */
public class MenuControl extends MouseAdapter {
    private GameEvents gE;
    
    public MenuControl(GameEvents gE) {
        this.gE = gE;
    }
    
    public void mouseClicked(MouseEvent m) {
        gE.setValue(m, 4, m.getX(), m.getY());
    }
    
    public void mouseEntered(MouseEvent e) {
        //gE.setValue(m, 4);//not needed now. can be added for styling the buttons.
    }
}
