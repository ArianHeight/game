package project.main;

import java.util.Random;

public class Spawner {
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private int numberOfEnemies;

	
	public Spawner(Handler handler, HUD hud){
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
			if (hud.getLevel() % 49 == 0){
				for (int i = 0; i < 10 * hud.getLevel(); i++){
					int [] coord = enemyCoordinates();
					handler.addObject(new Zombie(coord[0], coord[1], handler, hud));
					numberOfEnemies ++;
				}
			}
			else {
				if (hud.getLevel() < 15){
					for (int i = 0; i < 2 * hud.getLevel(); i++){
						int [] coord = enemyCoordinates();
						handler.addObject(new Zombie(coord[0], coord[1], handler, hud));
						numberOfEnemies ++;
					}
				}
				if (hud.getLevel() >= 15){
					for (int i = 0; i < 3 * (hud.getLevel() - 15); i++){
						int [] coord = enemyCoordinates();
						handler.addObject(new ZombieThrower(coord[0], coord[1], handler, hud));
						numberOfEnemies ++;
					}
				}
				if (hud.getLevel() >= 30){
					for (int i = 0; i < 4 * (hud.getLevel() - 30); i++){
						int [] coord = enemyCoordinates();
						handler.addObject(new ZombieKnight(coord[0], coord[1], handler, hud));
						numberOfEnemies ++;
					}
				}
				if (hud.getLevel() % 50 == 0){
					for (int i = 0; i < 600 * hud.getLevel() / 50; i++){
						int [] coord = enemyCoordinates();
						handler.addObject(new ZombieKnight(coord[0], coord[1], handler, hud));
						handler.addObject(new Zombie(coord[0], coord[1], handler, hud));
						numberOfEnemies += 2;
					}
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
