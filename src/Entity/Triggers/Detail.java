package Entity.Triggers;

import Entity.Animation;
import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Detail extends MapObject{

    public static int count = 0;

    private BufferedImage[] sprites;

    private boolean remove;

    public Detail(TileMap tm) {

        super(tm);

        width = 30;
        height = 30;
        cwidth = 30;
        cheight = 30;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/detail.png"));
            sprites = new BufferedImage[4];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

    }

    public boolean shouldRemove() { return remove; }

    public void checkDetail(Player player) {
        if (intersects(player)) {
            count++;
            remove = true;
        }
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
