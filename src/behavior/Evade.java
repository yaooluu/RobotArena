package behavior;

import environment.Boid;

public class Evade {
	public static void evade(Boid b1,Boid b2)
	{	
		//behavior finish (evade)
		if(b1.pos.minus(b2.pos).getLength() > 100) {
			if(b1.curBehavior.equals("evade")) {
				b1.curBehavior = "";
				return;
			}
		}
		
		Steering st;
		//Steering behavior
		st=Behavior.seek(b2, b1.pos);	
		Behavior.changeAcc(b1, st);
		st=Behavior.collisionAvoid(b1);
		Behavior.addAcc(b1, st);
	}
}
