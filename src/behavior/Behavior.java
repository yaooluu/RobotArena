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
	public static Steering st;
	
	public static Steering seek(Boid boid, Position targetPos)
	{
		Position newTarget = PathLibrary.getNextTarget(boid.pos, targetPos);
		if(newTarget != null) targetPos = newTarget;
		
		st=new Steering();
		//Steering behavior
		st.a=(Accel) boid.pos.minus(targetPos);
		//clip velocity
		if(st.a.getLength()>Config.MAX_LINACC[boid.getType()])
		{
			st.a.normalize();;
			st.a.multiply(Config.MAX_LINACC[boid.getType()]);
		}
		st.ar=0;
		return st;
	}
	
	
}
