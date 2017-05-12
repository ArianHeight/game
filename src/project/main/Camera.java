package project.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Camera {
	private int X, Y;
	private Player p;
	
	public Camera(Player p){
		this.p = p;
	}

	public void tick() {
		X += p.getVelX();
		Y += p.getVelY();
		
	}
	
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/Coin.png")).getImage();
		g.drawImage(img, 0, 0, 16, 16, 0, 0, 16, 16, null);
	}
}
