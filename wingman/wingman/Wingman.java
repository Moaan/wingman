package wingman;

import CombatGame.GameControl;
import CombatGame.GameEvents;
import CombatGame.GameTime;
import CombatGame.MenuControl;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author Moaan
 */
public class Wingman extends JApplet implements Runnable {
    static HashMap<Integer, String> controls = new HashMap<>();
    static boolean gameIsOver = false, gameStart = false, displayControls = false;
    static int borderX = 740; //objects need to know the border
    static int borderY = 580; //objects need to know the border
    private Thread coreThread;
    private BufferedImage bufferedImg;
    static int scrollSpeed = 1, planeSpeed = 6, score = 0, frameLength = 1, totalDistance = 0;
    Random generator = new Random(2468104);
    ImageObserver observer;
    public static GameEvents gE;
    GameTime timeKeeper;
    //declaring the games objects
    OceanBackground theOcean;
    public static UserPlane player1, player2;
    public Island island_1, island_2, island_3;
    public Image island_1_Image, island_2_Image, island_3_Image;
    public static EnemyArmy theEnemyArmy;
    // Bullets shot by user planes and allies
    public static ArrayList<Bullet> enemyBullets, alliedBullets;
    public static AudioClip backgroundSound, explosionSound_1, explosionSound_2;
    public static int numberOfPlayers;
    //Start Menu buttons
    public static OnePlayerButton oneB;
    public static TwoPlayerButton twoB;
    public static ControlsButton ctrlsB;
    public static ExitControlsButton eCtrlsB;

    public static void main(String argv[]) {
        final Wingman Beta = new Wingman();
        Beta.init();
        JFrame f = new JFrame("Moaan's Wingman Game");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", Beta);
        f.pack();
        f.setSize(new Dimension(borderX, borderY));
        f.setVisible(true);
        f.setResizable(false);
        Beta.start();
    }

    public void init() {
        gE = new GameEvents();
        int timedEvents[] = {180, 330, 480, 630, 780, 930, 1120, 1180};
        timeKeeper = new GameTime(gE, timedEvents, frameLength);

        setBackground(Color.black);
        this.setFocusable(true);
        observer = this;

        //initializing Background Objects
        Image islandImages[] = {getSprite("Resources/island1_2.png"),
            getSprite("Resources/island2_2.png"),
            getSprite("Resources/island3_2.png")};
        String backGroundMusic = "Resources/ff8RideOn.wav";
        String gameOverMusic = "Resources/ff8GameOver.wav";
        theOcean = new OceanBackground(getSprite("Resources/water3.png"), islandImages, backGroundMusic, gameOverMusic);

        //declaring and initializeing enemy bullet images
        Image e_BulletImages[] = {getSprite("Resources/enemybullet1.png"),
            getSprite("Resources/enemybullet2.png")};

        alliedBullets = new ArrayList();

        //initializing enemy Objects
        Image enemy1Images[] = {getSprite("Resources/enemy1_1.png"),
            getSprite("Resources/enemy1_2.png"),
            getSprite("Resources/enemy1_3.png"),
            getSprite("Resources/explosion1_1.png"),
            getSprite("Resources/explosion1_2.png"),
            getSprite("Resources/explosion1_3.png"),
            getSprite("Resources/explosion1_4.png"),
            getSprite("Resources/explosion1_5.png"),
            getSprite("Resources/explosion1_6.png")};
        Image enemy2Images[] = {getSprite("Resources/enemy2_1.png"),
            getSprite("Resources/enemy2_2.png"),
            getSprite("Resources/enemy2_3.png"),
            getSprite("Resources/explosion1_1.png"),
            getSprite("Resources/explosion1_2.png"),
            getSprite("Resources/explosion1_3.png"),
            getSprite("Resources/explosion1_4.png"),
            getSprite("Resources/explosion1_5.png"),
            getSprite("Resources/explosion1_6.png")};
        Image enemy3Images[] = {getSprite("Resources/enemy3_1.png"),
            getSprite("Resources/enemy3_2.png"),
            getSprite("Resources/enemy3_3.png"),
            getSprite("Resources/explosion1_1.png"),
            getSprite("Resources/explosion1_2.png"),
            getSprite("Resources/explosion1_3.png"),
            getSprite("Resources/explosion1_4.png"),
            getSprite("Resources/explosion1_5.png"),
            getSprite("Resources/explosion1_6.png")};
        Image enemy4Images[] = {getSprite("Resources/enemy4_1.png"),
            getSprite("Resources/enemy4_2.png"),
            getSprite("Resources/enemy4_3.png"),
            getSprite("Resources/explosion1_1.png"),
            getSprite("Resources/explosion1_2.png"),
            getSprite("Resources/explosion1_3.png"),
            getSprite("Resources/explosion1_4.png"),
            getSprite("Resources/explosion1_5.png"),
            getSprite("Resources/explosion1_6.png")};
        Image allEnemyImages[][] = {enemy1Images, enemy2Images, enemy3Images, enemy4Images};

        theEnemyArmy = new EnemyArmy(allEnemyImages, e_BulletImages);
        enemyBullets = new ArrayList();

        //Start Menu buttons
        oneB = new OnePlayerButton();
        twoB = new TwoPlayerButton();
        ctrlsB = new ControlsButton();
        eCtrlsB = new ExitControlsButton();

        gE.addObserver(theEnemyArmy);
        gE.addObserver(oneB);
        gE.addObserver(twoB);
        gE.addObserver(ctrlsB);
        GameControl gC = new GameControl(gE);
        MenuControl mC = new MenuControl(gE);
        addKeyListener(gC);
        addMouseListener(mC);

        explosionSound_1 = getAudioFile("Resources/snd_explosion1.wav");
        explosionSound_2 = getAudioFile("Resources/snd_explosion2.wav");

    }

