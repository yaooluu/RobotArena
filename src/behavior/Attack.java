package behavior;

import physics.Accel;
import main.Config;
import environment.Boid;

public class Attack {
	
	private static Steering st;
	public Attack()
	{
		st=new Steering();
	}
	//current version
	public static Steering goAttack(Boid b1,Boid b2)
	{
		
			//Steering behavior
			st=new Steering();
			//Steering behavior
			st.a=(Accel) b1.pos.minus(b2.pos);
			//clip velocity
			if(st.a.getLength()>Config.MAX_LINACC[b1.getType()])
			{
				st.a.normalize();;
				st.a.multiply(Config.MAX_LINACC[b1.getType()]);
			}
			return st;			
	}

}
