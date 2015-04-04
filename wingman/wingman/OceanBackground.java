package wingman;

import CombatGame.Background;
import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 *
 * @author Moaan
 */
public class OceanBackground extends Wingman implements Background {

    Image tile;
    //Image menuTile = getSprite("Resources/water.png");
    int TileWidth, TileHeight;
    int scrollSpeed = 1, totalDistance = 0;
    Island islands[];
    AudioClip backgroundMusic, gameOverMusic;
    boolean EndOfGameFlag = true;
    private String gameTitle = "WINGMAN-SAM";
    private Font f1 = new Font("Stencil", Font.PLAIN, 36);
    private Font f2 = new Font("Stencil", Font.PLAIN, 36);
    private Rectangle2D bounds;
    private FontRenderContext context;

    OceanBackground(Image tile, Image islandImages[], String SoundFileLocation1, String SoundFileLocation2) {
        this.tile = tile;
        islands = new Island[islandImages.length];
        for (int i = 0; i < islandImages.length; i++) {
            islands[i] = new Island(islandImages[i], scrollSpeed, generator);
        }
        URL url1 = Wingman.class.getResource(SoundFileLocation1);
        URL url2 = Wingman.class.getResource(SoundFileLocation2);
        backgroundMusic = newAudioClip(url1);
        gameOverMusic = newAudioClip(url2);
        TileWidth = tile.getWidth(this);
        TileHeight = tile.getHeight(this);
    }

    public void drawStartMenu(Graphics2D g, ImageObserver obs, int width, int height) {
        int NumberX = (int) (borderX / TileWidth);
        int NumberY = (int) (borderY / TileHeight);

        //non-moving background
        for (int i = 0; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g.drawImage(tile, j * TileWidth, i * TileHeight, TileWidth, TileHeight, this);
            }
        }

        if (!displayControls) {
            //measure the label
            context = g.getFontRenderContext(); //gets font characteristics specific to screen res.
            bounds = f1.getStringBounds(gameTitle, context);

            //set (x,y) = top left corner of text rectangle
            int x = (int) (width - bounds.getWidth()) / 2;
            int y = (int) (height - bounds.getHeight()) / 5;
            //draw Game Title
            g.setFont(f1);
            g.setColor(Color.BLACK);
            g.drawString(gameTitle, x, y);
            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString(gameTitle, x + 5, y);
        }
    }

    public void draw(Graphics2D g, ImageObserver obs) {
        int NumberX = (int) (borderX / TileWidth);
        int NumberY = (int) (borderY / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g.drawImage(tile, j * TileWidth, i * TileHeight + (totalDistance % TileHeight), TileWidth, TileHeight, this);
            }
        }
        totalDistance += scrollSpeed;

        //Draws any and all background objects
        for (int i = 0; i < islands.length; i++) {
            islands[i].update();
            islands[i].draw(g, obs);
        }
    }

    @Override
    public void playMusic() {
        backgroundMusic.loop();
    }

    @Override
    public void playGameOverMusic() {
        if (EndOfGameFlag) {
            backgroundMusic.stop();
            gameOverMusic.play();
            EndOfGameFlag = false;
        }
    }
}
