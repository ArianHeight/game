package project.main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import project.main.Game.STATE;

public class Explosion extends GameObject {
	
	private int time;
	
	public Explosion(int x, int y, Handler handler, HUD hud) {
		super(x, y);
		time = 30;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)winX - 16, (int)winY - 16, 64, 64);
	}
	
	protected void collision(){
		for (int i = Game.handler.enemies.size() - 1; i >= 0; i--){
			Enemy tempObject = Game.handler.enemies.get(i);
			
			if (tempObject.getId() == ID.Zombie || tempObject.getId() == ID.ZombieKnight || tempObject.getId() == ID.ZombieThrower){
				if (getBounds().intersects(tempObject.getBounds())){
					tempObject.health -= 2;
				}
			}
		}
	}

	@Override
	public void tick() {
		if (time == 0){
			Game.handler.explosions.remove(this);
		}
		if (Game.gameState == STATE.Game){
			collision();
			time --;
		}
	}
	
	@Override
	public void render(Graphics g) {
		Image img = new ImageIcon(this.getClass().getResource("/bam.png")).getImage();
		updateWindowCoordinates();
		g.drawImage(img, (int)winX - 16, (int)winY - 16, (int)winX + 48, (int)winY + 48, 0, 0, 32, 32, null);
	}
}