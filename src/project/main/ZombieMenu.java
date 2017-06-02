package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import project.main.Game.STATE;


public class ZombieMenu {
	
	public void render (Graphics g){
		if (Game.gameState == STATE.ZombieMenu){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Zombies", 550, 50);
			
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 580, 200, 64);
			g.drawString("Back", 600, 620);
		}
	}	
}
