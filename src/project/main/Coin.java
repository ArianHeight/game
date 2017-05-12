package project.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Coin extends GameObject {

	public Coin(int x, int y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/Coin.png")).getImage();
		g.drawImage(img, 0, 0, 16, 16, 0, 0, 16, 16, null);
	}
}
