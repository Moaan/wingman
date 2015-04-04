package wingman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author Moaan
 */
public class EnemyPlane extends Wingman {

    private Image images[];
    private Image bulletImage;
    private int x, y, sizeX, sizeY, xSpeed, ySpeed;
    private int imageStage = 0;

    EnemyPlane(Image images[], Image bulletImage, int startX, int startY, int xSpeed, int ySpeed) {
        this.images = images;
        this.bulletImage = bulletImage;
        x = startX;
        y = startY;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        sizeX = images[0].getWidth(null);
        sizeY = images[0].getHeight(null);
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }

    //check if collision with plane or bullet occured
    public boolean updateAndCheckCollision() {
        move();
        if ((x > borderX || x < 0) || (y > borderY || y < 0) || imageStage == 9) {
            return true;
        } else if (imageStage < 3) {
            if (generator.nextDouble() < 0.05) {
                Bullet b = new Bullet(bulletImage, x + 1, y, -3 * xSpeed, -3 * ySpeed);
                enemyBullets.add(b);
                b = null;
            }
            if (player1.collision(x, y, sizeX, sizeY)) {
                imageStage = 3;
                score += 5;
                explosionSound_1.play();
            } 
            if (numberOfPlayers > 1) {
                if (player2.collision(x, y, sizeX, sizeY)) {
                    imageStage = 3;
                    score += 5;
                    explosionSound_1.play();
                }
            } if(imageStage < 3) {
                for (int i = 0; i < alliedBullets.size(); i++) {
                    if (alliedBullets.get(i).collision(x, y, sizeX, sizeY)) {
                        imageStage = 3;
                        score += 5;
                        explosionSound_1.play();
                    }
                }
            }
        }
        return false;

    }

    public void move() {
        y += ySpeed;
        x += xSpeed;
    }

    public void draw(Graphics g, ImageObserver obs) {
        for (int i = 0; i < images.length; i++) {
            if (imageStage == i) {
                g.drawImage(images[i], x, y, obs);
                if (i == 2) {
                    imageStage = 0;
                } else {
                    imageStage++;
                }
                i = images.length;
            }
        }
    }
}
