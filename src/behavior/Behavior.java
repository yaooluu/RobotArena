package behavior;

import main.Config;
import environment.Boid;
import physics.Accel;
import physics.Position;

class Steering {
	public Accel a;
	public float ar;
	
	public Steering()
	{
		this.a = new Accel(0,0);
		this.ar=0;
	}

}

public class Behavior {
	private static Steering st;
	
	public static Steering seek(Boid boid, Position targetPos)
	{
		st=new Steering();
		//Steering behavior
		st.a=(Accel) boid.pos.minus(targetPos);
		//clip velocity
		st.a.truncate(Config.MAX_LINACC[boid.getType()]);
		st.ar=0;
		return st;
	}
	
	
}
