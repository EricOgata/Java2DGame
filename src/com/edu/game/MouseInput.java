package com.edu.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.edu.gameObjects.Bullet;
import com.edu.gameObjects.GameObject;
import com.edu.gameObjects.ID;

public class MouseInput extends MouseAdapter{
	private int cont = 0;
	private Handler handler;
	private float mouseX;
	private float mouseY;
	
	public MouseInput(Handler handler) {
		this.handler = handler;
		mouseX = 0;
		mouseY = 0;
	}
	
	public void update(){
			for(int i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId() == ID.Player){
					handler.addObject(new Bullet((int)tempObject.getX() + 16, (int)tempObject.getY() + 16, ID.Bullet, handler, mouseX, mouseY));
					HUD.HEALTH --;
				}
			}
	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void mousePressed(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		update();
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
}
