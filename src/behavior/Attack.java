package behavior;

import main.Config;
import environment.Boid;

public class Attack {
	
	private Steering st;
	public Attack()
	{
		st=new Steering();
	}
	public static Steering goAttack(Boid b1,Boid b2)
	{
		
			//Steering behavior
			st.a=(Accel) b1.pos.minus(b2.pos);
			//clip velocity
			if(a.getLength()>Config.MAX_LINACC)
			{
				a.normalize();;
				a.multiply(Config.MAX_LINACC);
			}
								
	}

}
