package behavior;


import java.util.List;
import main.Config;
import main.Main;
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
	
	//arrival
//detect arrival
	public static Steering arrive(Boid boid, Vec2D targetPos)
	{
		Steering st=new Steering();
		Vec2D distance;
		float arrivalRadius=8,decelerationRadius=60;
		float timeToTarget=0.5f;
		
		distance=targetPos.minus(boid.pos);
		Vec2D goalVelocity=new Vec2D(0,0);
		Vec2D maxVelocity=new Vec2D(distance.x,distance.y);
		maxVelocity.normalize();
		maxVelocity.multiply((Config.MAX_SPEED[boid.getType()]));
		
		if(distance.getLength()<=arrivalRadius)
		{
			return st;
		}
		if(distance.getLength()>decelerationRadius)
		{
			goalVelocity.x=maxVelocity.x;
			goalVelocity.y=maxVelocity.y;
		}
		else
		{
			goalVelocity.x=maxVelocity.x;
			goalVelocity.y=maxVelocity.y;
			goalVelocity.multiply((distance.getLength()/decelerationRadius));		

			st.a=goalVelocity.minus(boid.v);
			st.a.multiply(1/timeToTarget);
		}		
		return st;
	}
	public static void changeAcc(Boid boid,Steering st)
	{
		//
		boid.a=st.a;
		boid.a.truncate(Config.MAX_LINACC[boid.getType()]);
		boid.ar += st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()])
			boid.ar=Config.MAX_ANGACC[boid.getType()];
		//
	}
	
	public static void addAcc(Boid boid,Steering st)
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
	
	//collision avoidance
	public static Steering collisionAvoide(Boid boid)
	{
		List<Boid> boids=Main.getBoids();
		Steering st=new Steering();
		float INFINITY=2147483647;
		float shortestTime=(float) 1.3;
		Boid firstTarget = null;
		float firstMinSeparation=INFINITY;
		float firstDistance = INFINITY;
		Vec2D firstRelativePosition = null;
		Vec2D firstRelativeVelocity = null;
		
		Vec2D relativePosition=new Vec2D(0,0);
		Vec2D relativeVelocity=new Vec2D(0,0);
		float relativeSpeed;
		float timeToCollision=0;
		float distance = INFINITY;
		float minSeparation;
		
		for(Boid t:boids)
		{
			if(t!=boid)
			{
				relativePosition=boid.pos.minus(t.pos);
				relativeVelocity=boid.v.minus(t.v);
				relativeSpeed=relativeVelocity.getLength();
				
				if(relativeSpeed==0)
					return st;
				timeToCollision=-(relativePosition.dotCross(relativeVelocity)/(relativeSpeed*relativeSpeed));
				
				//check collision
				distance=relativePosition.getLength();
				minSeparation=distance-relativeSpeed*timeToCollision;
				if(minSeparation>(boid.getSize()+t.getSize())/2)
				{
					continue;
				}
				if(timeToCollision>0&&timeToCollision<shortestTime)
				{
					shortestTime=timeToCollision;
					firstTarget=t;
					firstMinSeparation=minSeparation;
					firstDistance=distance;
					firstRelativePosition=relativePosition;
					firstRelativeVelocity=relativeVelocity;					
				}
				
			}
		}
		
		if(firstTarget==null)return st;
		if(firstMinSeparation<=0 || firstDistance<(boid.getSize()+firstTarget.getSize())/2)
		{
			//evade
			relativePosition=boid.pos.minus(firstTarget.pos);
		}
		else
		{
			relativePosition.x=firstRelativePosition.x+firstRelativeVelocity.x*shortestTime;
			relativePosition.y=firstRelativePosition.y+firstRelativeVelocity.y*shortestTime;
		}
		relativePosition.normalize();
		st.a=relativePosition.multiply(Config.MAX_LINACC[boid.getType()]);
		return st;
		
	}
	
	
}
