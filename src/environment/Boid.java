package environment;

import main.Config;
import processing.core.PApplet;

public class Boid {
	
	private PApplet canvas = null;
	
	private int x = 0;
	private int y = 0;
	private int r = 0; //heading, range [0,360), starts from X-right
	
	private int team = -1;
	private int type = -1;
	
	private int size = 0;
	private int vision = 0;
	private int auditory = 0;
	private int fuel = 0;
	
	private RGB rgb = null;
	
	//getters
	public int getX() {return x;}
	public int getY() {return y;}
	public int getR() {return r;}
	
	public int getSize() {return size;}
	public int getVision() {return vision;}
	public int getAuditory() {return auditory;}
	public int getFuel() {return fuel;}	
	
	//setters
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setR(int r) {this.r = r;}
	
	//init boid
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
