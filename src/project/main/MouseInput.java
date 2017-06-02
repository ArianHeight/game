package project.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import project.main.Game.STATE;

public class MouseInput extends MouseAdapter {
	
	private Game game;
	private Player player;
	
	public MouseInput(Game game, Handler handler, Player p, HUD hud){
		this.game = game;
		this.player = p;
	}
	
	public void mousePressed(MouseEvent e){
		double mx = e.getX();
		double my = e.getY();
		
		if (Game.gameState == STATE.Menu){
			// clicks play
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 150, 200, 64)){
				Game.gameState = STATE.Game;
			}
			// clicks help
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 250, 200, 64)){
				Game.gameState = STATE.Help;
			}
			// clicks balls
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)){
				Game.gameState = STATE.BallsMenu;
			}
			// clicks balls
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 450, 200, 64)){
				Game.gameState = STATE.ZombieMenu;
			}
		}
		// clicks back
		if (Game.gameState == STATE.Help){
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 580, 200, 64)){
				Game.gameState = STATE.Menu;
				return;
			}
		}
		if (Game.gameState == STATE.BallsMenu){
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 580, 200, 64)){
				Game.gameState = STATE.Menu;
				return;
			}
		}
		if (Game.gameState == STATE.ZombieMenu){
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 580, 200, 64)){
				Game.gameState = STATE.Menu;
				return;
			}
		}
		if (Game.gameState == STATE.End){
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 580, 200, 64)){
				game.reset();
				return;
			}
		}
		
		// in the game; adds a projectile when a position on the window is clicked
		
		if (Game.gameState == STATE.Game){
			//double xVal = (mx - (player.x) + Game.camera.X - Game.WIDTH / 2);
			//double yVal = (my - (player.y) + Game.camera.Y - Game.HEIGHT / 2);
			double xVal = (mx - player.getWinX());
			double yVal = (my - player.getWinY());
			
			//System.out.println(xVal + " " + yVal + " " + game.camera.X + " " + game.camera.Y);
			//System.out.println("Player X: " + player.x + " Player Y: " + player.y);
			//System.out.println("Camera X: " + game.camera.X + " Camera Y: " + game.camera.Y);
			

			//handler.addObject(new Ball(player.x + game.camera.X, player.y + game.camera.Y, ID.Ball, xVal, yVal));
			
			//handler.addObject(new Ball(game.camera.X + Game.WIDTH / 2, game.camera.Y + Game.HEIGHT / 2, ID.Ball, xVal, yVal, player));
			Game.generateBall(player.x + 16, player.y + 16, xVal, yVal, player);
			//handler.addObject(new Ball(player.x, player.y, ID.Ball, xVal, yVal));
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	/**
	 * This method determines if a specific point on the window is bounded by a rectangle. 
	 */	
	private boolean mouseOver(double mx, double my, double x, double y, int width, int height){
		return (mx > x && mx < x + width && my > y && my < y + height);
	}
	
	public void changePlayer(Player p){
		player = p;
	}
}
