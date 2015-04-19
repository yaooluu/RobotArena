package main;

import java.util.List;
import behavior.Attack;
import behavior.Behavior;
import physics.Vec2D;
import processing.core.PApplet;
import environment.Boid;

public class Player {
	Boid b;
	
	
	private PApplet canvas = null;
	
	/*
	Player(float x, float y, float r, int team, Config.BOID_TYPE type, int id)
	{
		b=new Boid(x, y, r, team, type, id);
		canvas=Config.canvas;
	}*/
	
	Player(Boid b) {
		this.b = b;
		canvas = Config.canvas;
	}
	
	void move()
	{
		//b.r%=360;
		//acceleration
		if(Main.arrowKeys[0]) {				
			b.a.x=(float)Math.sin(Math.toRadians(b.r));
			b.a.y=-(float)Math.cos(Math.toRadians(b.r));
			b.a.drag(Config.MAX_LINACC[b.getType()]+b.bonusAcc);			
			b.vr=0;
			Behavior.update(b);			
		
		} else {
			b.v=b.v.multiply(0f);
			b.a=b.a.multiply(0f);
		}
		
		//counter clockwise rotate
		if(Main.arrowKeys[2]) {	
			//System.out.println("Left.");
			b.vr=-Config.MAX_ANGACC[b.getType()];	
			b.v=b.v.multiply(0f);
			b.a=b.a.multiply(0f);
			Behavior.update(b);
			
		}
		
		//clockwise rotate 
		if(Main.arrowKeys[3]) {	
			//System.out.println("Right.");
			b.vr=Config.MAX_ANGACC[b.getType()];	
			b.v=b.v.multiply(0f);
			b.a=b.a.multiply(0f);
			Behavior.update(b);			
		}
		
		//ultimate
		if(Main.arrowKeys[1]||canvas.key =='a')
		{
			//Config.ult_music.trigger();
			b.ultimate();
			Behavior.update2(b);
		}
	}
	
	
	/*
	void move()
	{
		if(canvas.keyPressed)
		{
			//System.out.println("In key Pressed function.");
			//b.r%=360;

			if(canvas.key == canvas.CODED)
			{
				if(canvas.keyCode == canvas.UP)
						{
							//acceleration
							b.a.x=(float)Math.sin(Math.toRadians(b.r));
							b.a.y=-(float)Math.cos(Math.toRadians(b.r));
							b.a.drag(Config.MAX_LINACC[b.getType()]);
							
							b.vr=0;
							Behavior.update(b);
						}
	
				if(canvas.keyCode == canvas.LEFT)
				{
					//counter clockwise rotate
					b.vr=-Config.MAX_ANGACC[b.getType()];

					b.v=b.v.multiply(0f);
					b.a=b.a.multiply(0f);
					Behavior.update(b);
				}
				else if(canvas.keyCode == canvas.RIGHT)
				{
					//clockwise rotate 
					b.vr=Config.MAX_ANGACC[b.getType()];

					b.v=b.v.multiply(0f);
					b.a=b.a.multiply(0f);
					Behavior.update(b);
					
				}
			}
		}
		
	}
	*/
	void controlTeam(List<Boid> boids)
	{
		if(canvas.keyPressed)
		{
			System.out.println(canvas.key);
			switch(canvas.key)
			{
			case '1':
				
				if(canvas.mousePressed)
				{
					Vec2D target = new Vec2D(canvas.mouseX, canvas.mouseY);				
					Attack.goAttack(boids.get(0), target);
				}
				break;		
			case '2':
				if(canvas.mousePressed)
				{
					Vec2D target = new Vec2D(canvas.mouseX, canvas.mouseY);				
					Attack.goAttack(boids.get(1), target);
				}
				break;	
			case '3':
				if(canvas.mousePressed)
				{
					Vec2D target = new Vec2D(canvas.mouseX, canvas.mouseY);				
					Attack.goAttack(boids.get(2), target);
				}
				break;	
			case '4':
				if(canvas.mousePressed)
				{
					Vec2D target = new Vec2D(canvas.mouseX, canvas.mouseY);				
					Attack.goAttack(boids.get(3), target);
				}
				break;	
			case '5':
				if(canvas.mousePressed)
				{
					Vec2D target = new Vec2D(canvas.mouseX, canvas.mouseY);				
					Attack.goAttack(boids.get(4), target);
				}
				break;	
			}
		}
	}
	
}
