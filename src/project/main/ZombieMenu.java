package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import project.main.Game.STATE;


public class ZombieMenu {
	
	public void render (Graphics g){
		if (Game.gameState == STATE.ZombieMenu){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 15);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Zombies", 550, 50);
			
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 580, 200, 64);
			g.drawString("Back", 600, 620);
			
			g.setFont(font3);
			
			for (int i = 1; i <= 3; i++){
				String picName;
				int y = 350;
				
				if (i == 1){
					picName = "/zombie.png";
					g.drawString("Zombie", 180 * i - 40, y);
				}
				else if (i == 2){
					picName = "/zombieThrower.png";
					g.drawString("Zombie Thrower", 180 * i - 50, y);
				}
				else {
					picName = "/zombieKnight.png";
					g.drawString("Zombie Knight", 180 * i - 50, y);
				}
				
				Image img = new ImageIcon(this.getClass().getResource(picName)).getImage();
				g.drawImage(img, 180 * i - 50, 250, 180 * i + 30, 330, 0, 0, 32, 32, null);
			}
		}
	}	
}