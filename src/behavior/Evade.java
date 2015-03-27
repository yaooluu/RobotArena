package behavior;

import environment.Boid;

public class Evade {
	public static void evade(Boid b1,Boid b2)
	{		
			//Steering behavior
			Behavior.seek(b1, b2.pos);	
	}
}
