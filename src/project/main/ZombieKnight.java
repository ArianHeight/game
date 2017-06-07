package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import simpleAI.HeavyChaser;

public class ZombieKnight extends Enemy {
	
	private int atkDimensions = 30; //square dimensions
	private int delayedX;
	private int delayedY;
	
	//for special attack
	private boolean isSpecialAttacking = false;
	private static int voidRadius = 75;
	private static int voidForce = 7; 
	private static int pre_vAtkTimer = -1;
	private static int pre_vAtkMaxTime = 200;
	private static int voidAttackTimer = -1;
	private static int voidAttackMaxTime = 250;
	private static int vAtkCooldownTimer = -1;
	private static int vAtkCooldownMaxTime = 600;

	public ZombieKnight(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.ZombieKnight;
		ai = new HeavyChaser();
		setHealth(300);
		setMaxHealth(300);
		
		//sets some variables
		stunMaxTime = 9;
		pre_atkMaxTime = 50;
		atkCooldownMaxTime = 150;
		atkDMG = 20;
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
		
		//range & attack stuffs
		if (this.pre_atkTimer != -1) //player is currently attacking
		{
			this.conditions.add("PLAYER_ATKTIMER_ACTIVE");
		}
		if (this.pre_atkTimer != -1 || this.voidAttackTimer != -1)
		{
			this.conditions.add("SPECIAL_ACTIVATED");
		}
		else if (distToPlayer <= 35.0) //attack distance
		{
			this.conditions.add("PLAYER_SWIPE_RANGE");
			this.conditions.add("PLAYER_SUCK_RANGE");
		}
		else if (distToPlayer <= 70.0) //attack distance
		{
			this.conditions.add("PLAYER_SWIPE_RANGE");
		}
		else if (distToPlayer <= 1600.0) //in range
		{
			this.conditions.add("PLAYER_IN_RANGE");
		}
		else //totally not in range like at all
		{
			this.conditions.add("PLAYER_OUT_RANGE");
		}
		
		//conditions
		if (vAtkCooldownTimer == -1)
		{
			this.conditions.add("NO_VOID_ACTIVE");
		}
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
			else if (s.equals("FLAG_SUCK_ATTACK"))
			{
				specialAttack();
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
		if (isSpecialAttacking)
		{
			isSpecialAttacking = false;
			return new Rectangle((int)this.winX, (int)this.winY, voidRadius, voidRadius);
		}
		return new Rectangle(delayedX, delayedY, atkDimensions, atkDimensions);
	}
	
	public void stunMove()
	{	
		x += stunX * (xDiff / distToPlayer);
		y += stunY * (yDiff / distToPlayer);
		
		x = Game.clamp(x, -1000, 2625);
		y = Game.clamp(y, -1000, 1500);
		
		if (stunTimer == 0)
		{
			stunX = 0;
			stunY = 0;
		}
		
		stunTimer -= 1;
	}
	
	public void specialAttack()
	{
		//either prime timer or countdown timer
		if (pre_vAtkTimer == -1 && voidAttackTimer == -1 && this.conditions.contains("NO_VOID_ACTIVE")) //prime
		{
			pre_vAtkTimer = pre_vAtkMaxTime;
			voidAttackTimer = voidAttackMaxTime;
			vAtkCooldownTimer = vAtkCooldownMaxTime;
			
			//sets attack bounds
			double x = Game.player.getX() - Game.camera.X + Game.WIDTH / 2;
			double y = Game.player.getY() - Game.camera.Y + Game.HEIGHT / 2;
			delayedX = (int)x;
			delayedY = (int)y;
		}
		else if (pre_vAtkTimer != -1) //pre_atkTimer counting...
		{
			if (pre_vAtkTimer == 0) //attacking
			{
				isAttacking = true;
				isSpecialAttacking = true;
			}
			pre_vAtkTimer -= 1;
		}
		else if (voidAttackTimer != -1 && this.conditions.contains("SPECIAL_ACTIVATED"))
		{
			voidAttackTimer--;
			//add to special game variable for rendering later
			//Game.dmgAreas.add(new Rectangle(delayedX, delayedY, atkDimensions, atkDimensions));
		}
	}
	
	public void countDownSpecialCD()
	{
		if (this.voidAttackTimer == -1 && this.vAtkCooldownTimer > -1)
		{
			this.vAtkCooldownTimer--;
		}
	}
	
	public void tick()
	{
		/*
		super.tick();
	    super.collision();
		updateConditions();
		ai.runStateUpdate(conditions);
		readActiveEffects();
		countDownSpecialCD();*/
		
		move();
		super.tick();
		super.collision();
	}

	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/ZombieKnight.png")).getImage();
		//Camera c = Game.camera;
		updateWindowCoordinates();
		//g.drawImage(img, (int)(x - c.X + Game.WIDTH / 2), (int)(y - c.Y + Game.HEIGHT / 2), (int)(x + 32 - c.X + Game.WIDTH / 2), (int)(y + 32 - c.Y + Game.HEIGHT / 2), 0, 0, 32, 32, null);
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		super.render(g);
	}
}