package main;

import java.util.*;
import pathfinding.Graph;
import processing.core.PApplet;
import processing.core.PImage;
import environment.*;
import behavior.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	private static List<Boid> boids = null;
	public static List<Boid> getBoids() {return boids;}
	
	private static Graph graph = null;
	public static Graph getGraph() {return graph;}
	PImage environment;
	
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
		
		boids.add(new Boid(100, 100, 90, 0, Config.BOID_TYPE.scout, 1));
		boids.add(new Boid(700, 500, 270, 1, Config.BOID_TYPE.scout, 2));
	}
	
	public void draw() {
		background(255);
	  //draw the indoor environment
	  image(environment, 0, 0);
		for(int i=0;i<boids.size();i++) {
			Boid b = boids.get(i);
			
			//Wander.wander(b);

			if(i<boids.size()-1)
				Attack.goAttack(b, boids.get(i+1));

			Behavior.update(b);
			b.draw();
		}
	}
	
	public void mousePressed() {


	}
	
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
}
