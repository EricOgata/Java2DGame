package com.edu.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.edu.game.Game.STATE;
import com.edu.gameObjects.BasicEnemy;
import com.edu.gameObjects.GameObject;
import com.edu.gameObjects.ID;
import com.edu.gameObjects.Player;
import com.edu.gameObjects.Spawn;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	
	Font font1;
	Font font2;
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
		font1 = new Font("arial", 1, 50);
		font2 = new Font("arial", 1, 25);
	}
	
	public void mousePressed(MouseEvent e){
		int mX = e.getX();
		int mY = e.getY();
		
		if(game.gameState == STATE.Menu){
			//play button
			if(moveOver(mX, mY, Game.WIDTH/2 - 100, Game.HEIGHT / 2 - 100 , 200, 64)){
				
				// Remove todos os objetos dentro de handler como pilha.
				for(int i = (handler.object.size() - 1); i >= 0 ; i--){
					handler.object.remove(i);
				}
				HUD.restartGame();
				
				
				Random r = new Random();
				game.gameState = STATE.Game;
				// Ordem que os objetos são instanciados influencia na ordem que eles são exibidos
				handler.addObject(new Player(Game.WIDTH/2 - 32, Game.HEIGHT/2 - 32, ID.Player, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			}
			
			//Help Button
			if(moveOver(mX, mY, Game.WIDTH/2 - 100, Game.HEIGHT / 2 , 200, 64)){
				game.gameState = STATE.Help;
			}
			
			//quit button
			if(moveOver(mX, mY, Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100 , 200, 64)){
				System.exit(0);
			}
		}else if(game.gameState == STATE.Help){
			//Help Button
			if(moveOver(mX, mY, Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100 , 200, 64)){
				game.gameState = STATE.Menu;
			}
		}else if(game.gameState == STATE.GameOver){
			//Help Button
			if(moveOver(mX, mY, Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100 , 200, 64)){
				game.gameState = STATE.Menu;
			}
		}

	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean moveOver(int mouseX, int mouseY, int x, int y, int width, int height){
		if(mouseX > x && mouseX < x + width){
			if(mouseY > y && mouseY < y + height){
				return true;
			}else return false;
		}else return false;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){		
		
		if(game.gameState == STATE.Menu){
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Shooter Cube", Game.WIDTH / 8 * 3 - 40, Game.HEIGHT / 2 - 150);
			
			
			g.setFont(font2);
			g.setColor(Color.darkGray);
			g.draw3DRect(Game.WIDTH/2 - 100, Game.HEIGHT / 2 - 100 , 200, 64, true);		
			g.draw3DRect(Game.WIDTH/2 - 100, Game.HEIGHT / 2 , 200, 64, true);		
			g.draw3DRect(Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100, 200, 64, true);
			
			g.setColor(Color.WHITE);
			g.drawString("Play", Game.WIDTH/2 - 25, Game.HEIGHT / 2 - 50);
			g.drawString("Help", Game.WIDTH/2 - 30, Game.HEIGHT / 2 + 45);
			g.drawString("Quit", Game.WIDTH/2 - 30, Game.HEIGHT / 2 + 145);
		}else if(game.gameState == STATE.Help){
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("Menu", Game.WIDTH / 8 * 3 + 51, Game.HEIGHT / 2 - 150);
			
			
			g.setFont(font2);
			g.setColor(Color.DARK_GRAY);
			g.draw3DRect(Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100, 200, 64, true);
			
			g.setColor(Color.WHITE);
			
			g.drawString("Utilize as teclas W,A,S,D ou os direcionais para andar.", Game.WIDTH/6, Game.HEIGHT / 2 - 25);	
			g.drawString("Utilize o mouse para atirar.", Game.WIDTH/3, Game.HEIGHT / 2);
			g.drawString("Toda vez que você atirar, você irá perder HP.", Game.WIDTH / 4, Game.HEIGHT / 2 + 25);
			
			
			g.drawString("Voltar", Game.WIDTH/2 - 40, Game.HEIGHT / 2 + 145);			
		}else if(game.gameState == STATE.GameOver){
			g.setColor(Color.RED);
			g.setFont(font1);
			g.drawString("GAME OVER", Game.WIDTH/2 - 150, Game.HEIGHT /2 - 100);
			g.setFont(font2);
			g.drawString("Score: "+HUD.getScore(), Game.WIDTH/2-60, Game.HEIGHT /2 - 50);
			g.drawString("Level: "+HUD.getLevel(), Game.WIDTH/2-60, Game.HEIGHT /2 - 25);
			g.draw3DRect(Game.WIDTH/2 - 100, Game.HEIGHT / 2 + 100, 200, 64, true);
			
			g.drawString("Menu Inicial", Game.WIDTH/2 - 70, Game.HEIGHT / 2 + 145);
		}

	}
}
