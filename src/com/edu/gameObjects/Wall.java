package com.edu.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.edu.game.Handler;

public class Wall extends GameObject{
	
	private Handler handler;
	private int positions[][] = new int[4][2]; 
	private boolean moving;
	private float speed = 1000;
	private float elapsed = 0.01f;
	
	private float distance = 0;
	
	private float endX;
	private float endY;
	
	private float startX;
	private float startY;
	
	public Wall(int x, int y, ID id, Handler handler, int[][] matrix) {
		super(x, y, id);
		this.handler = handler;
		moving = false;
		this.velX = 0;
		this.velY = 0;
		this.positions = matrix;
	}

	@Override
	public void tick() {
		if(moving == true){
			this.x += velX * speed * elapsed;
			this.y += velY * speed * elapsed;
			if(Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2)) >= distance){
				x = endX;
				y = endY;
				moving = false;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		//g.setColor(Color.green);
		//g2d.draw(getBounds());
		
		g.setColor(new Color(129,108,91));
		g.fillRect((int)x,(int)y, 30, 30);	
		
	}
	
	public void moveWalls(int mazeNumb){
		startX = x;
		startY = y;
		
		endX = positions[mazeNumb][0];
		endY = positions[mazeNumb][1];
		
		distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
		velX = (endX - startX) / distance;
		velY = (endY - startY) / distance; 
		
		moving = true;		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y, 30, 30);
	}

	public int[][] getPositions() {
		return positions;
	}

	public void setPositions(int[][] positions) {
		this.positions = positions;
	}
	
	

}
