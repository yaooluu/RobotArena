package main;

import java.util.*;
import processing.core.PApplet;
import environment.*;
import behavior.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	private static List<Boid> boids = new ArrayList<Boid>();
	public static List<Boid> getBoids() {return boids;}
	
	public void setup() {
		Config.canvas = this;	//set global canvas object
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		//init teams
		int id = 0;
		for(int i=0;i<Config.TEAM_SIZE.length;i++) {
			for(int j=0;j<Config.TEAM_SIZE[i];j++) {
				id++;
				Boid b = new Boid(100+400*i, 20*(j+1), 90+180*i, i, Config.BOID_TYPE.scout, id);	
				boids.add(b);
			}			
		}
	}
	
	public void draw() {
		background(255);

		for(int i=0;i<boids.size();i++) {
			Boid b = boids.get(i);

			if(frameCount % 60 == 1) {
				System.out.print(b.getId()+": ");
				Boid ve = b.getVisibleEnemy();
				Boid ae = b.getAudibleEnemy();
				Boid da = b.getDetectableAlly();
				System.out.print((ve==null?ve:ve.getId()) + ", ");
				System.out.print((ae==null?ae:ae.getId()) + ", ");
				System.out.println(da==null?da:da.getId());
				if(i==boids.size()-1) System.out.println();
			}
			//Wander.wander(b);
			Behavior.update(b);
			b.draw();
		}

	}
}
