package behavior;

import physics.Vec2D;
import environment.Boid;

public class Wander {
	private static float randomBinomial()
	{
		return (float) (Math.random()-Math.random());
	}
	
	public static void wander(Boid boid)
	{
		float wanderOffset=10, wanderRadius=5;
		float wanderRate=(float)0.2;
		float wanderOrientation = 0;
		Vec2D target = new Vec2D(0,0);
		float targetOrientation;
		wanderOrientation+=randomBinomial()*wanderRate;
		targetOrientation=wanderOrientation+boid.r;
		
		
		target.x=(float) (boid.pos.x+wanderOffset*Math.sin(boid.r));
		target.y=(float) (boid.pos.y+wanderOffset*Math.cos(boid.r));
		
		target.x+=wanderRadius*Math.sin(targetOrientation);
		target.y+=wanderRadius*Math.cos(targetOrientation);
		//boundary detect
		
		//Steering behavior
		Steering st;
		st=Behavior.seek(boid, target);
		Behavior.changeBoid(boid, st);
	}
	
}
