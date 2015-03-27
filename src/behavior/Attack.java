package behavior;

import main.Config;
import physics.Accle;
import environment.Boid;

public class Attack {
	
	Accle a = new Accle(0,0);
	
	public Attack(Boid b1,Boid b2)
	{

			//Steering behavior
			a=(Accle) b1.pos.minus(b2.pos);
			//clip velocity
			if(a.getLength()>Config.MAX_LINACC)
			{
				a.normalize();;
				a.multiply(Config.MAX_LINACC);
			}
								
	}

}
