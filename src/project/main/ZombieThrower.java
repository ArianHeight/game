package project.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ZombieThrower extends Enemy {

	public ZombieThrower(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.ZombieThrower;
		setHealth(150 + 3 * hud.getLevel());
		setMaxHealth(150 + 3 * hud.getLevel());
		setVel(7);
	}

	public void tick()
	{
		super.move();
		super.tick();
		super.collision();
	}
	
	public void attack()
	{
		
	}
	
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/ZombieThrower.png")).getImage();
		updateWindowCoordinates();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		super.render(g);
	}
}
