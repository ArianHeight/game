package project.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import project.main.Game.STATE;



public class KeyboardInput extends KeyAdapter {

 
	/**
	* This method gets called whenever a key is pressed. It sets the player's velX and velY accordingly. 
	*/
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if (Game.gameState == STATE.Game){
			Player p = Game.player;
			double vel = p.getVel();
			if (key == KeyEvent.VK_UP){
				p.setVelY(-vel);
			}
			if (key == KeyEvent.VK_DOWN){
				p.setVelY(vel);
			}
			if (key == KeyEvent.VK_RIGHT){
				p.setVelX(vel);
			}
			if (key == KeyEvent.VK_LEFT){
				p.setVelX(-vel);
			}
			if (key == KeyEvent.VK_W){
				p.setVelY(-vel);
			}
			if (key == KeyEvent.VK_S){
				p.setVelY(vel);
			}
			if (key == KeyEvent.VK_D){
				p.setVelX(vel);
			}
			if (key == KeyEvent.VK_A){
				p.setVelX(-vel);
			}
			if (key == KeyEvent.VK_SPACE){
				//System.out.println("spiral");
				p.spiral();
			}
		}
		if (key == KeyEvent.VK_U){
			//System.out.println(Game.gameState);
		    if (Game.gameState == STATE.Game){
			    Game.gameState = STATE.UpgradeStore;
		    }
		    else if (Game.gameState == STATE.UpgradeStore){
		 	   Game.gameState = STATE.Game;
		    }
	   }
	}
 
	/**
	* This method gets called whenever a key is released. It sets the player's velX and velY to zero accordingly.
	*/
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		Player p = Game.player;
		if (key == KeyEvent.VK_UP){
			p.setVelY(0);
		}
		if (key == KeyEvent.VK_DOWN){
			p.setVelY(0);
		}
		if (key == KeyEvent.VK_RIGHT){
			p.setVelX(0);
		}
		if (key == KeyEvent.VK_LEFT){
			p.setVelX(0);
		}
		if (key == KeyEvent.VK_W){
			p.setVelY(0);
		}
		if (key == KeyEvent.VK_S){
			p.setVelY(0);
		}
		if (key == KeyEvent.VK_D){
			p.setVelX(0);
		}
		if (key == KeyEvent.VK_A){
			p.setVelX(0);
		}
		// quits the game if the Esc key is pressed
		if (key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}
}
