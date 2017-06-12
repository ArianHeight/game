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
			if (key == KeyEvent.VK_UP){
				Game.k_up = true;
			}
			if (key == KeyEvent.VK_DOWN){
				Game.k_down = true;
			}
			if (key == KeyEvent.VK_RIGHT){
				Game.k_right = true;
			}
			if (key == KeyEvent.VK_LEFT){
				Game.k_left = true;
			}
			if (key == KeyEvent.VK_W){
				Game.k_w = true;
			}
			else if (key == KeyEvent.VK_S){
				Game.k_s = true;
			}
			if (key == KeyEvent.VK_D){
				Game.k_d = true;
			}
			else if (key == KeyEvent.VK_A){
				Game.k_a = true;
			}
			if (key == KeyEvent.VK_SPACE){
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
		if (key == KeyEvent.VK_UP){
			Game.k_up = false;
		}
		if (key == KeyEvent.VK_DOWN){
			Game.k_down = false;
		}
		if (key == KeyEvent.VK_RIGHT){
			Game.k_right = false;
		}
		if (key == KeyEvent.VK_LEFT){
			Game.k_left = false;
		}
		if (key == KeyEvent.VK_W){
			Game.k_w = false;
		}
		else if (key == KeyEvent.VK_S){
			Game.k_s = false;
		}
		if (key == KeyEvent.VK_D){
			Game.k_d = false;
		}
		else if (key == KeyEvent.VK_A){
			Game.k_a = false;
		}
	}
}
