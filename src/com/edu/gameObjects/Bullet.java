package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.edu.game.Game;
import com.edu.game.HUD;
import com.edu.game.Handler;

public class Bullet extends GameObject{
	
	private Handler handler;
	
	
	private int colorR = 0;
	private int colorG = 0;
	private int colorB = 0;
	private boolean visible;
	private Random r;
	
	// posições para calculo de navegação
	private float distance;
	private float directionX;
	private float directionY;
	private float ballSpeed = 100;
	private float elapsed = 0.1f;
	
	public Bullet(int x, int y, ID id, Handler handler, float targetX, float targetY) {
		super(x, y, id);
		this.handler = handler;
		visible = true;
		r = new Random();
		
		float diffX = targetX - x;
		float diffY = targetY - y;
		
		distance = (float)Math.sqrt(Math.pow((double)diffX, 2) + Math.pow((double)diffY, 2));
		
		directionX = diffX / distance;
		directionY = diffY / distance;
		
		this.velX = directionX * ballSpeed * elapsed;
		this.velY = directionY * ballSpeed * elapsed;
		

		

	}
	
	@Override
	public void tick() {
		colorR = (int)Game.clamp((float)r.nextInt(255),0,255);
		colorG = (int)Game.clamp((float)r.nextInt(255),0,255);
		colorB = (int)Game.clamp((float)r.nextInt(255),0,255);
		
		this.x += velX;
		this.y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT|| x <= 0 || x >= Game.WIDTH) handler.removeObject(this);
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, new Color(colorR, colorG, colorB), 3, 3, 0.09f, handler));
		
		collision();
	}
	
	
	@Override
	public void render(Graphics g) {
		if(isVisible()){
			Graphics2D g2d = (Graphics2D) g;
			
			//g.setColor(Color.green);
			//g2d.draw(getBounds());		
			
			g.setColor(new Color(colorR, colorG, colorB));
			
			g.fillRect((int)x, (int)y, 3, 3);
		}		
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 3, 3);
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){			
			GameObject tempObject = handler.object.get(i);
			
			// Tiro desaparece ao colidir com uma parede.
			
			// colidindo com inimigos
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.Wall){ // Verifica se colidiu com um elemento com o ID<BasicEnemy>		
				if(getBounds().intersects(tempObject.getBounds())){
					// Collision code
					handler.removeObject(this);
					if(tempObject.getId() != ID.Wall){
						int size = 0;
						if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy){
							size = 16;
						}else {
							size = 10;
						}
						//handler.addObject(new Particle((int)tempObject.getX(), (int)tempObject.getY(), ID.Particle, 10, Color.WHITE, handler, size));
						handler.removeObject(tempObject);
					}
				}
			}			
		}
	}	
	
	public void setVisible(boolean status){
		this.visible = status;
	}
	
	public boolean isVisible(){
		return (visible == true)?true:false;
	}
	
}
