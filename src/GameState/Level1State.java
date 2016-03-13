package GameState;

import Entity.Triggers.Detail;
import Entity.Triggers.TriggerBox;
import Entity.Triggers.TriggerMessage;
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;

import Audio.AudioPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private ArrayList<TriggerBox> triggers;
	private ArrayList<Detail> details;
	
	private HUD hud;
	
	private AudioPlayer bgMusic;

	TriggerBox obstacleTrigger;
	TriggerBox allDetailsTrigger;
	TriggerBox compileTrigger;
	TriggerBox endGameTrigger;

	boolean startSearch = false;
	boolean endSearch = false;
	boolean detail1 = false;
	boolean detail2 = false;
	boolean detail3 = false;
	boolean detail4 = false;
	boolean allDetails = false;
	boolean hoverOn = false;
	boolean endGame = false;
	boolean death = false;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg0.jpg", 0.2);
		
		player = new Player(tileMap);
		player.setPosition(80, 770);
		
		populateEnemies();
		createDetails();
		createTriggers();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();

		Detail.count = 0;
		tileMap.updateTile(23, 5, 12);
		tileMap.updateTile(23, 22, 13);
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();

		Slugger s;

		Point[] points = new Point[] {
			new Point(1500, 810),
			new Point(87 * 30, 57 * 30),
			new Point(51 * 30, 57 * 30),
			new Point(46 * 30, 50 * 30)
		};

		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}

	private void createDetails() {

		details = new ArrayList<Detail>();

		Detail d;

		Point[] points = new Point[] {
				new Point(900 + 15, 270 + 15),
				new Point(3030 + 15, 870 + 15),
				new Point(60 + 15, 1140 + 15),
				new Point(960 + 15, 1080 + 15),
				new Point(930 + 15, 1500 + 15)
		};

		for(int i = 0; i < points.length; i++) {
			d = new Detail(tileMap);
			d.setPosition(points[i].x, points[i].y);
			details.add(d);
		}

	}

	private void createTriggers() {

		triggers = new ArrayList<TriggerBox>();

		TriggerBox startTrigger = new TriggerBox(tileMap, TriggerMessage.START, 10);
		TriggerBox enemyTrigger = new TriggerBox(tileMap, TriggerMessage.ENEMY, 7);
		TriggerBox noTimeTrigger = new TriggerBox(tileMap, TriggerMessage.NO_TIME, 3);
		TriggerBox beautifulTrigger = new TriggerBox(tileMap, TriggerMessage.BEAUTIFUL, 3);
		endGameTrigger = new TriggerBox(tileMap, TriggerMessage.END_GAME, 6);
		obstacleTrigger = new TriggerBox(tileMap, TriggerMessage.OBSTACLE, 10);

		startTrigger.setPosition(150, 760);
		enemyTrigger.setPosition(1440, 790);
		noTimeTrigger.setPosition(2100, 520);
		beautifulTrigger.setPosition(2610, 1720);
		endGameTrigger.setPosition(3090, 370);
		obstacleTrigger.setPosition(2460, 430);

		triggers.add(startTrigger);
		triggers.add(enemyTrigger);
		triggers.add(noTimeTrigger);
		triggers.add(beautifulTrigger);
		triggers.add(endGameTrigger);
		triggers.add(obstacleTrigger);
	}

	public void update() {

		if (endGame || death) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bgMusic.close();
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);

		// update triggers

		if (!detail1 && Detail.count == 1) {
			detail1 = true;
			TriggerBox detail1Trigger = new TriggerBox(tileMap, TriggerMessage.DETAIL_1, 2);
			detail1Trigger.setTriggerOn(true);
			triggers.add(detail1Trigger);
		}

		if (!detail2 && Detail.count == 2) {
			detail2 = true;
			TriggerBox detail2Trigger = new TriggerBox(tileMap, TriggerMessage.DETAIL_2, 2);
			detail2Trigger.setTriggerOn(true);
			triggers.add(detail2Trigger);
		}

		if (!detail3 && Detail.count == 3) {
			detail3 = true;
			TriggerBox detail3Trigger = new TriggerBox(tileMap, TriggerMessage.DETAIL_3, 2);
			detail3Trigger.setTriggerOn(true);
			triggers.add(detail3Trigger);
		}

		if (!detail4 && Detail.count == 4) {
			detail4 = true;
			TriggerBox detail4Trigger = new TriggerBox(tileMap, TriggerMessage.DETAIL_4, 2);
			detail4Trigger.setTriggerOn(true);
			triggers.add(detail4Trigger);
		}

		if (!allDetails && Detail.count == 5) {
			allDetails = true;
			allDetailsTrigger = new TriggerBox(tileMap, TriggerMessage.ALL_DETAILS, 3);
			allDetailsTrigger.setTriggerOn(true);
			triggers.add(allDetailsTrigger);

			compileTrigger = new TriggerBox(tileMap, TriggerMessage.COMPILE, 5);
			compileTrigger.setPosition(2460, 430);
			triggers.add(compileTrigger);
		}

		if (endSearch && !triggers.contains(compileTrigger)) {
			tileMap.updateTile(15, 87, 14);
			hoverOn = true;
		}

		for (int i = 0; i < triggers.size(); i++) {
			TriggerBox triggerBox = triggers.get(i);
			triggerBox.checkTrigger(player);
			triggerBox.update();
			if (triggerBox.shouldRemove()) {
				triggers.remove(i);
				i--;
			}
		}

		if (!triggers.contains(obstacleTrigger) && !startSearch) startSearch = true;
		if (triggers.contains(allDetailsTrigger)) endSearch = true;
		if (!triggers.contains(endGameTrigger)) endGame = true;

		// update details
		for (int i = 0; i < details.size(); i++) {
			Detail detail = details.get(i);
			if (startSearch) detail.checkDetail(player);
			detail.update();
			if (detail.shouldRemove()) {
				details.remove(i);
				i--;
			}
		}
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

		if (player.getHealth() == 0) death = true;
		
	}
	
	public void draw(Graphics2D g) {

		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);

		// draw detail
		for (int i = 0; i < details.size(); i++) {
			details.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);

		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}

		// draw trigger
		for (int i = 0; i < triggers.size(); i++) {
			triggers.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);

		if (endGame) {
			try {
				BufferedImage levelComplete = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/levelComplete.png"));
				g.drawImage(levelComplete, GamePanel.WIDTH / 2 - levelComplete.getWidth() / 2,
						GamePanel.HEIGHT / 2 - levelComplete.getHeight() / 2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (death) {
			try {
				BufferedImage gameOver = ImageIO.read(getClass().getResourceAsStream("/Sprites/Triggers/gameOver.png"));
				g.drawImage(gameOver, GamePanel.WIDTH / 2 - gameOver.getWidth() / 2,
						GamePanel.HEIGHT / 2 - gameOver.getHeight() / 2, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		//if(k == KeyEvent.VK_UP) player.setUp(true);
		//if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W ) player.setJumping(true);
		if(k == KeyEvent.VK_E && hoverOn) player.setGliding(true);
		//if(k == KeyEvent.VK_R) player.setScratching();
		//if(k == KeyEvent.VK_F) player.setFiring();
		if(k == KeyEvent.VK_P) {
			gsm.setState(GameStateManager.MENUSTATE);
			bgMusic.close();
		}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		//if(k == KeyEvent.VK_UP) player.setUp(false);
		//if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E && hoverOn) player.setGliding(false);
	}
	
}












