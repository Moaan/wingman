package wingman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 *
 * @author Moaan
 */
public class EnemySquad extends Wingman {

    private ArrayList<EnemyPlane> squadMembers;
    private int squadSize;

    EnemySquad(Image images[], Image bulletImage, int size, int startX, int startY, int xSpeed, int ySpeed) {
        squadSize = size;
        int unitStartX, unitStartY = startY;
        squadMembers = new ArrayList(squadSize);
        for (int i = 0; i < squadSize; i++) {
            unitStartX = startX + i * ((Wingman.borderX - startX) / squadSize);
            squadMembers.add(i, new EnemyPlane(images, bulletImage, unitStartX, unitStartY, xSpeed, ySpeed));
        }

    }

    public boolean updateAndCheckLife() {
        for (int i = 0; i < squadMembers.size(); i++) {
            if (squadMembers.get(i).updateAndCheckCollision()) {
                squadMembers.remove(i);
            }
        }
        if (squadMembers.isEmpty()) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g, ImageObserver obs) {
        for (int i = 0; i < squadMembers.size(); i++) {
            squadMembers.get(i).draw(g, obs);
        }
    }
}
