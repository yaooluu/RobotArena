package behavior;

import main.Config;
import physics.Vec2D;
import environment.Boid;

public class Wander {
	
	private static float randomBinomial()
	{
		//Random random = new Random(System.currentTimeMillis());
		//return random.nextFloat();
		//return (float)Math.random();
		return (float)(Math.random()-Math.random());
	}
	
	private static int mod = 1;
	
	public static void wander(Boid boid)
	{
		//float ran = randomBinomial();
		if(Config.canvas.frameCount % mod != 0) return;
		
		mod = (int)Math.random()*120 + 60;
		
		float wanderOffset=320;
		float wanderRadius=20;
		float wanderRate=3f;

		wanderRadius=(float)Math.random()*20+35;
		wanderOffset=(float)Math.random()*100+30;
		Vec2D target = new Vec2D(0,0);
		float targetOrientation;
		
		boid.wanderOrientation+=randomBinomial()*wanderRate;

		boid.wanderOrientation%=360;

		targetOrientation=boid.wanderOrientation+boid.r;
		
		
		target.x=(float) (boid.pos.x+wanderOffset*Math.sin(Math.toRadians(boid.r)));
		//orientation's coordinate is not the same as the canvas, so here should use minus instead of plus
		target.y=(float) (boid.pos.y-wanderOffset*Math.cos(Math.toRadians(boid.r)));
		
		target.x+=wanderRadius*Math.sin(Math.toRadians(targetOrientation));
		target.y-=wanderRadius*Math.cos(Math.toRadians(targetOrientation));
		//draw the target
		//Boid b1 = new Boid(target.x, target.y, targetOrientation, 0, Config.BOID_TYPE.scout,3);
		Config.canvas.ellipse(target.x, target.y, 30, 30);
		//Steering behavior
		Steering st;
		st=Behavior.seek(boid, target);
		Behavior.changeAcc(boid, st);
		
		//collision avoidance
		st=Behavior.collisionAvoid(boid);
		Behavior.changeAcc(boid, st);
		
		//behavior finished (wander) (test)
		boid.wanderChangeCount++;
		if(boid.curBehavior.equals("wander") && boid.wanderChangeCount > 1) {
			boid.wanderChangeCount = 0;
			boid.curBehavior = "";
		}
	}
	
}
