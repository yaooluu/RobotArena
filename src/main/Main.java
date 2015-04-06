package main;

import java.util.*;

import decisionmaking.DecisionTree;
import pathfinding.Graph;
import physics.Collision;
import physics.Vec2D;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import environment.*;
import environment.Buff;
import behavior.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	private static List<Boid> boids = null;
	public static List<Boid> getBoids() {return boids;}
	
	private static Graph graph = null;
	public static Graph getGraph() {return graph;}
	PImage environment;
	
	//debug
	private Vec2D mouseVec = null;
	private boolean pause = false;
	
	public void setup() {
		Config.canvas = this;
		boids = new ArrayList<Boid>();
		
		environment=loadImage("../src/environment/GameEnvironment.png");
		
		
		graph = World.createGraphFromImage(this);
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		//init teams
		/*
		int id = 0;
		for(int i=0;i<Config.TEAM_SIZE.length;i++) {
			for(int j=0;j<Config.TEAM_SIZE[i];j++) {
				id++;
				Boid b = new Boid(100+400*i, 20*(j+1), 90+180*i, i, Config.BOID_TYPE.scout, id);	
				boids.add(b);
			}			
		}*/
		
		boids.add(new Boid(150, 150, 90, 0, Config.BOID_TYPE.scout, 1));
		//boids.add(new Boid(200, 400, 90, 0, Config.BOID_TYPE.tank, 2));
		boids.add(new Boid(600, 400, 270, 1, Config.BOID_TYPE.tank, 3));
	}
	
	public void draw() {	
		//pause game when pressed space bar
		if(pause == false) {
			
			background(255);
			smooth(8);
			
			drawEnvironment();
			
			if(mouseVec != null)
			{
				Behavior.addAcc(boids.get(0), Behavior.seek(boids.get(0), mouseVec));
				//Behavior.changeAcc(boids.get(0), Behavior.arrive(boids.get(0), mouseVec));
			}
			
			//Attack.goAttack(boids.get(0), boids.get(1));
			//Attack.goAttack(boids.get(1), boids.get(0));
			
			//Evade.evade(boids.get(1), boids.get(0));
			//Wander.wander(boids.get(0));
			//Trace.trace(boids.get(0), boids.get(1));
			//GetBuff.goBuff(boids.get(0), "blue");
			
			for(int i=0;i<boids.size();i++) {
				Boid b = boids.get(i);
				///*//
				if(boids.size()>1) 
					Attack.goAttack(boids.get(i), boids.get((i+1)%2));
				else
					Wander.wander(b);
				//*/
				//if(frameCount % 10 == 1)
				//DecisionTree.PerformDecision(b);
			}
	
			Collision.allCollision(boids);
			Behavior.borderAvoid(boids);
			
			for(Boid b : boids) {
				Behavior.update2(b);
				b.draw();
			}
			
			World.detectFallOff(boids);
			
		} else {
			drawText("Game Paused", 30, 30, "Georgia", 20, new RGB(255,0,0));
		}
	}
	
	private void drawEnvironment() {
		//draw the indoor environment
		image(environment, 0, 0);
		
		//draw buffs
		for(Buff buff : World.getBuffs()) {
			if(buff.countdown > 0) {
				if(frameCount % 60 == 0)
					buff.countdown--;
				String buffText = "Red";
				if(buff.getType() == 1) buffText = "Blue";
				buffText += " in " + buff.countdown + " sec(s)";
				drawText(buffText, buff.x - 40, buff.y, "Georgia", 15, buff.getRGB());
			}
			else {
				float x = buff.x, y = buff.y, size = 10;
				RGB c = buff.getRGB();
				fill(c.r, c.g, c.b);
				noStroke();
				
				beginShape();
				vertex(x, y-size);
				vertex(x+size, y);
				vertex(x, y+size);
				vertex(x-size, y);
				endShape(CLOSE);
				
				//judge if buff is taken
				for(Boid b : boids) {
					if(b.pos.minus(buff).getLength() < b.getSize()/2) {
						buff.countdown = Config.BUFF_COUNTDOWN;
						break;
					}
				}
			}
		}
		
			
	}
	
	private void drawText(String text, float x, float y, String font, float size, RGB rgb) {
		PFont f = createFont(font,size,true);
		textFont(f,size);                 
		fill(rgb.r, rgb.g, rgb.b);                        
		text(text, x, y);
	}

	public void mousePressed() {
		//System.out.println("Mouse clicked: ("+mouseX+", "+mouseY+")");
		mouseVec = new Vec2D(mouseX, mouseY);
	}
	
	public void keyPressed() {
		//System.out.println("Key pressed: "+key);
		if(key == ' ') {
			pause = !pause;
		}
		
		else if(key == 'v') {
			Config.showVision = !Config.showVision;
		}
	}
	
	/*
	private void testBoidVision(Boid b) {
		if(frameCount % 60 == 1) {
			System.out.print(b.getId()+": ");
			Boid ve = b.getVisibleEnemy();
			Boid ae = b.getAudibleEnemy();
			Boid da = b.getDetectableAlly();
			System.out.print((ve==null?ve:ve.getId()) + ", ");
			System.out.print((ae==null?ae:ae.getId()) + ", ");
			System.out.println(da==null?da:da.getId());
		}
	}
	*/
}
