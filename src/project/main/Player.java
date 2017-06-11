package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import balls.Ball;
import project.main.Game.STATE;

public class Player extends GameObject{
	protected double velX, velY;
	protected double vel;

	private HUD hud;
	
	private int spiralCounter;
	 
	public Player(int x, int y, HUD hud) {
		super(x, y);
		id = ID.Player;
		this.hud = hud;
		vel = 5;
		spiralCounter = 0;
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
			spiralCounter--;
			if (spiralCounter < 0){
				spiralCounter = 0;
			}
		}
		
		collision();
		
	}
	
	private void collision(){
		for (int i = Game.handler.enemies.size() - 1; i >= 0; i--){
			Enemy tempObject = Game.handler.enemies.get(i);
			
			if (tempObject.getId() == ID.Zombie || tempObject.getId() == ID.ZombieKnight)
			{
				if (tempObject.getIsAttacking())
				{
					if (getBounds().intersects(tempObject.getAtkBounds()))
					{
						HUD.HEALTH -= tempObject.getATKdmg();
						tempObject.getAtkBounds();
					}
				}
			}
		}
		
		for (VoidAttack v : Game.voidAreas)
		{
			if (v.isAttacking())
			{
				//dmg
				if (getBounds().intersects(v.getAtkBounds()))
				{
					HUD.HEALTH -= v.getDMG();
				}
			}
			if (v.isPulling())
			{
				//pull
				double pullX = v.getPullX();
				double pullY = v.getPullY();
				x += pullX;
				y += pullY;
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
		
		for (int i = Game.handler.enemyBalls.size() - 1; i >= 0; i--){
			Ball tempObject = Game.handler.enemyBalls.get(i);
			if (tempObject == null || Game.gameState != STATE.Game){
				return;
			}
			if (tempObject.getId() == ID.RockBall || tempObject.getId() == ID.LifeBall){
				if (getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= (int)(((Ball)tempObject).getPower()); //balls have normal dmg on player
					
					Game.handler.enemyBalls.remove(tempObject);
				}
			}
			else if (tempObject.getId() == ID.WaterBall || tempObject.getId() == ID.FireBall || tempObject.getId() == ID.CrystalBall){
				if (getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= (int)(((Ball)tempObject).getPower() * 0.2); //balls have 20% effect on player
					
					Game.handler.enemyBalls.remove(tempObject);
				}
			}
			else if (tempObject.getId() == ID.FluxBall){
				if (getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= (int)(((Ball)tempObject).getPower() * 0.05); //balls have 5% effect on player
					
					Game.handler.enemyBalls.remove(tempObject);
				}
			}
			else if (tempObject.getId() == ID.MysteryBall){
				if (getBounds().intersects(tempObject.getBounds())){
					HUD.HEALTH -= (int)(((Ball)tempObject).getPower() * 0.00035); //balls have 0.035% effect on player
					
					Game.handler.enemyBalls.remove(tempObject);
				}
			}
		}
	}
	
	public void spiral(){
		if (spiralCounter > 0){
			return;
		}
		for (int i = 0; i < 360; i += 10){
			Game.generateBall(x + 16, y + 16, i * 2 * Math.PI / 360, this);
		}
		spiralCounter = 2;
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
	
	public int getSpiralCounter(){
		return spiralCounter;
	}
}
