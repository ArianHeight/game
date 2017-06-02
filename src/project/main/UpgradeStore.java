package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import project.main.Game.STATE;

public class UpgradeStore extends MouseAdapter {
	private HUD hud;
	
	private int [] baseCosts = {10, 10, 10, 30, 30, 1337};
	private int [] upgradeIncreases = {15, 10, 10, 30, 30, 9001};
	
	private boolean [] ballPurchased = {false, false, false, false, false};
	private int [] ballCosts = {20, 2, 5, 11, 2243};
	
	public UpgradeStore(HUD hud){
		this.hud = hud;
	}
	
	public void render(Graphics g){
		Color c = new Color(0f, 0f, 0f, 0.5f);
		g.setColor(c);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 48));
		g.drawString("Upgrades", Game.WIDTH / 2 - 100, 70);
		
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
		g.drawString("Upgrade Ball", 560, 120);
		g.drawString("Speed", 560, 135);
		g.drawString("Cost: " + baseCosts[3], 560, 155);
		g.drawRect(550, 100, 100, 80);
		
		// box 5
		g.drawString("Upgrade Ball", 710, 120);
		g.drawString("Power", 710, 135);
		g.drawString("Cost: " + baseCosts[4], 710, 155);
		g.drawRect(700, 100, 100, 80);
		
		// box 6
		g.drawString("Refill Health", 860, 120);
		g.drawString("Cost: " + baseCosts[5], 860, 140);
		g.drawRect(850, 100, 100, 80);
		
		
		
		
		
		g.setFont(new Font("arial", 0, 30));
		g.drawString("Balls", Game.WIDTH / 2 - 50, 250);
		
		for (int i = 1; i <= 5; i++){
			g.setFont(new Font("arial", 0, 12));
			
			if (i == 1){
				g.drawString("Rock Ball", 150 * i - 40, 320);
			}
			if (i == 2){
				g.drawString("Flux Ball", 150 * i - 40, 320);
			}
			if (i == 3){
				g.drawString("Fire Ball", 150 * i - 40, 320);
			}
			if (i == 4){
				g.drawString("Life Ball", 150 * i - 40, 320);
			}
			if (i == 5){
				g.drawString("??? Ball", 150 * i - 40, 320);
			}
			String cost;
			if (ballCosts[i - 1] == 99999){
				cost = "Press to Equip";
			}
			else {
				cost = "Cost: " + ballCosts[i-1];
			}
			g.drawString(cost, 150 * i - 40, 340);
			g.drawRect(150 * i - 50, 300, 100, 80);
		}
		
		/*
		// Ball box 1
		
		g.drawString("Rock Ball", 110, 320);
		g.drawString("Cost: " + ballCosts[0], 110, 340);
		g.drawRect(100, 300, 100, 80);
		
		// Ball box 2
		g.drawString("Flux Ball", 260, 320);
		g.drawString("Cost: " + ballCosts[1], 260, 340);
		g.drawRect(250, 300, 100, 80);
		
		g.drawString("Coins: " + hud.getCoins(), Game.WIDTH / 2 - 50, 500);
		g.drawString("Press u to go back: ", Game.WIDTH / 2 - 50, 550);
		g.drawString("Current Ball: " + Game.currentBall, Game.WIDTH / 2 - 50, 600);
		*/
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.gameState == STATE.UpgradeStore){
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
			
			for (int i = 1; i <= 5; i++){
				if (mx >= (150 * i - 50) && mx <= (150 * i + 50) && my >= 300 && my <= 380){
					if (i == 1){
						if (ballPurchased[0]){
							Game.currentBall = ID.RockBall;
						}
						else if (hud.getCoins() >= ballCosts[0]){
							hud.setCoins(hud.getCoins() - ballCosts[0]);
							ballCosts[0] = 99999;
							ballPurchased[0] = true;
							Game.currentBall = ID.RockBall;
						}
					}
					if (i == 2){
						if (ballPurchased[1]){
							Game.currentBall = ID.FluxBall;
						}
						else if (hud.getCoins() >= ballCosts[1]){
							hud.setCoins(hud.getCoins() - ballCosts[1]);
							ballCosts[1] = 99999;
							ballPurchased[1] = true;
							Game.currentBall = ID.FluxBall;
						}
					}
					if (i == 3){
						if (ballPurchased[2]){
							Game.currentBall = ID.FireBall;
						}
						else if (hud.getCoins() >= ballCosts[2]){
							hud.setCoins(hud.getCoins() - ballCosts[2]);
							ballCosts[2] = 99999;
							ballPurchased[2] = true;
							Game.currentBall = ID.FireBall;
						}
					}
					if (i == 4){
						if (ballPurchased[3]){
							Game.currentBall = ID.LifeBall;
						}
						else if (hud.getCoins() >= ballCosts[3]){
							hud.setCoins(hud.getCoins() - ballCosts[3]);
							ballCosts[3] = 99999;
							ballPurchased[3] = true;
							Game.currentBall = ID.LifeBall;
						}
					
					}
					if (i == 5){
						if (ballPurchased[4]){
							Game.currentBall = ID.MysteryBall;
						}
						else if (hud.getCoins() >= ballCosts[4]){
							hud.setCoins(hud.getCoins() - ballCosts[4]);
							ballCosts[4] = 99999;
							ballPurchased[4] = true;
							Game.currentBall = ID.MysteryBall;
						}
					}
				}
			}
			
			/*
			// ball box 1
			if (mx >= 100 && mx <= 200){
				if (my >= 300 && my <= 380){
					if (ballPurchased[0]){
						Game.currentBall = ID.RockBall;
					}
					else if (hud.getCoins() >= ballCosts[0]){
						hud.setCoins(hud.getCoins() - ballCosts[0]);
						ballCosts[0] = 99999;
						ballPurchased[0] = true;
						Game.currentBall = ID.RockBall;
					}
				}
			}
			
			// ball box 2
			if (mx >= 250 && mx <= 400){
				if (my >= 300 && my <= 380){
					if (ballPurchased[1]){
						Game.currentBall = ID.FluxBall;
					}
					else if (hud.getCoins() >= ballCosts[1]){
						hud.setCoins(hud.getCoins() - ballCosts[1]);
						ballCosts[1] = 99999;
						ballPurchased[1] = true;
						Game.currentBall = ID.FluxBall;
					}
				}
			}
			*/
		}			
	}
	
	public void reset(){
		int [] bc = {10, 10, 10, 30, 30, 1337};
		int [] ui = {15, 10, 10, 30, 30, 9001};
		
		boolean [] bp = {false, false, false, false, false};
		int [] bco = {20, 2, 5, 11, 2243};
		
		for (int i = 0; i < bc.length; i++){
			baseCosts[i] = bc[i];
		}
		for (int i = 0; i < ui.length; i++){
			upgradeIncreases[i] = ui[i];
		}
		for (int i = 0; i < bp.length; i++){
			ballPurchased[i] = bp[i];
		}
		for (int i = 0; i < bco.length; i++){
			ballCosts[i] = bco[i];
		}
	}
}
