package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

import simpleAI.SimpleThrower;
import balls.*;
import project.main.Game.STATE;

public class ZombieThrower extends Enemy {
	private static Random r = new Random();
	private int ballType;
	
	//for dodging
	private int dodgeTimer = -1;
	private int dodgeMaxTime;
	private int dodgeCooldownTimer = -1;
	private int dodgeCooldownMaxTime;
	private boolean canDodge = true;
	private double dodgeVel = 7;
	private int dodgeX;
	private int dodgeY;
	
	public ZombieThrower(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.ZombieThrower;
		ai = new SimpleThrower();
		setHealth(150 + 3 * hud.getLevel());
		setMaxHealth(150 + 3 * hud.getLevel());
		//setVel(7);
		
		//sets some variables
		stunMaxTime = 12;
		pre_atkMaxTime = 15;
		atkCooldownMaxTime = 225;
		dodgeMaxTime = 6;
		dodgeCooldownMaxTime = 24;
		atkDMG = 7;
		
		//lucky draw
		ballType = r.nextInt(7); //0-6 inclusive
	}
	
	private Rectangle getDodgeBounds()
	{
		return new Rectangle((int)winX - 34, (int)winY - 34, 100, 100);
	}
	
	protected boolean checkIfDodge()
	{
		for (int i = Game.handler.balls.size() - 1; i >= 0; i--){
			Ball tempObject = Game.handler.balls.get(i);
			//early return if game paused
			if (tempObject == null || Game.gameState != STATE.Game){
				return false;
			}
			
			if (tempObject.getId() == ID.WaterBall || tempObject.getId() == ID.FireBall || tempObject.getId() == ID.RockBall || tempObject.getId() == ID.FluxBall || tempObject.getId() == ID.LifeBall || tempObject.getId() == ID.CrystalBall || tempObject.getId() == ID.MysteryBall){
				if (getDodgeBounds().intersects(tempObject.getBounds())){ //this means the ball is close enough to warrant dodging
					//angles
					double dotProduct = ((x - 16 - tempObject.getX()) * tempObject.getVelX()) + ((y - 16 - tempObject.getY()) * tempObject.getVelY()); //dot product is cos of angle
					double angle =  Math.atan2(y - 16 - tempObject.getY(), x - 16 - tempObject.getX()) - Math.atan2(y - 16 - tempObject.getY(), tempObject.getVelX());
					
					//perpendicular line
					double xMove = -tempObject.getVelY();
					double yMove = tempObject.getVelX();
					
					//sets up values for priming
					dodgeX = (int)(dodgeVel * (xMove / tempObject.getVel()) * (angle / Math.abs(angle)) * (r.nextInt(2) * 2 - 1));
					dodgeY = (int)(dodgeVel * (yMove / tempObject.getVel()) * (angle / Math.abs(angle)) * (r.nextInt(2) * 2 - 1));
					
					return true;
				}
			}
		}
		return false;
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
		
		if (distToPlayer <= 225.0) //too close distance
		{
			this.conditions.add("PLAYER_TOO_CLOSE");
		}
		else if (distToPlayer <= 375.0) //attack distance
		{
			this.conditions.add("PLAYER_THROW_RANGE");
		}
		else if (distToPlayer <= 1800.0) //in range
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
		if (canDodge)
		{
	if (checkIfDodge())
			{
				this.conditions.add("PROJECTILLE_IN_RANGE");
			}
		}
	}
	
	protected void readActiveEffects()
	{
		this.effects = ai.getFX();
		for (String s : this.effects)
		{
			if (s.equals("STRAIGHT_LINE_MOVE") && dodgeTimer == -1) //only runs if not dodging
			{
				move(); //last possible state, least important, method will almost always end before this
			}
			else if (s.equals("BACK_UP") && dodgeTimer == -1) //only runs if not dodging
			{
				backUp();
			}
			else if (s.equals("THROW") && dodgeTimer == -1) //only runs if not dodging
			{
				attack();
			}
			else if (s.equals("DODGE"))
			{
				dodge();
				return; //can't do anything after stun
			}
			else if (s.equals("STUN_LOCK"))
			{
				stunMove();
				return; //can't do anything after stun
			}
		}
	}
	
