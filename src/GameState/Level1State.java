package GameState;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState{
	private TileMap tileMap;
	private Background bg;
	private Player player;
	private HUD hud;
	private AudioPlayer bgMusic;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
public Level1State(GameStateManager gsm){
	this.gsm = gsm;
	init();
}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/grassbg1.gif",0.1);
		player = new Player(tileMap);
		player.setPosition(100,100);
		enemies = new ArrayList<Enemy>();
		Slugger s;
		s = new Slugger(tileMap);
		s.setPosition(100, 100);
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		hud = new HUD(player);
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
	}
	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		Slugger s;
		Point points[] = new Point[] {
				new Point(200,100),
				new Point(860,200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800,200)
				};
		for(int i = 0; i < points.length; i++){
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		s = new Slugger(tileMap);
		s.setPosition(860, 200);
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		player.update();
		tileMap.setPosition(GamePanel.WIDTH/2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());
		player.checkAttack(enemies);
		bg.setPosition(tileMap.getX(), tileMap.getY());
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getX(),e.getY()));
				
			}
		}
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
			explosions.get(i).draw(g);
		}
		hud.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}
		if(k == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}
		if(k == KeyEvent.VK_UP){
			player.setUp(true);
		}
		if(k == KeyEvent.VK_DOWN){
			player.setDown(true);
		}if(k == KeyEvent.VK_W){
			player.setJumping(true);
		}
		if(k == KeyEvent.VK_E){
			player.setGliding(true);
		}
		if(k == KeyEvent.VK_R){
			player.setScratching();
		}
		if(k == KeyEvent.VK_F){
			player.setFiring();
			}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		if(k == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		if(k == KeyEvent.VK_UP){
			player.setUp(false);
		}
		if(k == KeyEvent.VK_DOWN){
			player.setDown(false);
		}if(k == KeyEvent.VK_W){
			player.setJumping(false);
		}
		if(k == KeyEvent.VK_E){
			player.setGliding(false);
		}
	}

}
