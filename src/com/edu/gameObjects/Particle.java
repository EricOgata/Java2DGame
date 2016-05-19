package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.edu.game.Game;
import com.edu.game.Handler;

/*Classe de particulas */
public class Particle extends GameObject{
	
	private int size;
	private int life;
	private Handler handler;
	private Color color;
	
	public Particle(int x, int y, ID id, int life, Color color, Handler handler, int size) {
		super(x, y, id);
		this.x = x + (size/2)/2;
		this.y = y + (size/2)/2;
		this.life = life;
		this.color = color;
		this.handler = handler;
		this.size = size / 2;
	}

	@Override
	public void tick() { // update
			x+= velX;
			x = Game.clamp(x, 0,  Game.WIDTH - 37);			
			y+= velY;
			y = Game.clamp(y, 0, Game.HEIGHT - 60);
			life --;
			
			
			if(life == 0){
				handler.removeObject(this);
				if(size > 0){
					handler.addObject(new Particle((int)x, (int)y, ID.Particle, 3, Color.WHITE, handler, size));
				}				
			}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int)x - size, (int)y - size, size, size);
		//g2d.dispose();
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
