package wingman;

import CombatGame.IdleGameObject;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author Moaan
 */
public class Island extends Wingman implements IdleGameObject{

    Image img;
    int x, y, speed;
    Random gen;

    Island(Image img, int speed, Random gen) {
        this.img = img;
        x = Math.abs(gen.nextInt() % (borderX - 30));
        y = Math.abs(gen.nextInt() % (borderY - 30));;
        this.speed = speed;
        this.gen = gen;
    }

    public void update() {
        y += speed;
        if (y >= borderY) {
            y = -100;
            x = Math.abs(gen.nextInt() % (borderX - 30));
        }
    }

    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(img, x, y, obs);
    }
}