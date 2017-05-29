package project.main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected double x, y;
	/*
	 * winX and winY are the coordinates for the object with respect to the window. That is, these values are always
	 * between the WIDTH of the game and the HEIGHT of the game. 
	 */
	protected double winX, winY;
	protected ID id;
	protected boolean isMoving;
	 
	public GameObject(double x, double y){
		this.x = x;
		this.y = y;
		isMoving = true;
	}
	 
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public void setX(double x){ this.x = x; }
	public void setY(double y){ this.y = y; }
	public double getX(){ return x; }
	public double getY(){ return y; }
	public void setId(ID id){ this.id = id;	}
	public ID getId(){ return id; }
	public double getWinX(){ return winX; }
	public double getWinY(){ return winY; }
	public void setMoving(boolean moving){ this.isMoving = moving; }
	
	/*
	 * Because the camera's positions are close to the center (WIDTH / 2, HEIGHT / 2), we need to add them
	 * to find the coordinates with respect to the window. 
	 */
	public void updateWindowCoordinates(){
		winX = x - Game.camera.X + Game.WIDTH / 2;
		winY = y - Game.camera.Y + Game.HEIGHT / 2;
	}
	
	public String toString(){
		return "Absolute X: " + x + " Absolute Y: " + y + "\nWindow X: " + winX + " Window Y: " + winY;
	}
}
