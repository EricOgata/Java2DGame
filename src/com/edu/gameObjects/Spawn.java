package com.edu.gameObjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.plaf.SliderUI;

import com.edu.game.Game;
import com.edu.game.HUD;
import com.edu.game.Handler;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private int scoreKeep = 0;
	private Random r = new Random();
	int[][] maze;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
		

    }

	
	public char[][] generateMaze(int r, int c){
		// dimensions of generated maze
		int rows = r, columns = c;
		
		// build maze and initialize with only walls
		StringBuilder s = new StringBuilder(columns);
		for(int x = 0; x < columns; x++){
			s.append('*');
		}
		char[][] maz = new char[rows][columns];
		
		for(int x = 0; x<rows; x++)maz[x] = s.toString().toCharArray();
		
		// select random pointand open as start node
		Point st = new Point((int)(Math.random()*rows), (int)(Math.random()*columns), null);
		maz[st.r][st.c] = 'S';
		
		//iterate through direct neightbors of node
		ArrayList<Point> frontier = new ArrayList<Point>();
		for(int x = -1; x<=1; x++){
			for(int y=-1;y<=1;y++){
        		if(x==0&&y==0||x!=0&&y!=0)
        			continue;
        		try{
        			if(maz[st.r+x][st.c+y]=='.') continue;
        		}catch(Exception e){ // ignore ArrayIndexOutOfBounds
        			continue;
        		}
        		// add eligible points to frontier
        		frontier.add(new Point(st.r+x,st.c+y,st));
        	}
		}
		Point last=null;
        while(!frontier.isEmpty()){

        	// pick current node at random
        	Point cu = frontier.remove((int)(Math.random()*frontier.size()));
        	Point op = cu.opposite();
        	try{
        		// if both node and its opposite are walls
        		if(maz[cu.r][cu.c]=='*'){
        			if(maz[op.r][op.c]=='*'){

        				// open path between the nodes
        				maz[cu.r][cu.c]='.';
        				maz[op.r][op.c]='.';

        				// store last node in order to mark it later
        				last = op;

        				// iterate through direct neighbors of node, same as earlier
        				for(int x=-1;x<=1;x++)
				        	for(int y=-1;y<=1;y++){
				        		if(x==0&&y==0||x!=0&&y!=0)
				        			continue;
				        		try{
				        			if(maz[op.r+x][op.c+y]=='.') continue;
				        		}catch(Exception e){
				        			continue;
				        		}
				        		frontier.add(new Point(op.r+x,op.c+y,op));
				        	}
        			}
        		}
        	}catch(Exception e){ // ignore NullPointer and ArrayIndexOutOfBounds
        	}

        	// if algorithm has resolved, mark end node
        	if(frontier.isEmpty())
        		maz[last.r][last.c]='E';
        }
        
        return maz;
	}
	
	public void tick(){
		scoreKeep++;
		if(scoreKeep >= 500){
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);			
			
			
			/*
			for(int i = handler.object.size() - 1; i >= 0 ; i--){			
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId() == ID.Wall) handler.removeObject(tempObject);
			}
			
			char[][] maze = generateMaze(15, 30);

			int xInicial = 30;
			int yInicial = 30;
			
			for(int i = 0; i<15;i++){
				for(int j = 0 ;j<30;j++){
					if(maze[i][j] == '*'){
						handler.addObject(new Wall(xInicial, yInicial, ID.Wall, handler, new int[0][0]));
					}					
					xInicial += 30;
				}
				xInicial = 30;
				yInicial += 30;
			}
			*/
			
			int val = r.nextInt(101); // generates an number from 0 to 100			
			if(val >= 0 && val <= 80){ // chance de 80%
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
				if(val >= 0 && val <= 20){
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));	
				}
			}else{
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
			}
			val = r.nextInt(101); // generates an number from 0 to 100
			if(val >= 0 && val <= 40){ // chance de 40%
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
			}
			val = r.nextInt(101); // generates an number from 0 to 100
			if(val >= 0 && val <= 20){ // chance de 40%
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
				if(val == 1){ // Sudden Death
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
				}
			}
			/*
			// Spawnar novo inimigo a cada lvl.
			if(hud.getLevel() == 2){
				//handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			} else if(hud.getLevel() == 3){
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			} else if(hud.getLevel() == 4){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
			} else if(hud.getLevel() == 5){
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
			} 
			*/			
		}
	}
	
	static class Point{
    	Integer r;
    	Integer c;
    	Point parent;
    	public Point(int x, int y, Point p){
    		r=x;c=y;parent=p;
    	}
    	// compute opposite node given that it is in the other direction from the parent
    	public Point opposite(){
    		if(this.r.compareTo(parent.r)!=0)
    			return new Point(this.r+this.r.compareTo(parent.r),this.c,this);
    		if(this.c.compareTo(parent.c)!=0)
    			return new Point(this.r,this.c+this.c.compareTo(parent.c),this);
    		return null;
    	}
    }
	
}
