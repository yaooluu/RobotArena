package behavior;

import main.Config;
import physics.Vec2D;
import environment.Boid;

public class Evade {
	public static void evade(Boid b1,Boid b2)
	{	
		//behavior finish (evade)
		if(b1.pos.minus(b2.pos).getLength() > 150) {
			if(b1.curBehavior.equals("evade")) {
				b1.curBehavior = "";
				return;
			}
		}
		
		Steering st;
		//Steering behavior
		
		Vec2D target=new Vec2D(0,0);
		target=b1.pos.minus(b2.pos);
		
		float temp=target.x;
		if(b1.pos.x>Config.SCREEN_WIDTH/2)
		{
			target.x=-target.y;
			target.y=temp;
		}
		else
		{
			target.x=target.y;
			target.y=-temp;
		}
		
		target.normalize();
		//target.drag(50f);
		
		//how to guarantee the target is accessible?
		float angle=Vec2D.vecToR(target);
		target.x=(float) (b1.pos.x+200*Math.sin(Math.toRadians(angle)));
		target.y=(float) (b1.pos.y-200*Math.cos(Math.toRadians(angle)));
		
		
		
		st=Behavior.seek(b1, target);	
		
		Behavior.changeAcc(b1, st);

	}
}
