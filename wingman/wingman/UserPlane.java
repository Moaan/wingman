package wingman;

import CombatGame.GameEvents;
import CombatGame.MobileGameObject;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Observable;

/**
 *
 * @author Moaan
 */
public class UserPlane extends Wingman implements MobileGameObject {

    private Image images[];
    private Image bulletImages[];
    private int imageStage = 0;
    private final int MAX_HP = 4, speed = 6;
    private int hp, x, y, playerNumber, lives = 3;
    private int freezeFlag = 18; //Is used to cause a delay in the plane spawning after collision
    ImageObserver obs;
    int xSpeed = 0, ySpeed = 0;
    private String controlSet;
    Image[] healthImages;

    UserPlane(Image images[], Image bulletImages[], Image healthInfo[],int x, int y, int playerNumber) {
        this.images = images;
        this.bulletImages = bulletImages;
        this.x = x;
        this.y = y;
        hp = MAX_HP;
        this.playerNumber = playerNumber;
        this.controlSet = playerNumber + "";
        healthImages = healthInfo;
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        if (hp > 0) { //nothing needs to be changed.
            if (imageStage == 0) {
                g.drawImage(images[0], x, y, obs);
                imageStage++;
            } else if (imageStage == 1) {
                g.drawImage(images[1], x, y, obs);
                imageStage++;
            } else if (imageStage == 2) {
                g.drawImage(images[2], x, y, obs);
                imageStage = 0;
            }
            System.out.println("hp = " + hp + " lives = " + lives);
        } else if (hp == 0 && lives > 0) { //player briefly invisible after taking a collision
            if (freezeFlag == 0) {
                freezeFlag = 18;
                lives--;
                hp = MAX_HP;
            } else if (freezeFlag == 12 || freezeFlag == 6) {
                g.drawImage(images[1], x, y, obs);
            }
            freezeFlag--;
        } else if (hp == 0 && lives == 0 && imageStage < 3) {
            imageStage = 3; // begin exploding
            explosionSound_2.play();
        } else if (imageStage == 3) {
            g.drawImage(images[3], x, y, obs);
            imageStage++;
        } else if (imageStage == 4) {
            g.drawImage(images[4], x, y, obs);
            imageStage++;
        } else if (imageStage == 5) {
            g.drawImage(images[5], x, y, obs);
            imageStage++;
        } else if (imageStage == 6) {
            g.drawImage(images[6], x, y, obs);
            imageStage++;
        } else if (imageStage == 7) {
            g.drawImage(images[7], x, y, obs);
            imageStage++;
        } else if (imageStage == 8) {
            g.drawImage(images[8], x, y, obs);
            imageStage++;
        } else if (imageStage == 9) {
            g.drawImage(images[9], x, y, obs);
            imageStage++;
        } else if (imageStage == 10) {
            gameIsOver = true;
        }
        //draw health informartion
        g.drawImage(healthImages[4-hp], 10 + (playerNumber-1)*(borderX-140), borderY-70, obs);
        int lifeDisplay = lives;
        while(lifeDisplay > 0) {
            g.drawImage(healthImages[5], 30*lifeDisplay + (playerNumber-1)*(borderX-140),borderY-105, obs);
            lifeDisplay--;
        }
    }

    public boolean collision(int x, int y, int w, int h) {
        if ((y + h > this.y) && (y < this.y + images[0].getHeight(this.obs))) {
            if ((x + w > this.x) && (x < this.x + images[0].getWidth(this.obs))) {
                hp = 0;
                return true;
            }
        }
        return false;
    }

    public void move() {
        if ((x + xSpeed < borderX - 70) && (x + xSpeed > 0)) {
            x += xSpeed;
        }
        if ((y + ySpeed < borderY - 88) && (y + ySpeed > 0)) {
            y += ySpeed;
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).collision(x, y, images[0].getWidth(null), images[0].getHeight(null))) {
                if (hp >= 1) {
                    hp--; 
                }
            }
        }
    }

    public void update(Observable obj, Object event) {
        GameEvents gameE = (GameEvents) event;
        if (gameE.eventType <= 1) {
            KeyEvent e = (KeyEvent) gameE.event;
            String action = controls.get(e.getKeyCode());
            if (action.equals("left" + controlSet)) {
                xSpeed = -1 * speed * gameE.eventType;
            } else if (action.equals("right" + controlSet)) {
                xSpeed = speed * gameE.eventType;
            } else if (action.equals("up" + controlSet)) {
                ySpeed = -1 * speed * gameE.eventType;
            } else if (action.equals("down" + controlSet)) {
                ySpeed = speed * gameE.eventType;
            } else if (action.equals("shoot" + controlSet)) {
                if (gameE.eventType == 0) {
                    Bullet bL = new Bullet(bulletImages[2], x + 13, y-3, -(speed + 4), speed + 4);
                    Bullet bC = new Bullet(bulletImages[0], x + 17, y-2, 0, speed + 4);
                    Bullet bR = new Bullet(bulletImages[1], x + 10, y-3, speed + 4, speed + 4);
                    alliedBullets.add(bL);
                    alliedBullets.add(bC);
                    alliedBullets.add(bR);
                    bL = bR = bC = null;
                    System.out.println("Space-Bar!");
                }
            }
            /*
             switch (key) {
             case "left":
             xSpeed = -1 * speed * gameE.eventType;
             //System.out.println("Left to position " + x + ", " + y);
             break;
             case "right":
             //x += speed;
             xSpeed = speed * gameE.eventType;
             //System.out.println("Right to position " + x + ", " + y);
             break;
             case "up":
             ySpeed = -1 * speed * gameE.eventType;
             //System.out.println("Up to position " + x + ", " + y);
             break;
             case "down":
             ySpeed = speed * gameE.eventType;
             //System.out.println("Down to position " + x + ", " + y);
             break;
             case "shoot":
             if (gameE.eventType == 0) {
             Bullet bL = new Bullet(bulletImages[0], x + 10, y, 0, speed + 4);
             Bullet bR = new Bullet(bulletImages[0], x + 24, y, 0, speed + 4);
             alliedBullets.add(bL);
             alliedBullets.add(bR);
             bL = bR = null;
             System.out.println("Space-Bar!");
             }
             break;
             default:
             //System.out.println("What you pressed is not a control key");
             }
             */
        } /*else if (gameE.eventType == 2) {
         String msg = (String) gameE.event;
         if (msg.equals("P_Explosion")) {
         hp = 0;
         }
         }*/
    }
}
