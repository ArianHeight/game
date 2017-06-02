package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class LifeBall extends Ball {
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Initial components are 
	* provided as input.
	*/
	public LifeBall(double x, double y, double xVal, double yVal, Player p) {
		super(x, y, xVal, yVal, p, 8);
		id = ID.LifeBall;
		setPower(30);
		infinitePierce = true;
	}
	
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Angle is provided as 
	* input
	*/
	public LifeBall(double x, double y, double angle, Player p) {
		super(x, y, angle, p, 8);
		id = ID.LifeBall;
		setPower(30);
		infinitePierce = true;
	}

	@Override
	public void render(Graphics g) {
		updateWindowCoordinates();
		Image img = new ImageIcon(this.getClass().getResource("/lifeball.png")).getImage();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 16, (int)winY + 16, 0, 0, 16, 16, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)winX, (int)winY, 4, 4);
	}
}
