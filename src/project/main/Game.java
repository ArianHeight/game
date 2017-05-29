package project.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1550691097823471818L;
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static final int tileSizeX = 64, tileSizeY = 64;
	private Thread thread;
	private boolean running = false;
	
	public static Camera camera;
	public static Player player;
	
	public static Handler handler;
	private HUD hud;
	private Spawner spawner;
	
	private Menu menu;
	private BallsMenu bm;
	private ZombieMenu zm;
	private UpgradeStore us;
	private End e;
	
	public static ID currentBall;
	
	private MouseInput mi;
	
	// possible states for the game to be in
	public enum STATE {
		Menu,
		BallsMenu,
		ZombieMenu,
		Help,
		UpgradeStore,
		Game,
		End;
	};
	
	public static STATE gameState = STATE.Menu;
	
 
	public Game(){
		currentBall = ID.FireBall;
		handler = new Handler();
		hud = new HUD();
		
		player = new Player(WIDTH/2, HEIGHT/2, hud);
		camera = new Camera(player);
		player.updateWindowCoordinates();
		menu = new Menu(this);
		bm = new BallsMenu();
		zm = new ZombieMenu(this);
		us = new UpgradeStore(hud);
		
		spawner = new Spawner(handler, hud);
		
		mi = new MouseInput(this, handler, player, hud);
		this.addMouseListener(mi);
		this.addMouseListener(us);
		this.addKeyListener(new KeyboardInput());
		new Window(WIDTH, HEIGHT, "Zombie Shooter", this);
  
		/*
  		for (int i = 0; i < 0; i++){
   			handler.addObject(new Coin(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.Coin));
  		}
  		*/
	}
 
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
 
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void run()
    {
		
		this.requestFocus(); // need not to click on window to focus
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(running)
        {
        	long now = System.nanoTime();
        	delta += (now - lastTime) / ns;
        	lastTime = now;
        	while (delta >=1)
        	{
        		tick();
        	    delta--;
        	}
        	if(running){
        		render();
        	}
                            
            if (System.currentTimeMillis() - timer > 1000)
            {
            	timer += 1000;
            }
        }
        stop();
       
    }
 
	private void tick(){
		handler.tick();
		player.tick();
		if (gameState == STATE.Game){
			hud.tick();
			camera.tick();
			spawner.tick();
		}
		/*
		if (HUD.HEALTH <= 0){
			HUD.HEALTH = 100;
			gameState = STATE.End;
			e = new End(hud);
		}
		*/
	}
 
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(2);
			return;
		}
  
		Graphics g = bs.getDrawGraphics();
		
		// creates the tiled background
		Image img1 = new ImageIcon(this.getClass().getResource("/blueGrass.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/stone.png")).getImage();

		for (int i = -15; i <= 3*WIDTH / tileSizeX + 1; i++){
			for (int j = -15; j <= 3*HEIGHT / tileSizeY + 1; j++){
				Image img;
				if ((i + j) % 2 == 0){
					img = img1;
				}
				else {
					img = img2;
				}
				g.drawImage(img, tileSizeX * (i-1) - (int)camera.getX(), tileSizeY * (j-1) - (int)camera.getY(), tileSizeX * i - (int)camera.getX(), tileSizeY * j - (int)camera.getY(), 0, 0, 32, 32, null);
			}
		}
		
		player.render(g);
		handler.render(g);
		if (gameState == STATE.Game){
			hud.render(g);
		} 
		else if (gameState == STATE.Menu || gameState == STATE.Help){
			menu.render(g);
		}
		else if (gameState == STATE.BallsMenu){
			bm.render(g);
		}
		else if (gameState == STATE.ZombieMenu){
			zm.render(g);
		}
		if (gameState == STATE.End){
			e.render(g);
			handler.freezeObjects();
		} else {
			handler.unfreezeObjects();
		}
		
		if (gameState == STATE.UpgradeStore){
			us.render(g);
			handler.freezeObjects();
			player.freeze();
		} else {
			handler.unfreezeObjects();
			player.unFreeze();
		}
		

		g.dispose();
		bs.show();
	}
 
    public static double clamp(double var, double min, double max){
		if (var >= max){
			return var = max;
		}
		else if (var <= min){
			return var = min;
		}
		return var;
    }
    
    public void reset(){
    	currentBall = ID.FireBall;
    	handler.clearAll();
		hud.clearAll();
		
		player = new Player(WIDTH/2, HEIGHT/2, hud);
		camera = new Camera(player);
		player.updateWindowCoordinates();
		
		gameState = STATE.Menu;
		
		spawner = new Spawner(handler, hud);
		
		mi.changePlayer(player);
    }
    
    public static void generateBall(double x, double y, double xVal, double yVal, Player p){
    	if (currentBall == ID.WaterBall){
    		handler.addObject(new WaterBall(x, y, xVal, yVal, p));
    	}
    	else if (currentBall == ID.FireBall){
    		handler.addObject(new FireBall(x, y, xVal, yVal, p));
    	}
    	else if (currentBall == ID.RockBall){
    		handler.addObject(new RockBall(x, y, xVal, yVal, p));
    	}
    	/*
    	else if (currentBall == ID.LifeBall){
    		handler.addObject(new LifeBall(x, y, xVal, yVal, p));
    	}
    	else if (currentBall == ID.FluxBall){
    		handler.addObject(new FluxBall(x, y, xVal, yVal, p));
    	}
    	*/
    }
    
    public static void generateBall(double x, double y, double angle, Player p){
    	if (currentBall == ID.WaterBall){
    		handler.addObject(new WaterBall(x, y, angle, p));
    	}
    	else if (currentBall == ID.FireBall){
    		handler.addObject(new FireBall(x, y, angle, p));
    	}
    	else if (currentBall == ID.RockBall){
    		handler.addObject(new RockBall(x, y, angle, p));
    	}
    	/*
    	else if (currentBall == ID.LifeBall){
    		handler.addObject(new LifeBall(x, y, xVal, yVal, p));
    	}
    	else if (currentBall == ID.FluxBall){
    		handler.addObject(new FluxBall(x, y, xVal, yVal, p));
    	}
    	*/
    }
 
 	public static void main (String [] args){
		new Game();
    }
}
