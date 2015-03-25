package main;

import processing.core.PApplet;
import environment.*;

@SuppressWarnings("serial")
public class Main extends PApplet {

	Boid b = null;
	
	public void setup() {
		Config.canvas = this;	//set global canvas object
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		//test boid style
		b = new Boid(100, 100,0, 0, Config.BOID_TYPE.scout);
	}
	
	public void draw() {
		background(255);
		b.draw();
	}
}
