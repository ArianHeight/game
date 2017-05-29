package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;

import project.main.Game.STATE;


public class Menu extends MouseAdapter {
	
	private Game game;
	//private Random r = new Random();
	
	public Menu(Game game){
		this.game = game;
	}
	/*
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Menu){
			// clicks play
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 150, 200, 64)){
				game.gameState = STATE.Game;
				for (int i = 0; i < 10; i++){
					System.out.println("Enemy aded");
		   			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
		  		}
			}
			// clicks help
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 250, 200, 64)){
				game.gameState = STATE.Help;
			}
		}
		// clicks back
		if (game.gameState == STATE.Help){
			if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)){
				game.gameState = STATE.Menu;
				return;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		return (mx > x && mx < x + width && my > y && my < y + height);
	}
	
	*/
	public void tick(){
		
	}
	
	public void render (Graphics g){
		if (game.gameState == STATE.Menu){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 15);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Menu", 570, 50);
			
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 150, 200, 64);
			g.drawString("Play", Game.WIDTH / 2 - 30, 190);
			
			g.drawRect(Game.WIDTH / 2 - 100, 250, 200, 64);
			g.drawString("Help", Game.WIDTH / 2 - 30, 290);

			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Balls", Game.WIDTH / 2 - 30, 390);
			
			g.drawRect(Game.WIDTH / 2 - 100, 450, 200, 64);
			g.drawString("Zombies", Game.WIDTH / 2 - 50, 490);
			
			g.setFont(font3);
			g.drawString("Game Produced by Edmond Xue and Sean Wang. Copyright 2017", Game.WIDTH / 2 - 240, 600);
		}
		else if (game.gameState == STATE.Help){
			Font font1 = new Font("arial", 1, 50);
			Font font2 = new Font("arial", 1, 30);
			Font font3 = new Font("arial", 1, 20);
			
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Help", 570, 50);
			
			g.setFont(font3);
			g.drawString("Use UDLR keys to move player. \nClick to shoot.", 400, 200);
			g.setFont(font2);
			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Back", 600, 390);
		}
	}	
}