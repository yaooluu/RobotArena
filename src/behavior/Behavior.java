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
		
		/*
		try {
			System.out.println("frame:"+Config.canvas.frameCount);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//*/
		
		//new way to change direction, Yao Lu
		
		/*
		float ang = Vec2D.getAngleBetween(boid.getOriVec(), targetPos.minus(boid.pos));
		if(ang > 30) {
			boid.r = Vec2D.vecToR(targetPos.minus(boid.pos));
			boid.v = new Vec2D(0,0);
			boid.a = new Vec2D(0,0);
		}
		*/
		//System.out.println("Boid.r:"+boid.r);
		//System.out.println("ang:"+ang);
		//System.out.println("Boid.r/vecToR:"+boid.r+"\n");
		
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
		boid.a.drag(Config.MAX_LINACC[boid.getType()]);
		boid.ar += st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()])
			boid.ar=Config.MAX_ANGACC[boid.getType()];
		//
	}
	
	public static void addAcc(Boid boid,Steering st)
	{
		//
		boid.a.plusEqual(st.a);
		boid.a.drag(Config.MAX_LINACC[boid.getType()]);
		boid.ar += st.ar;
		if(boid.ar>Config.MAX_ANGACC[boid.getType()])
			boid.ar=Config.MAX_ANGACC[boid.getType()];
		//
	}
	//update velocity
	public static void update(Boid boid)
	{
		
		
		boid.v.plusEqual(boid.a.multiply((float) (1.0/Config.FRAME_RATE)));
		boid.v.truncate(Config.MAX_SPEED[boid.getType()]);
		boid.vr += boid.ar;
		boid.r += boid.vr;
		boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));

	}
	
	
//update velocity
	public static void update2(Boid boid)
	{
		if(boid.isRotate)
		{
			boid.OldOrientation=boid.r;
			boid.isRotate=false;
		}
		boid.NewOrientation=getNewOrientation(boid);
		//current orientation is equal to new orientation
		float rDistance=boid.r-boid.NewOrientation;
		rDistance=(float) (rDistance%360);
		if(rDistance>180)
		{
			rDistance-=360;
		}
		else if(rDistance<-180)
		{
			rDistance+=360; 
		}	
		if(Math.abs(rDistance)<=5)
		{
			System.out.println(boid.r+" "+rDistance);
			boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));		
			boid.v.plusEqual(boid.a.multiply((float) (1.0/Config.FRAME_RATE)));
			boid.v.truncate(Config.MAX_SPEED[boid.getType()]);		
			boid.isRotate=true;
			boid.vr=0;
			boid.r=boid.NewOrientation;
		}
		else
		{
			smoothRotate(boid);
			boid.v=new Vec2D(0,0);
			boid.r += boid.vr;
			boid.vr += boid.ar;
		}
		
	//	System.out.println("new r:"+boid.NewOrientation);
	}
	
	
	private static float getNewOrientation(Boid boid)
	{
		if(boid.a.getLength()>0)
			return (float) Math.toDegrees(Math.atan2(boid.a.x, -1*boid.a.y));
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

		//	rotation=newOrientation-boid.r;
			rotation=newOrientation-boid.OldOrientation;
			rotation=(float) (rotation%360);
			if(rotation>180)
			{
				rotation-=360;
			}
			else if(rotation<-180)
			{
				rotation+=360; 
			}
		//stop and rotate
			boid.vr=rotation/50;	
			/*
			//boid.vr=rotation;
			if(Math.abs(rotation)<5)
			{
				boid.vr=0;
				boid.r=newOrientation;
			} 
			boid.r=newOrientation;
			*/
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