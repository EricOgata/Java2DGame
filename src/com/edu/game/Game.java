package com.edu.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import com.edu.gameObjects.Background;
import com.edu.gameObjects.BasicEnemy;
import com.edu.gameObjects.Bullet;
import com.edu.gameObjects.GameObject;
import com.edu.gameObjects.ID;
import com.edu.gameObjects.Player;
import com.edu.gameObjects.Spawn;
import com.edu.gameObjects.Wall;
import com.edu.sound.Sound;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 2880545970207596430L;
	
	/* Controle de Resolução */
	// https://en.wikipedia.org/wiki/List_of_common_resolutions
	//public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	public static final int WIDTH = 960, HEIGHT = WIDTH / 16 * 9; // PS VITA Resolution
	
	
	private Thread thread; // Thread aonde o jogo vai correr
	private boolean running = false;
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum STATE{
			Menu,
			Help,
			Game,
			GameOver
	};
	
	public STATE gameState = STATE.Menu;
	
	public Game(){
		new Window(WIDTH, HEIGHT, "Shooter Cube", this);
		handler = new Handler();
		hud = new HUD();

		menu = new Menu(this, handler);
		spawner = new Spawn(handler, hud);		
		this.requestFocus(); // requisita o focus da janela
		this.addKeyListener(new KeyInput(handler, this)); //listener de comandos
		this.addMouseListener(menu);
		this.addMouseListener(new MouseInput(handler));		
		
		if(gameState == STATE.Game){
			
			r = new Random();
			// Ordem que os objetos são instanciados influencia na ordem que eles são exibidos
			handler.addObject(new Player(WIDTH/2 - 32, HEIGHT/2 - 32, ID.Player, handler));
			
			for(int i = 0; i < 2; i++){
				handler.addObject(new BasicEnemy((int)clamp(r.nextInt(WIDTH), 1, WIDTH-32),(int)clamp(r.nextInt(HEIGHT), 1, HEIGHT-32), ID.BasicEnemy, handler));
				//handler.addObject(new BasicEnemy(WIDTH/2 + 32, HEIGHT/2 - 32, ID.BasicEnemy));
			}
		}
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; // 60 FPS
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1){
				tick(); // Tick means update();
				delta--;
			}
			if(running){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: "+frames);
				frames = 0;
			}
		}
		stop();
	}	
	
	private void tick(){
		/*Update each of classes*/
		handler.tick();
		switch (gameState) {
		case Game:
			hud.tick();
			spawner.tick();
			if(hud.HEALTH <= 0){
				gameState = STATE.GameOver;
			}
			break;
		case Menu:
			menu.tick();
			break;
		case Help:
			menu.tick();
			break;
		case GameOver:
			menu.tick();
			break;
		default:
			break;
		}		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		switch (gameState) {
		case Game:
			hud.render(g);
			break;
		case Menu:
			// Remove todos os objetos dentro de handler como pilha.
			for(int i = (handler.object.size() - 1); i >= 0 ; i--){
				handler.object.remove(i);
			}
			menu.render(g);
			break;
		case Help:
			menu.render(g);
			break;
		case GameOver:
			menu.render(g);
			break;
		default:
			break;
		}	
		
		g.dispose();
		bs.show();
		
	}
	
	// Função obriga que as variáveis fiquem dentro de um periodo, ex. minimo <= variável <= máximo. 
	public static float clamp(float var, float min, float max){
		if(var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	
	public static void main(String[] args) {
		new Game();
		Sound.sound1.loop();
	}

}
