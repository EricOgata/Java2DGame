package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.edu.game.Game;
import com.edu.game.HUD;
import com.edu.game.Handler;

public class BasicEnemy extends GameObject{
	
	private Handler handler;
	private Color color;
	
	public BasicEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 3;
		velY = 3;
		this.color = Color.RED;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y, 16, 16);
	}
	
	public void tick() {
		x += velX;
		y += velY;		
		
		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
		
		collision();
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.RED, 16, 16, 0.03f, handler));
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player || tempObject.getId() == ID.Wall){ // Verifica se colidiu com um elemento com o ID<BasicEnemy>
				
				if(getBounds().intersects(tempObject.getBounds())){
					// Collision code
					velX *= -1;
					velY *= -1;					
				}
			}			
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		//g.setColor(Color.green);
		//g2d.draw(getBounds());
		
		g.setColor(color);
		g.fillRect((int)x,(int)y, 16, 16);		
	}
	
	public Color getColor(){
		return this.color;
	}
	
}
