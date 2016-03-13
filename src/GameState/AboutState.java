package GameState;

import Main.GamePanel;
import TileMap.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AboutState extends GameState {

    private Background bg;

    public AboutState(GameStateManager gsm) {

        this.gsm = gsm;

        try {

            bg = new Background("/Backgrounds/aboutbg.png", 1);
            bg.setVector(-0.1, 0);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {}

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {
        // draw bg
        bg.draw(g);
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/aboutText.png"));
            g.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    public void keyReleased(int k) {}

}
