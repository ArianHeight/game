package project.main;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * Handler
 * A class that holds all of the GameObjects in a LinkedList (for efficiency). Calling its tick method calls the tick
 * method for all of the GameObjects (same for render). It allows for the addition and removal of GameObjects.
 */

public class Handler {
    	 
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	LinkedList<GameObject> enemies = new LinkedList<GameObject>();
	LinkedList<GameObject> balls = new LinkedList<GameObject>();
	LinkedList<GameObject> collectibles = new LinkedList<GameObject>();
	 
	public void tick(){
		for (int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	 
	public void render(Graphics g){
		for (int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	 
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void addObject(Enemy object){
		this.enemies.add(object);
	}
	 
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public void printObjects(){
		for (GameObject go: object){
			System.out.println(go);
		}
	}
	
	public void clearAll(){
		clearEnemies();
		clearBalls();
		clearCollectibles();
	}
	
	
	public void clearEnemies(){ enemies.clear(); }
	public void clearBalls(){ balls.clear(); }
	public void clearCollectibles(){ collectibles.clear(); }
	
	public void freezeObjects(){
		for (GameObject go: object){
			go.setMoving(false);
		}
	}
	
	public void unfreezeObjects(){
		for (GameObject go: object){
			go.setMoving(true);
		}
	}
}
