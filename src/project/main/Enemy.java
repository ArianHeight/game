package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class Enemy extends GameObject {
	protected double velX, velY;
	protected double vel;
	
	protected List<String> conditions;
	
	private Handler handler;
	private int health;
	private HUD hud;
	
	public Enemy(int x, int y, Handler handler, HUD hud) {
		super(x, y);
		this.handler = handler;
		this.hud = hud;
		
		velX = 2;
		velY = 2;
		vel = Math.sqrt(velX * velX + velY * velY);
		
		this.conditions = new ArrayList<String>();
	}
	
	public Rectangle getBounds(){
		/*
		Camera c = Game.camera;
		return new Rectangle((int)(x - c.X), (int)(y - c.Y), 32, 32);
		*/
		return new Rectangle((int)winX, (int)winY, 32, 32);
	}
	
	private void collision(){
		for (int i = handler.balls.size() - 1; i >= 0; i--){
			GameObject tempObject = handler.enemies.get(i);
			
			if (tempObject.getId() == ID.WaterBall || tempObject.getId() == ID.FireBall || tempObject.getId() == ID.RockBall){
				if (getBounds().intersects(tempObject.getBounds())){
					health -= (int)((Ball)tempObject).getPower();
					if (!((Ball)tempObject).infinitePierce){
						handler.balls.remove(tempObject);
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		move();
		
		if (health <= 0){
			handler.enemies.remove(this);
			handler.addObject(new Coin((int)x, (int)y));
			hud.setScore(hud.getScore() + 1);
		}
		collision();
	}
	
	//updates condition list based on current surroundings
	private void updateConditions()
	{
		//health
		if (this.health > 0)
		{
			if (!this.conditions.contains("HAS_HEALTH"))
			{
				this.conditions.add("HAS_HEALTH");
			}
		}
		else
		{
			if (this.conditions.contains("HAS_HEALTH"))
			{
				this.conditions.remove("HAS_HEALTH");
			}
			this.conditions.add("NO_HEALTH");
		}
		
		//distance to player
	}
	
	public void move(){
		double xDiff = Game.player.getX() - x;
		double yDiff = Game.player.getY() - y;
		double dist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		
		this.velX = vel * (xDiff / dist);
		this.velY = vel * (yDiff / dist);
		
		if (isMoving){
			x += velX;
			y += velY;
		}
	}

	@Override
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/Zombie.png")).getImage();
		//Camera c = Game.camera;
		updateWindowCoordinates();
		//g.drawImage(img, (int)(x - c.X + Game.WIDTH / 2), (int)(y - c.Y + Game.HEIGHT / 2), (int)(x + 32 - c.X + Game.WIDTH / 2), (int)(y + 32 - c.Y + Game.HEIGHT / 2), 0, 0, 32, 32, null);
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
	}
	
	public void setVelX(double velX){ this.velX = velX;	}
	public void setVelY(double velY){ this.velY = velY;	}
	public void setHealth(int health){ this.health = health; }
	public int getHealth(){ return health; }
}