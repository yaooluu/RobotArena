package behavior;

import environment.Boid;

public class Evade {
	public static void evade(Boid b1,Boid b2)
	{		
			Steering st;
			//Steering behavior
			st=Behavior.seek(b2, b1.pos);	
			Behavior.addAcc(b1, st);
			st=Behavior.collisionAvoid(b1);
			Behavior.addAcc(b1, st);
	}
}
