package project.main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

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
		Camera c = Game.camera;
		g.drawImage(img, x - c.X, y - c.Y, x + 16 - c.X, y + 16 - c.Y, 0, 0, 16, 16, null);
	}
}
