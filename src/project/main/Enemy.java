package project.main;
import simpleAI.*;

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
	private int health;
	private HUD hud;
	
	private int stunTimer;
	
	public Enemy(int x, int y, Handler handler, HUD hud) {
		super(x, y);
		this.handler = handler;
		this.hud = hud;
		
		velX = 2;
		velY = 2;
		vel = Math.sqrt(velX * velX + velY * velY);
		
		this.conditions = new ArrayList<String>();
		this.ai = new simpleChaser();
		this.effects = this.ai.getFX();
	}
	
	public Rectangle getBounds(){
		/*
		Camera c = Game.camera;
		return new Rectangle((int)(x - c.X), (int)(y - c.Y), 32, 32);
		*/
		return new Rectangle((int)winX, (int)winY, 32, 32);
	}
	
	private boolean collision(){
		for (int i = handler.balls.size() - 1; i >= 0; i--){
			Ball tempObject = handler.balls.get(i);
			
			if (tempObject.getId() == ID.WaterBall || tempObject.getId() == ID.FireBall || tempObject.getId() == ID.RockBall || tempObject.getId() == ID.FluxBall){
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
		move();
		
		if (health <= 0){
			handler.enemies.remove(this);
			handler.addObject(new Coin((int)x, (int)y));
			hud.setScore(hud.getScore() + 1);
			Game.spawner.decrementEnemiesLeft();
		}
		
		collision();
		//updateConditions(collision());
		//ai.runStateUpdate(conditions);
		//System.out.println(this.ai.getFX());
		//readActiveEffects();
	}
	
	//reads ai's effects
	private void readActiveEffects()
	{
		this.effects = ai.getFX();
		for (String s : this.effects)
		{
			if (s.equals("STRAIGHT_LINE_MOVE"))
			{
				move();
			}
		}
	}
	
	//updates condition list based on current surroundings
	private void updateConditions(boolean takenDamage)
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
			if (!this.conditions.contains("NO_HEALTH"))
			{
				this.conditions.add("NO_HEALTH");
			}
		}
		
		//distance to player
		double xDiff = Game.player.getX() - x;
		double yDiff = Game.player.getY() - y;
		double dist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		if (dist <= 40.0) //attack distance
		{
			if (!this.conditions.contains("PLAYER_SWIPE_RANGE"))
			{
				this.conditions.add("PLAYER_SWIPE_RANGE");
			}
		}
		else if (dist <= 1000.0) //in range
		{
			if (!this.conditions.contains("PLAYER_IN_RANGE")) //adds precon
			{
				this.conditions.add("PLAYER_IN_RANGE");
			}
			if (this.conditions.contains("PLAYER_OUT_RANGE")) //deletes unrelative precons
			{
				this.conditions.remove("PLAYER_OUT_RANGE");
			}
			if (this.conditions.contains("PLAYER_SWIPE_RANGE"))
			{
				this.conditions.remove("PLAYER_SWIPE_RANGE");
			}
		}
		else //totally not in range like at all
		{
			if (!this.conditions.contains("PLAYER_OUT_RANGE")) //adds precon
			{
				this.conditions.add("PLAYER_OUT_RANGE");
			}
			if (this.conditions.contains("PLAYER_IN_RANGE")) //deletes unrelative precons
			{
				this.conditions.remove("PLAYER_IN_RANGE");
			}
			if (this.conditions.contains("PLAYER_SWIPE_RANGE"))
			{
				this.conditions.remove("PLAYER_SWIPE_RANGE");
			}
		}
		
		//conditions
		if (takenDamage)
		{
			if (!this.conditions.contains("HIT")) //adds precon
			{
				this.conditions.add("HIT");
			}
			if (this.stunTimer <= 0  && this.conditions.contains("HIT"))
			{
				this.conditions.remove("HIT");
			}
		}
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
		
		x = Game.clamp(x, -1000, 2625);
		y = Game.clamp(y, -1000, 1500);
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