package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;

import project.main.Game.STATE;


public class End extends MouseAdapter {

	private HUD hud;
	//private Random r = new Random();
	
	public End(HUD hud){
		this.hud = hud;
	}

	public void tick(){
		
	}
	
	public void render (Graphics g){
		if (Game.gameState == STATE.End){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 20);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Game Over", 510, 50);
			
			g.setFont(font3);
			g.drawString("You lost with a score of: " + hud.getScore(), 500, 200);
			g.drawString("Level: " + hud.getLevel(), 500, 250);
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 580, 200, 64);
			g.drawString("Try Again", 580, 620);
		}
	}	
}