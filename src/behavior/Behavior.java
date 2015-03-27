package behavior;

import main.Config;
import environment.Boid;
import pathfinding.PathLibrary;
import physics.Vec2D;

class Steering {
	public Vec2D a;
	public float ar;
	
	public Steering()
	{
		this.a = new Vec2D(0,0);
		this.ar=0;
	}

}

public class Behavior {
	
	public static Steering seek(Boid boid, Vec2D targetPos)
	{

		Steering st;

		Vec2D newTarget = PathLibrary.getNextTarget(boid.pos, targetPos);
		if(newTarget != null) targetPos = newTarget;
		

		st=new Steering();
		//Steering behavior
		st.a=targetPos.minus(boid.pos);
		//clip velocity
		st.a.truncate(Config.MAX_LINACC[boid.getType()]);
		st.ar=0;
		//
		return st;
	}
	public static void changeBoid(Boid boid,Steering st)
	{
		//
		boid.a.plusEqual(st.a);
		boid.a.truncate(Config.MAX_LINACC[boid.getType()]/Config.FRAME_RATE);
		boid.ar += st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()]/Config.FRAME_RATE)
			boid.ar=Config.MAX_ANGACC[boid.getType()/Config.FRAME_RATE];
		//
	}
	//update velocity
	public static void update(Boid boid)
	{
		boid.pos.plusEqual(boid.v);
		boid.r += boid.vr;
		boid.v.plusEqual(boid.a);
		boid.vr += boid.ar;
	}
	
}
