package project.main;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author 
 * Heads up display, health bar, level, score, etc. 
 *
 */
public class HUD {
	public static double HEALTH = 100, MAX_HEALTH = 100;
	private double greenValue = 255;
	
	private int time = 0;
	private int score = 0;
	private int level = 1;
	private int coins = 0;
	
	public void tick(){		
		HEALTH = Game.clamp(HEALTH, 0, MAX_HEALTH);
		greenValue = HEALTH * 2;
		greenValue = Game.clamp(greenValue, 0, 255);
		time++;
	}
	
	public void render(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(15, 15, (int)(MAX_HEALTH * 2), 32);
		// changes color of health as it decreases from green (max health) to red (min health)
		g.setColor(new Color(255-(int)greenValue, (int) greenValue, 0));
		g.fillRect(15, 15, (int)(HEALTH * 2), 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, (int)(MAX_HEALTH * 2), 32);
		g.drawString((int)HEALTH + " / " + (int)MAX_HEALTH, 15, 70);
		
		g.drawString("Level: " + level, 15, 85);
		g.drawString("Score: " + score, 15, 100);
		g.drawString("Coins: " + coins, 15, 115);
	}
	
	public void clearAll(){
		HEALTH = 100;
		MAX_HEALTH = 100;
		greenValue = HEALTH * 2;
		time = 0;
		score = 0;
		level = 1;
		coins = 0;
	}
	
	public void setTime(int time){ this.time = time; }
	public int getTime(){ return time; }
	public void setScore(int score){ this.score = score; }
	public int getScore(){ return score; }
	public void setLevel(int level){ this.level = level; }
	public int getLevel(){ return level; }
	public void setCoins(int coins){ this.coins = coins; }
	public int getCoins(){ return coins; }	
}
