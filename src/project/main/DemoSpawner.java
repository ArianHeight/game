
package project.main;

import java.util.Random;

public class DemoSpawner {
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private int numberOfEnemies;

	
	public DemoSpawner(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
	}
	
	public int [] enemyCoordinates(){
		int x, y;
		int seed = r.nextInt(4);

		if (seed == 0){
			x = (int)(Game.camera.X - Game.WIDTH / 2) + r.nextInt(Game.WIDTH);
			y = (int)(Game.camera.Y - Game.HEIGHT / 2);
		}
		else if (seed == 1){
			x = (int)(Game.camera.X - Game.WIDTH / 2) + r.nextInt(Game.WIDTH);
			y = (int)(Game.camera.Y + Game.HEIGHT / 2);
		}
		else if (seed == 2){
			x = (int)(Game.camera.X - Game.WIDTH / 2);
			y = (int)(Game.camera.Y - Game.HEIGHT / 2) + r.nextInt(Game.HEIGHT);
		}
		else {
			x = (int)(Game.camera.X + Game.WIDTH / 2);
			y = (int)(Game.camera.Y - Game.HEIGHT / 2) + r.nextInt(Game.HEIGHT);
		}
		
		
		if (hud.getLevel() == 1){
			x += Game.WIDTH / 2 - 128;
			y += Game.HEIGHT / 2 - 72;
		}
		
		return new int [] {x, y};
	}
	
	public void tick(){	
		if (numberOfEnemies == 0){
			hud.setLevel(hud.getLevel() + 1);
			if (hud.getLevel() <= 2){
				for (int i = 0; i < hud.getLevel(); i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new Zombie(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
			if (hud.getLevel() >= 3 && hud.getLevel() <= 4){
				for (int i = 0; i < hud.getLevel() - 2; i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new ZombieThrower(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
			if (hud.getLevel() > 4){
				for (int i = 0; i < hud.getLevel() - 4; i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new ZombieKnight(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
			if (hud.getLevel() >= 7){
				for (int i = 0; i < hud.getLevel() - 6; i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new ZombieThrower(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
			if (hud.getLevel() >= 7){
				for (int i = 0; i < hud.getLevel() - 6; i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new Zombie(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
		}
	}
	
	public int getEnemiesLeft(){
		return numberOfEnemies;
	}
	public void decrementEnemiesLeft(){
		numberOfEnemies --;
	}
}