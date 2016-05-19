package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.edu.game.Game;
import com.edu.game.Handler;

public class Background extends GameObject{
	
	
	private int colorR = 0;
	private int colorG = 0;
	private int colorB = 0;
	private int cont = 0;
	private Random r;
	
	private Handler handler;
	
	public Background(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		r = new Random();
		velX = 0;
		velY = 0;
	}

	@Override
	public void tick() {
		colorR = (int)Game.clamp((float)r.nextInt(255),0,255);
		colorG = (int)Game.clamp((float)r.nextInt(255),0,255);
		colorB = (int)Game.clamp((float)r.nextInt(255),0,255);
		
		x += velX;
		y += velY;		
		
		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
		if(cont == 20){
			handler.removeAll(ID.Background);
		}else{
			cont++;
		}		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		//g.setColor(Color.green);
		//g2d.draw(getBounds());		
		
		g.setColor(new Color(colorR, colorG, colorB));
		g.fillRect((int)x, (int)y, Game.WIDTH, Game.HEIGHT);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Game.WIDTH, 64);
	}

}
