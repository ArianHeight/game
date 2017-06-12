package balls;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import project.main.ID;
import project.main.Player;

public class CrystalBall extends Ball {
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Initial components are 
	* provided as input.
	*/
	public CrystalBall(double x, double y, double xVal, double yVal, Player p) {
		super(x, y, xVal, yVal, p, 5);
		id = ID.CrystalBall;
		setPower(50);
		infinitePierce = false;
	}
	
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Angle is provided as 
	* input
	*/
	public CrystalBall(double x, double y, double angle, Player p) {
		super(x, y, angle, p, 5);
		id = ID.CrystalBall;
		setPower(50);
		infinitePierce = false;
	}

	@Override
	public void render(Graphics g) {
		updateWindowCoordinates();
		Image img = new ImageIcon(this.getClass().getResource("/crystalball.png")).getImage();
		int size = 16;
		if (isEnemy){
			size = 32;
		}
		g.drawImage(img, (int)winX, (int)winY, (int)winX + size, (int)winY + size, 0, 0, 16, 16, null);
	}
}