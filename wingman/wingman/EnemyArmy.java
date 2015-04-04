package wingman;

import CombatGame.GameEvents;
import CombatGame.MobileGameObject;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Moaan
 */
public class EnemyArmy extends Wingman implements MobileGameObject {

    private ArrayList<EnemySquad> army;
    boolean isAttacking = false; //NOT NEEDED RIGHT?
    Image unitImages[][];
    Image bulletImages[];

    EnemyArmy(Image unitImages[][], Image bulletImages[]) {
        army = new ArrayList(30);
        this.unitImages = unitImages;
        this.bulletImages = bulletImages;
    }

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        for (int i = 0; i < army.size(); i++) {
            army.get(i).draw(g, obs);
        }
    }

    @Override
    public void move() {
        for (int i = 0; i < army.size(); i++) {
            if (army.get(i).updateAndCheckLife());
        }
    }

    @Override
    public void update(Observable obj, Object event) { //Observable obj is the object that is affected by the event!
        GameEvents gameE = (GameEvents) event;
        if (gameE.eventType == 3) {

            if ((int) gameE.event == 0) {
                army.add(new EnemySquad(unitImages[0], bulletImages[0], 3*numberOfPlayers, 20, 0, 0, 2));
            }
            if ((int) gameE.event == 1) {
                army.add(new EnemySquad(unitImages[1], bulletImages[1], 4*numberOfPlayers, 30, 0, 0, 2));
            }
            if ((int) gameE.event == 2) {
                army.add(new EnemySquad(unitImages[2], bulletImages[1], 4*numberOfPlayers, 20, 0, 0, 2));
            }
            if ((int) gameE.event == 3) {
                army.add(new EnemySquad(unitImages[3], bulletImages[1], 2*numberOfPlayers, 70, borderY, 0, -2));
            }
            if ((int) gameE.event == 4) {
                army.add(new EnemySquad(unitImages[3], bulletImages[1], 2*numberOfPlayers, 60, borderY, 0, -2));
            }
            if ((int) gameE.event == 5) {
                army.add(new EnemySquad(unitImages[3], bulletImages[1], 3*numberOfPlayers, 50, borderY, 0, -2));
            }
            if ((int) gameE.event == 6) {
                army.add(new EnemySquad(unitImages[2], bulletImages[1], 4*numberOfPlayers, 30, 0, 0, 2));
            }
            if ((int) gameE.event == 7) {
                army.add(new EnemySquad(unitImages[2], bulletImages[1], 4*numberOfPlayers, 40, 0, 0, 3));
            }
            if ((int) gameE.event == 8) {
                army.add(new EnemySquad(unitImages[2], bulletImages[1], 5*numberOfPlayers, 20, 0, 0, 3));
            } else {
                System.out.println("No more enemy squads");
            }

        }
    }
}
