package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class Ball extends GameObject {
	protected double velX, velY, vel;
	private int power;
	private int time;
	
	public static int ballCost;
	public static boolean ballPurchased, infinitePierce;
	
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Initial components are 
	* provided as input.
	*/
	public Ball(double x, double y, double xVal, double yVal, Player p, double vel) {
		super(x, y);
		this.id = ID.Ball;
		this.vel = vel;
		time = 300;
		velX = vel * xVal / Math.sqrt(xVal * xVal + yVal * yVal) + p.getVelX();
		velY = vel * yVal / Math.sqrt(xVal * xVal + yVal * yVal) + p.getVelY();	
	}
	
	/**
	* Constructor
	* Sets velX and velY to its components such that the ball's total velocity is always two. Angle is provided as 
	* input
	*/
	public Ball(double x, double y, double angle, Player p, double vel) {
		super(x, y);
		this.id = ID.Ball;
		this.vel = vel;
		time = 300;
		velX = vel * Math.cos(angle) + p.getVelX();
		velY = vel * Math.sin(angle) + p.getVelY();	
	}

	@Override
	public void tick() {
		if (isMoving){
			x += velX;
			y += velY;
			time--;
		}
		
		if (time == 0){
			Game.handler.balls.remove(this);
		}
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
	
	public int getPower(){ return power; }
	public void setPower(int power){ this.power = power; }
}
