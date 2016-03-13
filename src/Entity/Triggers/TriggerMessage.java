package Entity.Triggers;

import Entity.MapObject;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TriggerMessage extends MapObject{

    // messages on trigger
    public static final int START = 0;
    public static final int ENEMY = 1;
    public static final int NO_TIME = 2;
    public static final int OBSTACLE = 3;
    public static final int BEAUTIFUL = 4;
    public static final int END_GAME = 5;
    public static final int DETAIL_1 = 6;
    public static final int DETAIL_2 = 7;
    public static final int DETAIL_3 = 8;
    public static final int DETAIL_4 = 9;
    public static final int ALL_DETAILS = 10;
    public static final int COMPILE = 11;

    BufferedImage image;
    long messageTimer;
    int time;
    boolean messageShown = false;
    boolean messageFinished = false;

    public TriggerMessage(TileMap tm, int type, int time) {

        super(tm);

        this.time = time;

        // load sprites
        try {
            switch (type) {
                case 0: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/start.png")); break;
                case 1: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/enemy.png")); break;
                case 2: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/noTime.png")); break;
                case 3: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/obstacle.png")); break;
                case 4: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/beautiful.png")); break;
                case 5: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/endGame.png")); break;
                case 6: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/detail1.png")); break;
                case 7: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/detail2.png")); break;
                case 8: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/detail3.png")); break;
                case 9: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/detail4.png")); break;
                case 10: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/allDetails.png")); break;
                case 11: image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/compile.png")); break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (!messageShown) {
            messageTimer = System.nanoTime();
            messageShown = true;
        }

        if(messageShown) {
            long elapsed = (System.nanoTime() - messageTimer) / 1000000;
            if(elapsed > time * 1000) {
                messageShown = false;
                messageFinished = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, 100, 10, null);
    }

    public boolean getMessageShown() { return messageShown; }

}
