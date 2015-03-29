package main;

import java.util.*;
import processing.core.PApplet;
import environment.*;
import behavior.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	List<Boid> boids = new ArrayList<Boid>();
	
	public void setup() {
		Config.canvas = this;	//set global canvas object
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		//init teams
		for(int i=0;i<Config.TEAM_SIZE.length;i++) {
			for(int j=0;j<Config.TEAM_SIZE[i];j++) {
				Boid b = new Boid(100+400*i, 100*(j+1), 90+180*i, i, Config.BOID_TYPE.tank);	
				boids.add(b);
			}			
		}

	}
	
	public void draw() {
		background(255);

		for(int i=0;i<boids.size();i++) {
			Boid b = boids.get(i);
			Wander.wander(b);
			Behavior.update(b);
			b.draw();
		}

	}
}