    private AudioClip getAudioFile(String fileName) {
        URL url = Wingman.class.getResource(fileName);
        return newAudioClip(url);
    }

    @Override
    public void start() {
        coreThread = new Thread(this);
        coreThread.setPriority(Thread.MIN_PRIORITY);
        coreThread.start();
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (coreThread == me) {
            repaint();
            try {
                coreThread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        updateAndDisplay(borderX, borderY, g2);
        g2.dispose();
        g.drawImage(bufferedImg, 0, 0, this);
        //g.drawImage(bufferedImg.getSubimage(0, 0, d.width/2, d.height), 0, 0, this);
        //g.drawImage(bufferedImg.getSubimage(0, 0, d.width/2, d.height),  d.width/2, 0, this);
    }

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bufferedImg == null || bufferedImg.getWidth() != w || bufferedImg.getHeight() != h) {
            bufferedImg = (BufferedImage) createImage(w, h);
        }
        g2 = bufferedImg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    public void updateAndDisplay(int w, int h, Graphics2D g2) {
        if (!gameStart) {
            //dispay start menu
            theOcean.drawStartMenu(g2, observer, getWidth(), getHeight());
            if(displayControls) {
                ctrlsB.drawControlsInfo(g2, observer, getWidth(), getHeight());
                eCtrlsB.draw(g2, observer, getWidth(), getHeight());
            } else {
                oneB.draw(g2, observer, getWidth(), getHeight());
                twoB.draw(g2, observer, getWidth(), getHeight());
                ctrlsB.draw(g2, observer, getWidth(), getHeight());
            }

        } else if ((gameStart) && !gameIsOver) {
            timeKeeper.play();

            theOcean.draw(g2, observer);

            theEnemyArmy.move();
            theEnemyArmy.draw(g2, this);

            if (numberOfPlayers > 1) {
                player2.move();
                player2.draw(g2, this);
            }

            player1.move();
            player1.draw(g2, this);

            for (int i = 0; i < alliedBullets.size(); i++) {
                if (alliedBullets.get(i).move()) {
                    alliedBullets.remove(i);
                } else {
                    alliedBullets.get(i).draw(g2, this);
                }
            }

            for (int i = 0; i < enemyBullets.size(); i++) {
                if (enemyBullets.get(i).move()) {
                    enemyBullets.remove(i);
                } else {
                    enemyBullets.get(i).draw(g2, this);
                }
            }
        } else {
            theOcean.playGameOverMusic();
            g2.drawImage(getSprite("Resources/gameOver3.png"), (borderX / 2) - 125, (borderY / 4) - 40, observer);
            // Drawing the Score in an aligned box:
            //String gameOverMessage = "Game Over";
            String scoreMessage = "Your Score: " + score;
            Font l = new Font("Garamond", Font.BOLD, 48);
            g2.setFont(l);

            //measure the message 'greeting'
            FontRenderContext context = g2.getFontRenderContext(); //gets font characteristics specific to screen res.
            Rectangle2D bounds = l.getStringBounds(scoreMessage, context);

            //set (x,y) = top left corner of text rectangle
            double x = (getWidth() - bounds.getWidth()) / 2;
            double y = (getHeight() - bounds.getHeight()) / 2;

            //add ascent to y to reach the baseline
            double ascent = -bounds.getY();
            double baseY = y + ascent;

            g2.setPaint(Color.GREEN);

            //Now, draw the centered, styled message
            //g2.drawString(gameOverMessage, (borderX / 2) - 160, (borderY / 4) - 40);
            g2.drawString(scoreMessage, (int) x, (int) baseY);
        }
    }

    public Image getSprite(String name) {
        //System.out.println(Wingman.class.getResourceAsStream(name));
        URL url = Wingman.class.getResource(name);
        Image img = getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println(e + "the image can't be added");
        }
        return img;
    }

