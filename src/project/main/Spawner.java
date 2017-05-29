package project.main;

import java.util.Random;

public class Spawner {
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private int spawnCounter;

	
	public Spawner(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
		spawnCounter = 40;
	}
	
	public void tick(){
		int time = hud.getTime();
		int x, y;
		if (r.nextInt(2) == 0){
			x = (int)(Game.camera.X - Game.WIDTH / 2);
		}
		else {
			x = (int)(Game.camera.X + Game.WIDTH / 2);
		}
		if (r.nextInt(2) == 0){
			y = (int)(Game.camera.Y - Game.HEIGHT / 2);
		}
		else {
			y = (int)(Game.camera.Y + Game.HEIGHT / 2);
		}
		if (time % spawnCounter == 0 && hud.getLevel() < 15){	
			
			//handler.addObject(new Zombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.Zombie, handler, hud));
			
			// spawns zombies at border of window
			handler.addObject(new Zombie(x, y, handler, hud));
		}
		if (time % spawnCounter == 0 && hud.getLevel() >= 10 && hud.getLevel() < 20){
			handler.addObject(new ZombieThrower(x, y, handler, hud));
		}
		if (time % spawnCounter == 0 && hud.getLevel() >= 20){
			handler.addObject(new ZombieKnight(x, y, handler, hud));
		}
		
		if (time % 200 == 0){
			hud.setLevel(hud.getLevel() + 1);
		}
		if (hud.getLevel() % 5 == 0 && time % 200 == 0 && spawnCounter >= 10){
			spawnCounter = (int)(spawnCounter * 0.8);
		}
	}
}