package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpgradeStore extends MouseAdapter {
	private HUD hud;
	
	private int [] baseCosts = {10, 10, 10, 30, 30, 1337};
	private int [] upgradeIncreases = {15, 10, 10, 30, 30, 9001};
	
	private boolean [] ballPurchased = {false};
	private int [] ballCosts = {20};
	
	public UpgradeStore(HUD hud){
		this.hud = hud;
	}
	
	public void render(Graphics g){
		Color c = new Color(0f, 0f, 0f, 0.5f);
		g.setColor(c);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 48));
		g.drawString("Upgrades", Game.WIDTH / 2 - 100, 50);
		
		// box 1
		g.setFont(new Font("arial", 0, 12));
		g.drawString("Upgrade Health", 110, 120);
		g.drawString("Cost: " + baseCosts[0], 110, 140);
		g.drawRect(100, 100, 100, 80);
		
		// box 2
		g.drawString("Upgrade Speed", 260, 120);
		g.drawString("Cost: " + baseCosts[1], 260, 140);
		g.drawRect(250, 100, 100, 80);

		// box 3
		g.drawString("Refill Health", 410, 120);
		g.drawString("Cost: " + baseCosts[2], 410, 140);
		g.drawRect(400, 100, 100, 80);
		
		// box 4
		g.drawString("Upgrade Ball Speed", 560, 120);
		g.drawString("Cost: " + baseCosts[3], 560, 140);
		g.drawRect(550, 100, 100, 80);
		
		// box 5
		g.drawString("Upgrade Ball Power", 710, 120);
		g.drawString("Cost: " + baseCosts[4], 710, 140);
		g.drawRect(700, 100, 100, 80);
		
		// box 6
		g.drawString("Refill Health", 860, 120);
		g.drawString("Cost: " + baseCosts[5], 860, 140);
		g.drawRect(850, 100, 100, 80);
		
		
		
		
		
		g.setFont(new Font("arial", 0, 30));
		g.drawString("Balls", Game.WIDTH / 2 - 100, 250);
		
		// Ball box 1
		g.setFont(new Font("arial", 0, 12));
		g.drawString("Rock Ball", 110, 320);
		g.drawString("Cost: " + ballCosts[0], 110, 340);
		g.drawRect(100, 300, 100, 80);
		
		g.drawString("Coins: " + hud.getCoins(), Game.WIDTH / 2 - 50, 500);
		g.drawString("Press u to go back: ", Game.WIDTH / 2 - 50, 550);
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		// box 1
		if (mx >= 100 && mx <= 200){
			if (my >= 100 && my <= 180){
				if (hud.getCoins() >= baseCosts[0]){
					hud.setCoins(hud.getCoins() - baseCosts[0]);
					baseCosts[0] += upgradeIncreases[0];
					HUD.MAX_HEALTH += 50;
					HUD.HEALTH += 50;
				}
			}
		}
		
		// box 2
		if (mx >= 250 && mx <= 350){
			if (my >= 100 && my <= 180){
				if (hud.getCoins() >= baseCosts[1]){
					hud.setCoins(hud.getCoins() - baseCosts[1]);
					baseCosts[1] += upgradeIncreases[1];
					Game.player.setVel(Game.player.getVel() + 1);
				}
			}
		}
		
		// box 3
		if (mx >= 400 && mx <= 500){
			if (my >= 100 && my <= 180){
				if (hud.getCoins() >= baseCosts[2]){
					hud.setCoins(hud.getCoins() - baseCosts[2]);
					HUD.HEALTH += HUD.MAX_HEALTH / 4;
				}
			}
		}
		
		// ball box 1
		if (mx >= 100 && mx <= 200){
			if (my >= 300 && my <= 380){
				if (hud.getCoins() >= ballCosts[0]){
					hud.setCoins(hud.getCoins() - ballCosts[0]);
					ballCosts[0] = 99999;
					ballPurchased[0] = true;
					Game.currentBall = ID.RockBall;
				}
			}
		}
	}
	
	public void reset(){
		
	}
}
