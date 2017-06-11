package project.main;

import project.main.Game.STATE;

/**
 * 
 * @author 
 * 
 * Classes that represent objects that are Collectible, i.e. Coins, extend this class. 
 *
 */

public abstract class Collectible extends GameObject {
	private int time = 300;
	public Collectible(double x, double y) {
		super(x, y);
	}
	public void tick() {
		if (Game.gameState == STATE.Game){
			time--;
		}
		
		if (time <= 0){
			Game.handler.collectibles.remove(this);
		}
	}
}
