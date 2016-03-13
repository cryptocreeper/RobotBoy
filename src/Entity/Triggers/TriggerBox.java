package Entity.Triggers;

import Entity.Animation;
import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TriggerBox extends MapObject {

    private boolean triggerOn = false;

    private BufferedImage[] sprites;
    private TriggerMessage triggerMessage;
    private boolean remove;

    public TriggerBox(TileMap tm, int type, int time) {
        super(tm);

        width = 30;
        height = 100;
        cwidth = 30;
        cheight = 100;

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/trigger.png"));
            sprites = new BufferedImage[2];

            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);

        triggerMessage = new TriggerMessage(tm, type, time);
    }

    public void checkTrigger(Player player) {
        if (!triggerOn) {
            if (intersects(player)) {
                triggerOn = true;
            }
        }
    }

    public boolean shouldRemove() { return remove; }

    public void setTriggerOn(boolean on) { triggerOn = on; }

    public void update() {
        animation.update();

        if (triggerOn) {
            triggerMessage.update();
            if (!triggerMessage.getMessageShown()) {
                triggerOn = false;
                remove = true;
            }
        }
    }

    public void draw(Graphics2D g) {

        //setMapPosition();
        //super.draw(g);

        if (triggerOn) triggerMessage.draw(g);
    }
}
