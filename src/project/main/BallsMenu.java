package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import project.main.Game.STATE;


public class BallsMenu {

	public void tick(){
		
	}
	
	public void render (Graphics g){
		if (Game.gameState == STATE.BallsMenu){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 15);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Balls", 570, 50);
			
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 580, 200, 64);
			g.drawString("Back", 600, 620);
			
			Image img = new ImageIcon(this.getClass().getResource("/waterball.png")).getImage();
			g.drawImage(img, 0, 0, 80, 80, 0, 0, 8, 8, null);
			
			g.setFont(font3);
			g.drawString("Water Ball", 10, 100);
			g.drawString("A generic ball", 0, 120);
		}
	}	
}
