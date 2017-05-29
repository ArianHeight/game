package project.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ZombieThrower extends Enemy {

	public ZombieThrower(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.ZombieThrower;
		setHealth(150);
	}

	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/ZombieThrower.png")).getImage();
		//Camera c = Game.camera;
		updateWindowCoordinates();
		//g.drawImage(img, (int)(x - c.X + Game.WIDTH / 2), (int)(y - c.Y + Game.HEIGHT / 2), (int)(x + 32 - c.X + Game.WIDTH / 2), (int)(y + 32 - c.Y + Game.HEIGHT / 2), 0, 0, 32, 32, null);
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
	}
}
