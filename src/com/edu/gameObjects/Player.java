package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.edu.game.Game;
import com.edu.game.HUD;
import com.edu.game.Handler;

public class Player extends GameObject{
	
	Random r = new Random();
	Handler handler;
	private long lastTime;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;	
		this.lastTime = System.nanoTime() / 1000000; // ms;
	}

	public void tick() {
		
		x += velX;		
		y += velY;		
		
		x = Game.clamp(x, 0, Game.WIDTH - 37);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.WHITE, 32, 32, 0.1f, handler));
		
		collision();
		recoverHP();
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy){ // Verifica se colidiu com um elemento com o ID<BasicEnemy>		
				if(getBounds().intersects(tempObject.getBounds())){
					// Collision code
					HUD.HEALTH -= 5;
					//handler.removeObject(tempObject);
				}
			}			
		}
	}
	
	public void recoverHP(){
		if(HUD.HEALTH > 0){
			long actualTime = System.nanoTime() / 1000000; //ms
			long delta = actualTime - lastTime;
			if(delta > 1000){
				lastTime = System.nanoTime() / 1000000;
				HUD.HEALTH += 1;
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//g2d.setColor(Color.green);
		//g2d.draw(getBounds());
		
		g.setColor(Color.white);	
		g.fillRect((int)x, (int)y, 32, 32);
	}
	
	


}
