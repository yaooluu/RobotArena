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
		boid.a.truncate(Config.MAX_LINACC[boid.getType()]);
		boid.ar += st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()])
			boid.ar=Config.MAX_ANGACC[boid.getType()];
		//
	}
	//update velocity
	public static void update(Boid boid)
	{
		boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));
		boid.r += boid.vr;
		boid.v.plusEqual(boid.a.multiply((float) (1.0/Config.FRAME_RATE)));
		boid.v.truncate(Config.MAX_SPEED[boid.getType()]);
		boid.vr += boid.ar;
		smoothRotate(boid);
	}
	
	private static float getNewOrientation(Boid boid)
	{
		if(boid.v.getLength()>0)
			return (float) Math.toDegrees(Math.atan2(boid.v.x, -boid.v.y));
		else
			return boid.r;					
	}
	
	//change rotation smoothly
	private static void smoothRotate(Boid boid)
	{
			float newOrientation=0;
			float rotation;
			//get new orientation
			newOrientation=getNewOrientation(boid);

			rotation=newOrientation-boid.r;

			rotation=(float) (rotation%360);
			if(rotation>180)
			{
				rotation-=360;
			}
			else if(rotation<-180)
			{
				rotation+=360; 
			}
			
			boid.vr=rotation/Config.FRAME_RATE;
			if(Math.abs(rotation)<5)
			{
				boid.vr=0;
				boid.r=newOrientation;
			}
	}
	
	
	
}
