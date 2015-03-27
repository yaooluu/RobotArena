package main;

import processing.core.PApplet;
import environment.*;
import behavior.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	Boid b1 = null;
	Boid b2 = null;
	
	public void setup() {
		Config.canvas = this;	//set global canvas object
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		//test boid style
		b1 = new Boid(100, 100, 90, 0, Config.BOID_TYPE.scout);
		b2 = new Boid(400, 100, 270, 0, Config.BOID_TYPE.scout);
	}
	
	public void draw() {
		background(255);
		
		b1.pos.plusEqual(b1.v);
		b1.v.plusEqual(b1.a);

		Attack.goAttack(b1, b2);
		Attack.goAttack(b1, b2);
		
		
		Behavior.update(b1);
		b1.pos.plusEqual(b1.v);
		b1.v.plusEqual(b1.a);
		b1.a.plusEqual(a1);
			
		b1.draw();
		b2.draw();
	}
}
