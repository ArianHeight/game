package project.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1550691097823471818L;
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static final int tileSizeX = 64, tileSizeY = 64;
	private Thread thread;
	private boolean running = false;
 
 //private Random r;
 private Handler handler;
 
 public Game(){
  handler = new Handler();
  this.addKeyListener(new KeyboardInput(handler));
  new Window(WIDTH, HEIGHT, "Game", this);
  /*
  r = new Random();
  
  for (int i = 0; i < 200; i++){
   handler.addObject(new Player(0, 0, ID.Player));
  }
  */
  handler.addObject(new Player(WIDTH/2 - 32, HEIGHT/2 - 32, ID.Player));
  handler.addObject(new Coin(20, 20, ID.Coin));
  //handler.addObject(new Player(WIDTH/2 + 32, HEIGHT/2 - 32, ID.Player2));
  //for (int i = 0; i < 20; i++){
  // handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy));
  //}
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
  }catch(Exception e){
   e.printStackTrace();
  }
 }
 
 public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        //int frames = 0;
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

            //frames++;
                            
            if (System.currentTimeMillis() - timer > 1000)
            {
             timer += 1000;
             //frames = 0;
            }
        }
        stop();
    }
 
 private void tick(){
  handler.tick();
 }
 
 private void render(){
  BufferStrategy bs = this.getBufferStrategy();
  if (bs == null){
   this.createBufferStrategy(3);
   return;
  }
  
  Graphics g = bs.getDrawGraphics();
  
  g.setColor(Color.BLACK);
  g.fillRect(0, 0, WIDTH, HEIGHT);
  Image img1 = new ImageIcon(this.getClass().getResource("/blueGrass.png")).getImage();
  Image img2 = new ImageIcon(this.getClass().getResource("/stone.png")).getImage();

  for (int i = 1; i <= 2*WIDTH / tileSizeX + 1; i++){
	  for (int j = 1; j <= 2*HEIGHT / tileSizeY + 1; j++){
		  if ((i + j) % 2 == 0){
			  g.drawImage(img1, tileSizeX * (i-1), tileSizeX * (j-1), tileSizeX * i, tileSizeX * j, 0, 0, 32, 32, null);
		  }
		  else {
			  g.drawImage(img2, tileSizeY * (i-1), tileSizeY * (j-1), tileSizeY * i, tileSizeY * j, 0, 0, 32, 32, null);
		  }
	  }
  }
    
  
  handler.render(g);
  
  g.dispose();
  bs.show();
 }
 
 public static int clamp(int var, int min, int max){
  if (var >= max){
   return var = max;
  }
  else if (var <= min){
   return var = min;
  }
  return var;
 }
 
 	public static void main (String [] args){
		new Game();
    }
}
