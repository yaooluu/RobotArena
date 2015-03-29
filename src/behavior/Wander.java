package behavior;

import physics.Vec2D;
import environment.Boid;

public class Wander {
	
	private static float randomBinomial()
	{
		//Random random = new Random(System.currentTimeMillis());
		return (float)(Math.random()-Math.random());
	}
	
	public static void wander(Boid boid)
	{
		float wanderOffset=40;
	  float wanderRadius=120;
	  float wanderRate=10f;
	  
		Vec2D target = new Vec2D(0,0);
		float targetOrientation;
		boid.wanderOrientation+=randomBinomial()*wanderRate;
		boid.wanderOrientation%=360;

		targetOrientation=boid.wanderOrientation+boid.r;
		
		
		target.x=(float) (boid.pos.x+wanderOffset*Math.sin(Math.toRadians(boid.r)));
		target.y=(float) (boid.pos.y+wanderOffset*Math.cos(Math.toRadians(boid.r)));
		
		
		
		target.x+=wanderRadius*Math.sin(Math.toRadians(targetOrientation));
		target.y+=wanderRadius*Math.cos(Math.toRadians(targetOrientation));
		//draw the target
		//Boid b1 = new Boid(target.x, target.y, targetOrientation, 0, Config.BOID_TYPE.scout);
		
		//Steering behavior
		Steering st;
		st=Behavior.seek(boid, target);
		Behavior.addAcc(boid, st);
		
		//collision avoidance
		st=Behavior.collisionAvoide(boid);
		Behavior.addAcc(boid, st);
	}
	
}
