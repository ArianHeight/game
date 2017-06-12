package project.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class VoidAttack {
	
	private boolean attacking = false;
	private boolean pulling = false;
	private int voidRadius = 300;
	private int voidForce = 4; 
	private int pre_vAtkTimer = -1;
	private int pre_vAtkMaxTime = 60;
	private int voidAttackTimer = -1;
	private int voidAttackMaxTime = 350;
	private int vAtkCooldownTimer = -1;
	private int vAtkCooldownMaxTime = 800;
	private Image texture;
	
	private int x, y;
	private int winX, winY; //collisions all done in window space coords for some reason
	
	//vstr
	public VoidAttack(int x, int y, int winX, int winY)
	{
		this.winX = winX;
		this.winY = winY;
		this.x = x;
		this.y = y;
		
		//sets up timers
		pre_vAtkTimer = pre_vAtkMaxTime;
		voidAttackTimer = voidAttackMaxTime;
		vAtkCooldownTimer = vAtkCooldownMaxTime;
		
		//for visuals
		texture = new ImageIcon(this.getClass().getResource("/voidEffect.png")).getImage();
	}
	
	//timer stuff
	public void tick()
	{
		//primes attack
		if (pre_vAtkTimer == 0)
		{
			attacking = true;
			pulling = true;
			
			winX = (int)(x - Game.camera.X + Game.WIDTH / 2);
			winY = (int)(y - Game.camera.Y + Game.HEIGHT / 2);
		}
		//stops pull
		if (voidAttackTimer == 0)
		{
			pulling = false;
			//System.out.println("stops");
		}
		
		//timer
		if (pre_vAtkTimer != -1)
		{
			pre_vAtkTimer--;
			
			//updates winX, winY
			winX = (int)(x - Game.camera.X + Game.WIDTH / 2);
			winY = (int)(y - Game.camera.Y + Game.HEIGHT / 2);
		}
		else if (voidAttackTimer != -1) //actual pull begins
		{
			//timer
			voidAttackTimer--;
			
			//updates winX, winY
			winX = (int)(x - Game.camera.X + Game.WIDTH / 2);
			winY = (int)(y - Game.camera.Y + Game.HEIGHT / 2);
		}
		else if (vAtkCooldownTimer != -1) //counts down the cooldown
		{
			vAtkCooldownTimer--; //timer
		}
		
	}
	
	public Rectangle getAtkBounds()
	{
		attacking = false; //everytime this method is run, assumes it is run by player
		return new Rectangle(this.winX, this.winY, voidRadius, voidRadius);
	}
	
	public double getPull()
	{
		//distance to player
		double xDiff = Game.player.getX() - x;
		double yDiff = Game.player.getY() - y;
		double distToPlayer = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		
		if (distToPlayer > 0) //positive
		{
			return (double)this.voidForce / Math.max(distToPlayer, 0.8);
		}
		return (double)this.voidForce / Math.min(distToPlayer, -0.8); //negative
	}
	
	public double getPullX()
	{
		//player's distance to this
		double xDiff = x - Game.player.getX();
		
		//distance to player
		double yDiff = Game.player.getY() - y;
		double distToPlayer = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		
		return (double)this.voidForce * xDiff / Math.max(distToPlayer, 0.8);
	}
	
	public double getPullY()
	{
		//player's distance to this
		double yDiff = y - Game.player.getY();
		
		//distance to player
		double xDiff = Game.player.getX() - x;
		double distToPlayer = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		
		return (double)this.voidForce * yDiff / Math.max(distToPlayer, 0.8);
	}
	
	public void render(Graphics g)
	{
		if (pulling) //full size
		{
			int add = voidRadius / 2;
			g.drawImage(texture, (int)winX - add, (int)winY - add, (int)winX + add, (int)winY + add, 0, 0, 32, 32, null);
		}
		else if (pre_vAtkTimer != -1) //scales up
		{
			int add = (int)((double)(pre_vAtkMaxTime - pre_vAtkTimer) / pre_vAtkMaxTime * voidRadius / 2);
			g.drawImage(texture, (int)winX - add, (int)winY - add, (int)winX + add, (int)winY + add, 0, 0, 32, 32, null);
		}
	}
	
	public int getDMG() { return voidForce; }
	public boolean isAttacking() { return attacking; }
	public boolean isPulling() { return pulling; }
	public boolean isStillActive() { return (vAtkCooldownTimer != -1); }
}
