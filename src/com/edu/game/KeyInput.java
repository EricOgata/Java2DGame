package com.edu.game;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import com.edu.game.Game.STATE;
import com.edu.gameObjects.Background;
import com.edu.gameObjects.GameObject;
import com.edu.gameObjects.ID;
import com.edu.gameObjects.Particle;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private Game game;
	
	public KeyInput(Handler handler, Game game){
		this.handler = handler;
		this.game = game;
		//iniciando keys.
		/* keyDown[0] = W/UP
		 * keyDown[1] = S/DOWN
		 * keyDown[3] = D/Right
		 * keyDown[4] = A/Left
		 * */
		for(int i = 0; i < keyDown.length; i++){
			keyDown[i] = false;
		}
	}
	
	private void update(){
		if(game.gameState == STATE.Game){
			String exibe = "{";
			for (boolean b : keyDown) {
				exibe += (b == true) ? "true, ": "false, ";
			}
			exibe += "}";
			//System.out.println(exibe);
				
			for(int i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get(i);
				//System.out.println(tempObject.getId());
				if(tempObject.getId() == ID.Player){
					tempObject.setVelY(0);
					tempObject.setVelX(0);

					if(Arrays.equals(keyDown, new boolean[]{true, false, false, false})){ tempObject.setVelY(-5); } // norte
					else if(Arrays.equals(keyDown, new boolean[]{false, true, false, false})){ tempObject.setVelY(5); } // Sul
					else if(Arrays.equals(keyDown, new boolean[]{false, false, true, false})){ tempObject.setVelX(5);} // Leste
					else if(Arrays.equals(keyDown, new boolean[]{false, false, false, true})){ tempObject.setVelX(-5);} // Oeste
					else if(Arrays.equals(keyDown, new boolean[]{true, false, true, false})){ tempObject.setVelY(-5); tempObject.setVelX(5); } // nordeste
					else if(Arrays.equals(keyDown, new boolean[]{true, false, false, true})){ tempObject.setVelY(-5); tempObject.setVelX(-5); } // noroeste
					else if(Arrays.equals(keyDown, new boolean[]{false, true, true, false})){ tempObject.setVelY(5); tempObject.setVelX(5);} // sudeste
					else if(Arrays.equals(keyDown, new boolean[]{false, true, false, true})){ tempObject.setVelY(5); tempObject.setVelX(-5); } // sudoeste				
				}
			}
		}		
	}
	
	public void keyPressed(KeyEvent e){
		
		/*
		switch (e.getKeyCode()) {
			case (KeyEvent.VK_W) : case(KeyEvent.VK_UP): keyDown[0] = true; break;
			case (KeyEvent.VK_S) : case(KeyEvent.VK_DOWN): keyDown[1] = true; break;
			case (KeyEvent.VK_D) : case(KeyEvent.VK_RIGHT): keyDown[2] = true; break;
			case (KeyEvent.VK_A) : case(KeyEvent.VK_LEFT): keyDown[3] = true; break;
			case (KeyEvent.VK_ESCAPE) : case(KeyEvent.VK_END): System.exit(0); break;			
		}
		*/
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_W && key == KeyEvent.VK_UP ) { keyDown[0] = true;}
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S && key == KeyEvent.VK_DOWN) { keyDown[1] = true;}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D && key == KeyEvent.VK_RIGHT) {keyDown[2] = true;}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A && key == KeyEvent.VK_LEFT) { keyDown[3] = true;}
		if(key == KeyEvent.VK_SPACE){
			if(HUD.getBombs() > 0){
				HUD.removeBomb();
				
				handler.removeAll(ID.BasicEnemy);
				handler.removeAll(ID.FastEnemy);
				handler.removeAll(ID.SmartEnemy);
				handler.addObject(new Background(0, 0, ID.Background, handler));
				
			}
		}
		if(key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_END) {
			/*
			System.out.println("Lista de Game Objects Atual");
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				System.out.println("GameObj N"+i+": "+tempObject.getId());
			}
			*/
			game.gameState = STATE.Menu;
		}
		
		update();
	}
	
	public void keyReleased(KeyEvent e){
		
		/*
		switch (e.getKeyCode()) {
			case (KeyEvent.VK_W) : case(KeyEvent.VK_UP): keyDown[0] = false; break;
			case (KeyEvent.VK_S) : case(KeyEvent.VK_DOWN): keyDown[1] = false; break;
			case (KeyEvent.VK_D) : case(KeyEvent.VK_RIGHT): keyDown[2] = false; break;
			case (KeyEvent.VK_A) : case(KeyEvent.VK_LEFT): keyDown[3] = false; break;
			case (KeyEvent.VK_ESCAPE) : case(KeyEvent.VK_END): System.exit(0); break;			
		}
		*/
		
		int key = e.getKeyCode();				
		//Key Events for player 1
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) keyDown[0] = false;//tempObject.setVelY(0);
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) keyDown[1] = false;//tempObject.setVelY(0);
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) keyDown[2] = false;//tempObject.setVelX(0);
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) keyDown[3] = false;//tempObject.setVelX(0);
		
		update();
	}	
}
