package project.main;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * Handler
 * A class that holds all of the GameObjects in a LinkedList (for efficiency). Calling its tick method calls the tick
 * method for all of the GameObjects (same for render). It allows for the addition and removal of GameObjects.
 */

public class Handler {
	
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	LinkedList<Ball> balls = new LinkedList<Ball>();
	LinkedList<Collectible> collectibles = new LinkedList<Collectible>();
	 
	public void tick(){
		for (int i = enemies.size() - 1; i >= 0; i++){
			enemies.get(i).tick();
		}
		for (int i = balls.size() - 1; i >= 0; i++){
			balls.get(i).tick();
		}
		for (int i = collectibles.size() - 1; i >= 0; i++){
			collectibles.get(i).tick();
		}
	}
	 
	public void render(Graphics g){
		for (int i = enemies.size() - 1; i >= 0; i++){
			enemies.get(i).render(g);
		}
		for (Ball b : balls){
			b.render(g);
		}
		for (Collectible c : collectibles){
			c.render(g);
		}
	}
	 
	public void addObject(Enemy object){ this.enemies.add(object); }
	public void addObject(Ball object){ this.balls.add(object); }
	public void addObject(Collectible object){ this.collectibles.add(object); }
	
	public void clearAll(){
		clearEnemies();
		clearBalls();
		clearCollectibles();
	}
	
	
	public void clearEnemies(){ enemies.clear(); }
	public void clearBalls(){ balls.clear(); }
	public void clearCollectibles(){ collectibles.clear(); }
	

	public void freezeObjects(){
		for (Enemy e: enemies){
			e.setMoving(false);
		}
	}
	
	public void unfreezeObjects(){
		for (Enemy e: enemies){
			e.setMoving(true);
		}
	}
	
}
