package behavior;

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
		target.x=target.y;
		target.y=-temp;
		target.normalize();
		target.drag(100);
		target.plusEqual(b1.pos);
		st=Behavior.seek(b1, target);	
		Behavior.changeAcc(b1, st);

	}
}
