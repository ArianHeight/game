package project.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import project.main.Game.STATE;

public class ResetButton extends MouseAdapter {
	
	private Game game;
	
	public ResetButton(Game g){
		this.game = g;
	}
	
	public void render(Graphics g){
		g.setColor(Color.red);
		g.setFont(new Font("arial", 0, 40));
		g.drawString("RESET", 1110, 75);
		g.drawRect(1100, 30, 150, 60);
		
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.gameState == STATE.Game){
			if (mx >= 1100 && mx <= 1250){
				if (my >= 30 && my <= 90){
					game.reset();
				}
			}
		}
	}
}