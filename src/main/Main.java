package main;

import java.util.*;

import decisionmaking.DecisionTree;
import pathfinding.Graph;
import physics.Collision;
import physics.Vec2D;
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
		//boids.add(new Boid(200, 400, 90, 0, Config.BOID_TYPE.tank, 2));
		boids.add(new Boid(600, 400, 270, 1, Config.BOID_TYPE.tank, 3));

	}
	
	public void draw() {
		background(255);
		
		//draw the indoor environment
		image(environment, 0, 0);
		
		Attack.goAttack(boids.get(0), boids.get(1));
		//Attack.goAttack(boids.get(1), boids.get(0));
		
		for(int i=0;i<boids.size();i++) {
			Boid b = boids.get(i);
			//Wander.wander(b);
		}
		
		//System.out.println(boids.get(0).v.getLength());System.out.println(boids.get(0).v.getLength());

		physicalCollision();
		
		//update boids' state
		for(Boid b : boids) {
			Behavior.update(b);
			b.draw();
		}
	}
	
	private void physicalCollision() {
		

		for(int i=0;i<boids.size();i++) {
			for(int j=0;j<boids.size();j++) {
				if(i == j) continue;
				Collision.perform(boids.get(i), boids.get(j));
			}
		}
		
		//physical collision between boid and walls		
		for(Boid b : boids) {
			for(Wall w : World.getWalls()) {
				float dist = b.pos.minus(new Vec2D(w.x, w.y)).getLength();
				if(dist < b.getSize()/2.0 + 4) {
					if(w.vec.x == 0 && w.vec.y > 0) {
						w.vec.multiply(Math.abs(b.v.y) * 2.8f);
					}
					else if(w.vec.x > 0 && w.vec.y == 0) {
						w.vec.multiply(Math.abs(b.v.x) * 2.8f);
					}
					else {
						w.vec.multiply(b.v.getLength() * 2.8f);
					}
					b.v.plusEqual(w.vec);
					b.a.x = 0;
					b.a.y = 0;
				}
			}
		}
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
