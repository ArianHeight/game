package project.main;

import java.awt.Graphics;
import java.util.ArrayList;

import balls.Ball;

/**
 * Handler
 * A class that holds all of the GameObjects in a LinkedList (for efficiency). Calling its tick method calls the tick
 * method for all of the GameObjects (same for render). It allows for the addition and removal of GameObjects.
 */

public class Handler {
	
	public boolean running = false;
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Ball> balls = new ArrayList<Ball>();
	public ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	 
	public void tick(){
		if (!running){
			return;
		}
		for (int i = enemies.size() - 1; i >= 0; i--){
			if (i >= enemies.size()){
				return;
			}
			if (enemies.get(i) == null){
				return;
			}
			enemies.get(i).tick();
		}
		for (int i = balls.size() - 1; i >= 0; i--){
			if (i >= balls.size()){
				return;
			}
			if (balls.get(i) == null){
				return;
			}
			balls.get(i).tick();
		}
		for (int i = collectibles.size() - 1; i >= 0; i--){
			if (i >= collectibles.size()){
				return;
			}
			if (collectibles.get(i) == null){
				return;
			}
			collectibles.get(i).tick();
		}
		for (int i = explosions.size() - 1; i >= 0; i--){
			if (i >= explosions.size()){
				return;
			}
			if (explosions.get(i) == null){
				return;
			}
			explosions.get(i).tick();
		}
	}
	 
	public void render(Graphics g){
		if (!running){
			return;
		}
		for (int i = enemies.size() - 1; i >= 0; i--){
			if (i >= enemies.size()){
				return;
			}
			if (enemies.get(i) == null){
				return;
			}
			enemies.get(i).render(g);
		}
		for (int i = balls.size() - 1; i >= 0; i--){
			if (i >= balls.size()){
				return;
			}
			if (balls.get(i) == null){
				return;
			}
			balls.get(i).render(g);
		}
		for (int i = collectibles.size() - 1; i >= 0; i--){
			if (i >= collectibles.size()){
				return;
			}
			if (collectibles.get(i) == null){
				return;
			}
			collectibles.get(i).render(g);
		}
		for (int i = explosions.size() - 1; i >= 0; i--){
			if (i >= explosions.size()){
				return;
			}
			if (explosions.get(i) == null){
				return;
			}
			explosions.get(i).render(g);
		}
	}
	 
	public void addObject(Enemy object){ this.enemies.add(object); }
	public void addObject(Ball object){ this.balls.add(object); }
	public void addObject(Collectible object){ this.collectibles.add(object); }
	public void addObject(Explosion object){ this.explosions.add(object); }
	
	public void clearAll(){
		clearEnemies();
		clearBalls();
		clearCollectibles();
		clearExplosions();
	}
	
	
	public void clearEnemies(){ enemies.clear(); }
	public void clearBalls(){ balls.clear(); }
	public void clearCollectibles(){ collectibles.clear(); }
	public void clearExplosions(){ explosions.clear(); }

	public void freezeObjects(){
		for (int i = enemies.size() - 1; i >= 0; i--){
			if (!(i >= enemies.size())){
				Enemy e = enemies.get(i);
				if (e != null){
					e.setMoving(false);
				}
			}
		}
		for (Ball b: balls){
			if (b != null){
				b.setMoving(false);
			}
		}
	}
	
	public void unfreezeObjects(){
		for (Enemy e: enemies){
			if (e != null){
				e.setMoving(true);
			}
		}
		for (Ball b: balls){
			if (b != null){
				b.setMoving(true);
			}
		}
	}
}