package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;

import project.main.Game.STATE;


public class Menu extends MouseAdapter {

	public void render (Graphics g){
		if (Game.gameState == STATE.Menu){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 15);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Menu", 570, 50);
			
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 150, 200, 64);
			g.drawString("Play", Game.WIDTH / 2 - 30, 190);
			
			g.drawRect(Game.WIDTH / 2 - 100, 250, 200, 64);
			g.drawString("Help", Game.WIDTH / 2 - 30, 290);

			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Balls", Game.WIDTH / 2 - 30, 390);
			
			g.drawRect(Game.WIDTH / 2 - 100, 450, 200, 64);
			g.drawString("Zombies", Game.WIDTH / 2 - 50, 490);
			
			g.setFont(font3);
			g.drawString("Game Produced by Edmond Xue and Sean Wang. Copyright 2017", Game.WIDTH / 2 - 240, 600);
		}
		else if (Game.gameState == STATE.Help){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 20);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Help", 570, 50);
			
			g.setFont(font3);
			g.drawString("Use UDLR / WASD keys to move player. Click to shoot.", 400, 200);
			g.drawString("Press space to use an AOE attack.", 400, 240);
			g.drawString("Collect coins by defeating enemies.", 400, 280);
			g.drawString("Press u to open and close the upgrade store.", 400, 320);
			g.drawString("You lose health when a zombie approaches you", 400, 360);
			g.drawString("Or you are hit.", 400, 400);
			g.drawString("It is game over when you run out of health.", 400, 440);
			g.drawString("Try to get the highest score possible. Have fun!", 400, 480);
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 580, 200, 64);
			g.drawString("Back", 600, 620);
		}
	}	
}