package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.edu.game.Game;
import com.edu.game.Handler;

public class SmartEnemy extends GameObject{
	
	private Handler handler;
	private GameObject player;
	private Color color;
	
	public SmartEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
		}
		
		color = Color.MAGENTA;
		
		velX = 7;
		velY = 7;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 10, 10);
	}

	public void tick() {
		x += velX;
		y += velY;	
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float)Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()) );
		
		velX = (float) ( (-2.5 / distance ) * diffX );
		velY = (float) ( (-2.5 / distance ) * diffY );
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
		
		handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.MAGENTA, 10, 10, 0.03f, handler));
		
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		//g.setColor(Color.green);
		//g2d.draw(getBounds());
		
		g.setColor(color);
		g.fillRect((int) x, (int)y, 10, 10);		
	}
	
	public Color getColor(){
		return this.color;
	}

}
