package project.main;

import java.awt.Graphics;

public class Camera {
	public double X, Y;
	private Player p;
	
	public Camera(Player p){
		this.p = p;
	}
	
	public double lerp(double a, double b, double t)
	{
		return (1.0 - t) * a + t * b; 
	}
	
	public void tick() {
		this.X = this.lerp(this.X, this.p.getX(), 0.2);
		this.Y = this.lerp(this.Y, this.p.getY(), 0.2);
	}
	
	public void render(Graphics g) {
	}
	
	public double getX(){ return X; }
	public double getY(){ return Y; }
}