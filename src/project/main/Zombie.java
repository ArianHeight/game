package project.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Zombie extends Enemy {

	public Zombie(int x, int y, Handler handler, HUD hud) {
		super(x, y, handler, hud);
		id = ID.Zombie;
		setHealth(100);
		setMaxHealth(100);
	}
	
	protected void updateConditions(boolean takenDamage)
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
	
	protected void readActiveEffects()
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
	
	public void tick() {
	    super.tick();
		updateConditions(super.collision());
		ai.runStateUpdate(conditions);
		readActiveEffects();
	}
	
	
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/Zombie.png")).getImage();
		updateWindowCoordinates();
		g.drawImage(img, (int)winX, (int)winY, (int)winX + 32, (int)winY + 32, 0, 0, 32, 32, null);
		
		super.render(g);
	}
}
