package com.edu.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;

import com.edu.game.Game.STATE;

public class HUD {
	
	public static float MAX_HEALTH = 100;
	public static float HEALTH = 100;
	
	private float greenValue = 255;
	
	private static int score = 0;
	private static int level = 1;
	private static int bombs = 3;
	
	
	public void tick(){
		HEALTH = Game.clamp(HEALTH, 0, MAX_HEALTH); // valor de Health tem que ficar entre 0 e HP máximo		
		greenValue = HEALTH * 2;		
		greenValue = Game.clamp(greenValue, 0, 255); // valor de Green deve ficar entre 0 e 255
		score++;		
	}
	
	public void render(Graphics g){
		// fillRect(x, y, width, height);
		
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		
		g.setColor(new Color((int)(255 - greenValue), (int)greenValue, 0));
		g.fillRect((int)15, (int)15, (int) (HEALTH * 2), 32);
		
		g.setColor(Color.white);
		//g.drawRect(15, 15, (int) (HEALTH * 2), 32);
		
		g.drawString("Score: "+score, 15, 64);
		g.drawString("Level: "+level, 15, 80);
		g.drawString("Bombs: "+bombs, 15, 96);
	}

	public static int getScore(){
		return score;
	}
	
	public static void restartGame(){
		score = 0;
		HEALTH = 100;
		level = 1;
		bombs = 3;
	}
	
	public static void setScore(int value) {
		score = value;
	}
	
	public static void addScore(int value){
		score += value;
	}

	public static int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public static void addBomb(){
		bombs++;
	}
	
	public static void removeBomb(){
		bombs--;
	}
	public static int getBombs(){
		return bombs;
	}
	
}
