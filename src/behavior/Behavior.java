package behavior;

import main.Config;
import environment.Boid;
import pathfinding.PathLibrary;
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
	
	public static Steering seek(Boid boid, Position targetPos)
	{

		Steering st;

		Position newTarget = PathLibrary.getNextTarget(boid.pos, targetPos);
		if(newTarget != null) targetPos = newTarget;
		

		st=new Steering();
		//Steering behavior
		st.a=(Accel) boid.pos.minus(targetPos);
		//clip velocity
		st.a.truncate(Config.MAX_LINACC[boid.getType()]);
		st.ar=0;
		//
		boid.a.plusEqual(st.a);
		boid.a.truncate(Config.MAX_LINACC[boid.getType()]);
		boid.ar+=st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()])
			boid.ar=Config.MAX_ANGACC[boid.getType()];
		//
		return st;
	}
	//update velocity
	public static void update(Boid boid)
	{
		boid.pos.plusEqual(boid.v);
		boid.r+=boid.vr;
		boid.v.plusEqual(boid.a);
		boid.vr+=boid.ar;
	}
	
}
