package environment;


import main.Config;
import physics.*;
import processing.core.PApplet;

public class Boid {
	
	private PApplet canvas = null;
	
	//dynamic movement parameters
	public Position pos = new Position(0,0);
	public float r = 0;
	
	public Velocity v = new Velocity(0,0);
	public float vr = 0;
	
	public Accel a = new Accel(0,0);
	public float ar = 0;
	
	
	//identify ally or enemy
	public int team = -1;
	
	private int type = -1;
	private int size = 0;
	private int vision = 0;
	private int auditory = 0;
	private int fuel = 0;
	private int mass = 0;
	private RGB rgb = null;
	
	public int getType() {return type;}
	public int getSize() {return size;}
	public int getVision() {return vision;}
	public int getAuditory() {return auditory;}
	public int getFuel() {return fuel;}
	public int getMass() {return mass;}
	
	public Boid(float x, float y, float r, int team, Config.BOID_TYPE type) {
		this.canvas = Config.canvas;
		
		this.team = team;
		this.type = type.value();
		this.rgb = RGB.getTeamColor(team);
		
		this.size = Config.BOID_SIZE[this.type];
		this.vision = Config.BOID_VISION[this.type]; 
		this.auditory  = Config.BOID_AUDITORY[this.type];
		this.fuel = Config.BOID_FUEL[this.type];
		this.mass = Config.BOID_MASS[this.type];
		
		pos.x = x;
		pos.y = y;
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
		canvas.translate(pos.x, pos.y);
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