	public void tick()
	{
		/*
		super.tick();
		super.move();
		super.collision();
		*/
		if (Game.gameState != STATE.Game){
			return;
		}
		super.tick();
	    super.collision();
		updateConditions();
		ai.runStateUpdate(conditions);
		readActiveEffects();
		countDownTimers();
	}
	
	public void stunMove()
	{
		super.stunMove();
		dodgeTimer = -1;
	}
	
	public void backUp()
	{
		this.velX = vel * (xDiff / distToPlayer);
		this.velY = vel * (yDiff / distToPlayer);
		
		if (isMoving){
			x -= velX;
			y -= velY;
		}
	}
	
	public void dodge() //primes for dodging
	{
		dodgeTimer = dodgeMaxTime;
		dodgeCooldownTimer = dodgeCooldownMaxTime;
		canDodge = false;
	}
	
	public void dodgeMove()
	{
		x += dodgeX;
		y += dodgeY;
	}
	
	public void attack()
	{
		if (!isAttacking)
		{
			//primes attack
			isAttacking = true;
			
			//activates timers
			pre_atkTimer = pre_atkMaxTime;
			atkCooldownTimer = atkCooldownMaxTime;
		}
	}
	
	public void countDownTimers()
	{
		//attacks
		if (pre_atkTimer != -1) //pre_atkTimer counting...
		{
			if (pre_atkTimer == 0) //attacking
			{
				switch (ballType)
				{
				case 0: //water
					Game.handler.enemyBalls.add(new WaterBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				case 1: //rock
					Game.handler.enemyBalls.add(new RockBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				case 2: //fire
					Game.handler.enemyBalls.add(new FireBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				case 3: //life
					Game.handler.enemyBalls.add(new LifeBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				case 4: //flux
					Game.handler.enemyBalls.add(new FluxBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				case 5: //crystal
					Game.handler.enemyBalls.add(new CrystalBall(x + 16, y + 16, xDiff, yDiff, Game.player));
					break;
				default: //mystery
					Game.handler.enemyBalls.add(new MysteryBall(x + 16, y + 16, xDiff, yDiff, Game.player));
				}
			}
			pre_atkTimer -= 1;
		}
		else if (atkCooldownTimer != -1)//cooldowntimer counting ...
		{
			if (atkCooldownTimer == 0)
			{
				isAttacking = false;
			}
			atkCooldownTimer -= 1;
		}
		
		//dodges
		if (dodgeTimer != -1)
		{
			dodgeMove();
			dodgeTimer--;
		}
		else if (dodgeCooldownTimer!= -1)
		{
			if (dodgeCooldownTimer == 0)
			{
				canDodge = true;
			}
			dodgeCooldownTimer--;
		}
	}
	
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/zombieThrower.png")).getImage();
		updateWindowCoordinates();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		super.render(g);
		
		//render ball only if pre_vAtkTimer is activated
		if (pre_atkTimer != -1) 
		{
			Image ball;
			switch (ballType)
			{
			case 0: //water
				ball = new ImageIcon(this.getClass().getResource("/waterball.png")).getImage();
				break;
			case 1: //rock
				ball = new ImageIcon(this.getClass().getResource("/rockball.png")).getImage();
				break;
			case 2: //fire
				ball = new ImageIcon(this.getClass().getResource("/fireball.png")).getImage();;
				break;
			case 3: //life
				ball = new ImageIcon(this.getClass().getResource("/lifeball.png")).getImage();;
				break;
			case 4: //flux
				ball = new ImageIcon(this.getClass().getResource("/fluxball.png")).getImage();
				break;
			case 5: //crystal
				ball = new ImageIcon(this.getClass().getResource("/crystalball.png")).getImage();
				break;
			default: //mystery
				ball = new ImageIcon(this.getClass().getResource("/mysteryball.png")).getImage();
			}
			g.drawImage(ball, (int)winX - 8, (int)winY - 8, (int)winX + 56, (int)winY + 56, 0, 0, 32, 32, null);
		}
	}
}
