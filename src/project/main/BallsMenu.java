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
			
			g.setFont(font3);
			
			for (int i = 1; i <= 7; i++){
				String picName;
				int y = 350;
				
				if (i == 1){
					picName = "/waterball.png";
					g.drawString("Water Ball", 160 * i - 40, y);
				}
				else if (i == 2){
					picName = "/fireball.png";
					g.drawString("Fire Ball", 160 * i - 40, y);
				}
				else if (i == 3){
					picName = "/rockball.png";
					g.drawString("Rock Ball", 160 * i - 40, y);
				}
				else if (i == 4){
					picName = "/fluxball.png";
					g.drawString("Flux Ball", 160 * i - 40, y);
				}
				else if (i == 5){
					picName = "/lifeball.png";
					g.drawString("Life Ball", 160 * i - 40, y);
				}
				else if (i == 6){
					picName = "/crystalball.png";
					g.drawString("Crystal Ball", 160 * i - 40, y);
				}
				else {
					picName = "/mysteryball.png";
					g.drawString("Mystery Ball", 160 * i - 40, y);
				}
				
				Image img = new ImageIcon(this.getClass().getResource(picName)).getImage();
				g.drawImage(img, 160 * i - 50, 250, 160 * i + 30, 330, 0, 0, 8, 8, null);
			}
		}
	}	
}
