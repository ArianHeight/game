package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import simpleAI.SimpleChaser;

public class Zombie extends Enemy {

	private static int atkDimensions = 30; //square dimensions
	private static int delayedX;
	private static int delayedY;
	
	public Zombie(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.Zombie;
		ai = new SimpleChaser();
		setHealth(100 + 2 * hud.getLevel());
		setMaxHealth(100 + 2 * hud.getLevel());
		
		//sets some variables
		stunMaxTime = 20;
		pre_atkMaxTime = 20;
		atkCooldownMaxTime = 100;
		atkDMG = 10;
	}
	
	protected void updateConditions()
	{
		this.conditions.clear();
		//health
		if (this.health > 0)
		{
			this.conditions.add("HAS_HEALTH");
		}
		else
		{
			this.conditions.add("NO_HEALTH");
		}
		
		if (this.pre_atkTimer != -1) //player is currently attacking
		{
			this.conditions.add("PLAYER_ATKTIMER_ACTIVE");
		}
		else if (distToPlayer <= 50.0) //attack distance
		{
			this.conditions.add("PLAYER_SWIPE_RANGE");
		}
		else if (distToPlayer <= 1500.0) //in range
		{
			this.conditions.add("PLAYER_IN_RANGE");
		}
		else //totally not in range like at all
		{
			this.conditions.add("PLAYER_OUT_RANGE");
		}
		
		//conditions
		if (stunTimer != -1)
		{
			this.conditions.add("HIT");
		}
	}
	
	protected void readActiveEffects()
	{
		this.effects = ai.getFX();
		for (String s : this.effects)
		{
			if (s.equals("STRAIGHT_LINE_MOVE"))
			{
				move(); //last possible state, least important, method will almost always end before this
			}
			else if (s.equals("FLAG_DMG_PLAYER"))
			{
				attack();
				return; //can't do anything after attack
			}
			else if (s.equals("STUN_LOCK"))
			{
				stunMove();
				return; //can't do anything after stun
			}
		}
	}
	
	public void attack()
	{
		//either prime timer or countdown timer
		if (pre_atkTimer == -1 && atkCooldownTimer == -1 && this.conditions.contains("PLAYER_SWIPE_RANGE")) //prime
		{
			pre_atkTimer = pre_atkMaxTime;
			atkCooldownTimer = atkCooldownMaxTime;
			
			//sets attack bounds
			double x = Game.player.getX() - Game.camera.X + Game.WIDTH / 2;
			double y = Game.player.getY() - Game.camera.Y + Game.HEIGHT / 2;
			delayedX = (int)x;
			delayedY = (int)y;
		}
		else if (pre_atkTimer != -1) //pre_atkTimer counting...
		{
			if (pre_atkTimer == 0) //attacking
			{
				isAttacking = true;
			}
			pre_atkTimer -= 1;
			Game.dmgAreas.add(new Rectangle(delayedX, delayedY, atkDimensions, atkDimensions));
		}
		else //cooldowntimer counting ...
		{
			atkCooldownTimer -= 1;
		}
	}
	
	public Rectangle getAtkBounds()
	{
		isAttacking = false; //everytime this method is run, assumes it is run by player
		return new Rectangle(delayedX, delayedY, atkDimensions, atkDimensions);
	}
	
	public void stunMove()
	{	
		x += stunX * Math.abs(xDiff / distToPlayer);
		y += stunY * Math.abs(yDiff / distToPlayer);

		if (stunTimer == 0)
		{
			stunX = 0;
			stunY = 0;
		}
		
		stunTimer -= 1;
	}
	
	public void tick() {
	    super.tick();
	    super.collision();
		updateConditions();
		ai.runStateUpdate(conditions);
		readActiveEffects();
	}
	
	
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/Zombie.png")).getImage();
		updateWindowCoordinates();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		
		//if (getAtkBounds().intersects(Game.player.getBounds())){
			//Image swoosh = new ImageIcon(this.getClass().getResource("/swoosh.png")).getImage();
			//g.drawImage(swoosh, delayedX, delayedY, delayedX + atkDimensions, delayedY + atkDimensions, 0, 0, 16, 16, null);
		//}
		
		super.render(g);
	}	
}
