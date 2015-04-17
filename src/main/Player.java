package main;

import behavior.Behavior;
import physics.Vec2D;
import processing.core.PApplet;
import environment.Boid;

public class Player {
	Boid b;
	
	
	private PApplet canvas = null;
	
	Player(float x, float y, float r, int team, Config.BOID_TYPE type, int id)
	{
		b=new Boid(x, y, r, team, type, id);
		canvas=Config.canvas;
	}
	void move()
	{
		if(canvas.keyPressed)
		{
			b.r%=360;
			if(canvas.key == 'A' || canvas.key == 'a')
			{
				//acceleration
				b.a.x=(float)Math.sin(Math.toRadians(b.r));
				b.a.y=-(float)Math.cos(Math.toRadians(b.r));
				b.a.drag(Config.MAX_LINACC[b.getType()]);
				
				b.vr=0;
				Behavior.update(b);
			}
			else if(canvas.key == canvas.CODED)
			{
	
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
	
}