    public void initializePlayers(int playerCount) {
        numberOfPlayers = playerCount;

        //initialize player1 controls
        controls.put(KeyEvent.VK_A, "left1");
        controls.put(KeyEvent.VK_W, "up1");
        controls.put(KeyEvent.VK_S, "down1");
        controls.put(KeyEvent.VK_D, "right1");
        controls.put(KeyEvent.VK_SPACE, "shoot1");

        //initializing plane Object(s)
        Image a_BulletImages[] = {getSprite("Resources/bullet.png"),
            getSprite("Resources/bulletLeft.png"),
            getSprite("Resources/bulletRight.png")};

        Image planeImages[] = {getSprite("Resources/myplane_1.png"),
            getSprite("Resources/myplane_2.png"),
            getSprite("Resources/myplane_3.png"),
            getSprite("Resources/explosion2_1.png"),
            getSprite("Resources/explosion2_2.png"),
            getSprite("Resources/explosion2_3.png"),
            getSprite("Resources/explosion2_4.png"),
            getSprite("Resources/explosion2_5.png"),
            getSprite("Resources/explosion2_6.png"),
            getSprite("Resources/explosion2_7.png")};
        Image healthInfoImages[] = {getSprite("Resources/health1_1.png"),
            getSprite("Resources/health1_2.png"),
            getSprite("Resources/health1_3.png"),
            getSprite("Resources/health1_4.png"),
            getSprite("Resources/health1_5.png"),
            getSprite("Resources/life.png")};
        player1 = new UserPlane(planeImages, a_BulletImages, healthInfoImages, borderX / (2 * numberOfPlayers), 290, 1);
        gE.addObserver(player1);
        if (numberOfPlayers > 1) {
            //initialize player2 controls
            controls.put(KeyEvent.VK_LEFT, "left2");
            controls.put(KeyEvent.VK_UP, "up2");
            controls.put(KeyEvent.VK_DOWN, "down2");
            controls.put(KeyEvent.VK_RIGHT, "right2");
            controls.put(KeyEvent.VK_ENTER, "shoot2");
            player2 = new UserPlane(planeImages, a_BulletImages, healthInfoImages, borderX * 4 / 6, 290, 2);
            gE.addObserver(player2);
        }
    }
}
