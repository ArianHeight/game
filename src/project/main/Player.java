package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Player extends GameObject{
	protected double velX, velY;
	protected double vel;

	private HUD hud;
	 
	public Player(int x, int y, HUD hud) {
		super(x, y);
		id = ID.Player;
		this.hud = hud;
		vel = 5;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)winX, (int)winY, 32, 32);
	}
	
	public Rectangle getCollectionBounds(){
		return new Rectangle((int)winX - 32, (int)winY - 32, 96, 96);
	}
	
	@Override
	public void tick() {
		if (isMoving){
			x += velX;
			y += velY;
		}
		
		  
		x = Game.clamp(x, -1000, 2625);
		y = Game.clamp(y, -1000, 1500);
		
		collision();
		
	}
	
	private void collision(){
		for (int i = Game.handler.enemies.size() - 1; i >= 0; i--){
			Enemy tempObject = Game.handler.enemies.get(i);
			
			if (tempObject.getId() == ID.Zombie || tempObject.getId() == ID.ZombieKnight || tempObject.getId() == ID.ZombieThrower){
				if (getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= 1;
				}
			}
		}
		
		for (int i = Game.handler.collectibles.size() - 1; i >= 0; i--){
			Collectible tempObject = Game.handler.collectibles.get(i);
			if (tempObject.getId() == ID.Coin){
				if (getBounds().intersects(tempObject.getBounds())){
					hud.setCoins(hud.getCoins() + 1);
					Game.handler.collectibles.remove(tempObject);
				}
			}
		}
	}
	
	public void spiral(){
		//System.out.println(x + " " + y);
		for (int i = 0; i < 360; i += 10){
			Game.generateBall(x + 16, y + 16, i * 2 * Math.PI / 360, this);
		}
	}
	
	public void freeze(){
		isMoving = false;
	}
	
	public void unFreeze(){
		isMoving = true;
	}
	
	@Override
	public void render(Graphics g) {
		updateWindowCoordinates();
		//int pX = (int)(this.x - Game.camera.getX()) + (Game.WIDTH / 2) - 16;
		//int pY = (int)(this.y - Game.camera.getY()) + (Game.HEIGHT / 2) - 16;
		
		// image of player
		Image img = new ImageIcon(this.getClass().getResource("/player.png")).getImage();
		// draw player
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
	}
	
	// accessor methods
	public double getVelX(){ return velX; }
	public double getVelY(){ return velY; }
	
	// mutator methods
	public void setVelX(double velX){ this.velX = velX;	}
	public void setVelY(double velY){ this.velY = velY;	}
	public double getVel(){ return vel; }
	
	public void setVel(double vel){
		this.vel = vel;
		velX = velX * vel / this.vel;
		velY = velY * vel / this.vel;
	}
}
