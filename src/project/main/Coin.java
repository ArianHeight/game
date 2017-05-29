package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Coin extends Collectible {

	public Coin(int x, int y) {
		super(x, y);
		id = ID.Coin;
	}

	@Override
	public void tick() {
		double xDiff = Game.player.getX() - x;
		double yDiff = Game.player.getY() - y;
		double dist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		
		if (dist <= 50.0)
		{
			this.x = Game.camera.lerp(this.x, Game.player.getX(), 0.25);
			this.y = Game.camera.lerp(this.y, Game.player.getY(), 0.25);
		}
	}

	@Override
	public void render(Graphics g) {
		updateWindowCoordinates();
		Image img = new ImageIcon(this.getClass().getResource("/Coin.png")).getImage();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 16, (int)winY + 16, 0, 0, 16, 16, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)winX, (int)winY, 16, 16);
	}
}
