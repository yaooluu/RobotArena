package environment;

import main.Config;
import processing.core.PApplet;

public class Boid {
	
	private PApplet canvas = null;
	
	//dynamic movement parameters
	public int x = 0;
	public int y = 0;
	public int r = 0;
	
	public int vx = 0;
	public int vy = 0;
	public int vr = 0;
	
	public int ax = 0;
	public int ay = 0;
	public int ar = 0;
	
	//identify ally or enemy
	public int team = -1;
	
	private int type = -1;
	private int size = 0;
	private int vision = 0;
	private int auditory = 0;
	private int fuel = 0;
	private RGB rgb = null;
	
	public int getSize() {return size;}
	public int getVision() {return vision;}
	public int getAuditory() {return auditory;}
	public int getFuel() {return fuel;}
	
	public Boid(int x, int y, int r, int team, Config.BOID_TYPE type) {
		this.canvas = Config.canvas;
		
		this.team = team;
		this.type = type.value();
		this.rgb = RGB.getTeamColor(team);
		
		this.size = Config.BOID_SIZE[this.type];
		this.vision = Config.BOID_VISION[this.type]; 
		this.auditory  = Config.BOID_AUDITORY[this.type];
		this.fuel = Config.BOID_FUEL[this.type];		
		
		this.x = x;
		this.y = y;
		this.r = r;
		
		draw();
	}
	
	public void draw() {
		canvas.noStroke();
		if(rgb != null) {
			canvas.stroke(rgb.r, rgb.g, rgb.b);
			canvas.strokeWeight(1.5f);
		}
				
		canvas.pushMatrix();
		canvas.translate(x, y);
		canvas.rotate(PApplet.radians(r));
				
		canvas.fill(0);
		canvas.ellipse(0, 0, size, size);		//draw body
		
		if(rgb != null)
			canvas.fill(rgb.r, rgb.g, rgb.b);	
		canvas.rect(-1, -1*size/2, 2, size*3/5);	//draw facing
		
		//draw fuel here...

		canvas.popMatrix();
	}
}
