package behavior;

import environment.Boid;

public class Evade {
	public static void evade(Boid b1,Boid b2)
	{		
			Steering st;
			//Steering behavior
			st=Behavior.seek(b2, b1.pos);	
			Behavior.changeBoid(b1, st);
			st=Behavior.collisionAvoide(b1);
			Behavior.changeBoid(b1, st);
	}
}
