package environment;

import main.*;
import physics.*;
import processing.core.PApplet;

public class Boid {
	
	private PApplet canvas = null;
	private int id = -1;
	
	//dynamic movement parameters
	public Vec2D pos = new Vec2D(0,0);
	public float r = 0;
	
	public Vec2D v = new Vec2D(0,0);
	public float vr = 0;
	
	public Vec2D a = new Vec2D(0,0);
	public float ar = 0;
	
	//offensive, defensive
	public int status = 1;
	
	public float wanderOrientation = 0;
	public boolean isRotate = true;
	public boolean isHittingWall = false;
	
	//identify ally or enemy
	private int team = -1;
	private int type = -1;
	private int size = 0;
	private int vision = 0;
	private int auditory = 0;
	private int fuel = 0;
	private int mass = 0;
	private RGB rgb = null;
	
	public int getId() {return id;}
	public int getTeam() {return team;}
	public int getType() {return type;}
	public int getSize() {return size;}
	public int getVision() {return vision;}
	public int getAuditory() {return auditory;}
	public int getFuel() {return fuel;}
	public int getMass() {return mass;}
	
	public Vec2D getOriVec() {
		Vec2D oriVec = new Vec2D(0,0);
		if(r == 0) oriVec = new Vec2D(0,-1);
		else if(r == 90) oriVec = new Vec2D(1,0);
		else if(r == 180) oriVec = new Vec2D(0,1);
		else if(r == 270) oriVec = new Vec2D(-1,0);
		else oriVec = new Vec2D((float)Math.sin(Math.toRadians(r)),
				(float)Math.cos(Math.toRadians(r)) * -1.0f);
		
		//System.out.println("getOriVec: r=" + r);
		//System.out.println("getOriVec: vec=" + oriVec);	
		return oriVec;
	}

	public Boid(float x, float y, float r, int team, Config.BOID_TYPE type, int id) {
		this.canvas = Config.canvas;
		
		this.id = id;
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
		if(canvas == null) return;
		float faceWidth = size / 15.0f;
		
		canvas.noStroke();
		if(rgb != null) {
			canvas.stroke(rgb.r, rgb.g, rgb.b);
			canvas.strokeWeight(0.5f + faceWidth);
		}
				
		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		canvas.rotate(PApplet.radians(r));

		//draw body lower
		canvas.fill(0);
		canvas.ellipse(0, 0, size, size);		
				
		if(rgb != null) {
			canvas.fill(rgb.r, rgb.g, rgb.b);
		}
		
		//draw body upper
		float upper = size / 3;
		canvas.rect(-1*upper/2, -1*upper/2 + faceWidth, upper, upper + faceWidth, 3);
		
		//draw facing
		canvas.rect(-1 * faceWidth/2, -0.9f * size/2, faceWidth, size * 0.5f);	
		
		//draw fuel here...
		
		if(Config.showVision == true) {
			canvas.strokeWeight(1.6f);
			//draw boid's vision
			float x1 = 0, y1 = 0, x2 = 50, y2 = -1*x2*Math.abs(PApplet.tan(vision/2));
			for (int i = 5; i <= 20; i++) {
				  float x = PApplet.lerp(x1, x2, i/10.0f);
				  float y = PApplet.lerp(y1, y2, i/10.0f);
				  canvas.point(x, y);
				  
				  x = PApplet.lerp(x1, -1 * x2, i/10.0f);
				  y = PApplet.lerp(y1, y2, i/10.0f);
				  canvas.point(x, y);
			}
			//draw boid's auditory
			canvas.strokeWeight(1.1f);
			dashedCircle(auditory + size/2, 4, 6);
		}

		canvas.popMatrix();
	}
	
	/**
	 * Draw dotted circle.
	 * function from: http://www.openprocessing.org/sketch/28215
	 */
	private void dashedCircle(float radius, int dashWidth, int dashSpacing) {
		
	    int steps = 200;
	    int dashPeriod = dashWidth + dashSpacing;
	    boolean lastDashed = false;
	    for(int i = 0; i < steps; i++) {
	      boolean curDashed = (i % dashPeriod) < dashWidth;
	      if(curDashed && !lastDashed) {
	        canvas.beginShape();
	      }
	      if(!curDashed && lastDashed) {
	    	  canvas.endShape();
	      }
	      if(curDashed) {
	        float theta = PApplet.map(i, 0, steps, 0, PApplet.TWO_PI);
	        canvas.vertex(PApplet.cos(theta) * radius, PApplet.sin(theta) * radius);
	      }
	      lastDashed = curDashed;
	    }
	    if(lastDashed) {
	    	canvas.endShape();
	    }
	}

	
	public Boid getVisibleEnemy() {
		Boid b = searchBoid(this.vision, Integer.MAX_VALUE, true);
		//if(canvas.frameCount % 60 == 1) System.out.println(this + " sees enemy " + b);
		return b;
	}
	
	public Boid getAudibleEnemy() {
		Boid b = searchBoid(360, this.auditory, true);
		//if(canvas.frameCount % 60 == 1) System.out.println(this + " hears enemy " + b);
		return b;
	}
	
	public Boid getDetectableAlly() {
		Boid b =  searchBoid(this.vision, Integer.MAX_VALUE, false);
		if(b==null) b = searchBoid(360, this.auditory, false);
		//if(canvas.frameCount % 60 == 1) System.out.println(this + " detects ally " + b);
		return b;
	}
	
	private Boid searchBoid(float vision,float auditory, boolean isEnemy) {
		Boid boid = null;
		float distance = Integer.MAX_VALUE;
		
		for(Boid b : Main.getBoids()) {
			if(this.id == b.id || (this.team!=b.team) != isEnemy) continue;
			if(World.detectAccessible(this.pos, b.pos) == false) continue;

			float ang = Vec2D.getAngleBetween(b.pos.minus(this.pos), 
					this.getOriVec());
			float dist = b.pos.minus(this.pos).getLength();
			if(dist < auditory && ( ang < vision/2
				 || (int)vision == 360)){
				if(dist<distance) {
					distance = dist;
					boid = b;
				}
			}
		}
		return boid;
	}

	public int getBuffRange() {
		float range = Integer.MAX_VALUE;
		for(Buff b : World.getBuffs()) {
			if(b.countdown > 0) continue;
			float r = b.minus(this.pos).getLength();
			if(r < range) range = r;
		}
		return (int)range;
	}
	
	public Vec2D getRedBuff() {
		return getBuff(0);
	}
	
	public Vec2D getBlueBuff() {
		return getBuff(1);
	}
	
	private Vec2D getBuff(int type) {
		float range = Integer.MAX_VALUE;
		Vec2D buff = new Vec2D(pos.x+1, pos.y+1);
		
		for(Buff b : World.getBuffs()) {
			if(b.getType() != type || b.countdown > 0) continue;
			
			float r = b.minus(this.pos).getLength();
			if(r < range) {
				range = r;
				buff = b;
			}
		};
		return buff;
	}
	
	public Vec2D findHide() {
		return pos;
	}
	
	public String toString() {
		//return "Boid(id="+this.id+", team="+this.team+", type="+this.type+")";
		return "Boid "+this.id;
	}
	
}
