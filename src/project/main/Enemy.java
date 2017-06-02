package project.main;
import simpleAI.*;

import java.awt.Color;
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
	protected List<String> effects;
	protected FSM ai;
	
	private Handler handler;
	private int MAX_HEALTH;
	protected int health;
	private HUD hud;
	
	protected int stunTimer;
	
	public Enemy(int x, int y, Handler handler, HUD hud) {
		super(x, y);
		this.handler = handler;
		this.hud = hud;
		
		velX = 2;
		velY = 2;
		vel = Math.sqrt(velX * velX + velY * velY);
		
		this.conditions = new ArrayList<String>();
		this.ai = new SimpleChaser();
		this.effects = this.ai.getFX();
	}
	
	public Rectangle getBounds(){
		/*
		Camera c = Game.camera;
		return new Rectangle((int)(x - c.X), (int)(y - c.Y), 32, 32);
		*/
		return new Rectangle((int)winX, (int)winY, 32, 32);
	}
	
	protected boolean collision(){
		for (int i = handler.balls.size() - 1; i >= 0; i--){
			Ball tempObject = handler.balls.get(i);
			
			if (tempObject.getId() == ID.WaterBall || tempObject.getId() == ID.FireBall || tempObject.getId() == ID.RockBall || tempObject.getId() == ID.FluxBall || tempObject.getId() == ID.LifeBall || tempObject.getId() == ID.MysteryBall){
				if (getBounds().intersects(tempObject.getBounds())){
					health -= (int)((Ball)tempObject).getPower();
					if (!((Ball)tempObject).infinitePierce){
						handler.balls.remove(tempObject);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void tick() {
		if (health <= 0){
			handler.enemies.remove(this);
			handler.addObject(new Coin((int)x, (int)y));
			hud.setScore(hud.getScore() + 1);
			Game.spawner.decrementEnemiesLeft();
		}
	}
	
	//reads ai's effects
	//protected abstract void readActiveEffects();
	
	//updates condition list based on current surroundings
	//protected abstract void updateConditions(boolean takenDamage);
	
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
		
		x = Game.clamp(x, -1000, 2625);
		y = Game.clamp(y, -1000, 1500);
	}

	@Override
	public void render(Graphics g) {
		/*
		Image img = new ImageIcon(this.getClass().getResource("/Zombie.png")).getImage();
		//Camera c = Game.camera;
		updateWindowCoordinates();
		//g.drawImage(img, (int)(x - c.X + Game.WIDTH / 2), (int)(y - c.Y + Game.HEIGHT / 2), (int)(x + 32 - c.X + Game.WIDTH / 2), (int)(y + 32 - c.Y + Game.HEIGHT / 2), 0, 0, 32, 32, null);
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		*/
		// draw health bar
		
		g.setColor(Color.gray);
		g.fillRect((int)winX, (int)winY - 10, (int)32, 6);
		// changes color of health as it decreases from green (max health) to red (min health)
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int)winX, (int)winY - 10, (int)(health * 1.0 / MAX_HEALTH * 32), 6);
		g.setColor(Color.white);
		g.drawRect((int)winX, (int)winY - 10, (int)32, 6);
	}
	
	public void setVelX(double velX){ this.velX = velX;	}
	public void setVelY(double velY){ this.velY = velY;	}
	public void setHealth(int health){ this.health = health; }
	public int getHealth(){ return health; }
	public void setMaxHealth(int health){ this.MAX_HEALTH = health; }
	public int getMaxHealth(){ return MAX_HEALTH; }
}