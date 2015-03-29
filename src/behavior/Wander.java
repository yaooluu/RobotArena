package behavior;

import java.util.List;
import java.util.Random;
import main.Config;
import physics.Vec2D;
import environment.Boid;

public class Wander {
	
	
	static float wanderOrientation = 0;
	private static float randomBinomial()
	{
		Random random = new Random(System.currentTimeMillis());
		return random.nextFloat();
	}
	
	public static void wander(Boid boid, List<Boid> boids)
	{
		float wanderOffset=40;
	  float wanderRadius=120;
	  float wanderRate=(float)0.3;
	  
		Vec2D target = new Vec2D(0,0);
		float targetOrientation;
		wanderOrientation+=randomBinomial()*wanderRate;
		wanderOrientation%=360;

		targetOrientation=wanderOrientation+boid.r;
		
		
		target.x=(float) (boid.pos.x+wanderOffset*Math.sin(Math.toRadians(boid.r)));
		target.y=(float) (boid.pos.y+wanderOffset*Math.cos(Math.toRadians(boid.r)));
		
		
		
		target.x+=wanderRadius*Math.sin(Math.toRadians(targetOrientation));
		target.y+=wanderRadius*Math.cos(Math.toRadians(targetOrientation));
		//draw the target
		//Boid b1 = new Boid(target.x, target.y, targetOrientation, 0, Config.BOID_TYPE.scout);
		
		//Steering behavior
		Steering st;
		st=Behavior.seek(boid, target);
		Behavior.changeBoid(boid, st);
		
		//collision avoidance
		st=Behavior.collisionAvoide(boid, boids);
		Behavior.changeBoid(boid, st);
	}
	
}
