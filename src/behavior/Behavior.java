package behavior;


import java.util.List;
import main.Config;
import main.Main;
import environment.Boid;
import environment.Border;
import environment.World;
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
		Vec2D goalPos;
		goalPos=new Vec2D(targetPos);

		Vec2D newTarget = PathLibrary.getNextTarget(boid.pos, targetPos);
		if(newTarget != null) targetPos = newTarget;
		
		System.out.println("Seeking: "+targetPos); 
		Config.canvas.ellipse(targetPos.x, targetPos.y, 10, 10);
		
		st=new Steering();
		//Steering behavior
		st.a=targetPos.minus(boid.pos);
		System.out.println("Seeking: "+st.a.getLength()); 
		//clip velocity
		st.a.truncate(Config.MAX_LINACC[boid.getType()]);
		st.ar=0;
		if(targetPos.equals(goalPos))
		{
			st=arrive(boid,goalPos);
			if(st.a.getLength()<=0.01f)
			{
				st.a.multiply(0);
				boid.a=boid.a.multiply(0f);
				boid.v=boid.v.multiply(0f);
			}
		}
		return st;
	}
	
	//arrival
//detect arrival
	public static Steering arrive(Boid boid, Vec2D targetPos)
	{
		Steering st=new Steering();
		Vec2D distance;
		float arrivalRadius=4,decelerationRadius=10;
		float timeToTarget=1f;
		
		distance=targetPos.minus(boid.pos);
		Vec2D goalVelocity=new Vec2D(0,0);
		Vec2D maxVelocity=new Vec2D(distance.x,distance.y);
		
		maxVelocity.truncate(Config.MAX_SPEED[boid.getType()]);

			
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
		}		
		st.a=goalVelocity;
		st.a.multiply(1/timeToTarget);
		return st;
	}
	public static void changeAcc(Boid boid,Steering st)
	{
		//
		if(st.a.getLength()>0.01f)
		{
			boid.a=st.a;
			boid.a.drag(Config.MAX_LINACC[boid.getType()]);
		}
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
		boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));
		boid.v.plusEqual(boid.a.multiply((float) (1.0/Config.FRAME_RATE)));
		boid.v.truncate(Config.MAX_SPEED[boid.getType()]);
		boid.vr += boid.ar;
		boid.r += boid.vr;
		
	}
	
	
//update velocity
	public static void update2(Boid boid)
	{
		if(boid.isRotate)
		{
			boid.isRotate=false;
		}
		//current orientation is equal to new orientation
		float rDistance=boid.r-getNewOrientation(boid);
		rDistance=(float) (rDistance%360);
		if(rDistance>180)
		{
			rDistance-=360;
		}
		else if(rDistance<-180)
		{
			rDistance+=360; 
		}	
		if(Math.abs(rDistance)<=8)
		{
			//boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));
			boid.a.truncate(Config.MAX_LINACC[boid.getType()]);
			boid.v.plusEqual(boid.a.multiply((float) (1.0/Config.FRAME_RATE)));
			boid.v.truncate(Config.MAX_SPEED[boid.getType()]);		
			boid.pos.plusEqual(boid.v.multiply((float) (1.0/Config.FRAME_RATE)));
			boid.isRotate=true;
			boid.vr=0;
		}
		else
		{
			smoothRotate(boid);
			boid.v=boid.v.multiply(0f);
			boid.r += boid.vr;
			boid.vr += boid.ar;
		}
		boid.v.truncate(Config.MAX_SPEED[boid.getType()]);	
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
		//stop and rotate
			boid.vr=rotation;
			if(Math.abs(boid.vr)>Config.MAX_ANGACC[boid.getType()])
			{
				if(rotation>0)
					boid.vr=Config.MAX_ANGACC[boid.getType()];
				else
					boid.vr=-Config.MAX_ANGACC[boid.getType()];
			}
	}
	
	//collision avoidance，avoid other boids
	public static Steering collisionAvoid(Boid boid)
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
			if(t!=boid && t.getTeam()==boid.getTeam())
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
	
	//avoid border and traps
	public static void borderAvoid(List<Boid> boids)
	{
		float avoidDist,minDist=1000f;
		Vec2D avoidVec=new Vec2D(0,0);
		for(Boid b : boids) {

			avoidDist=b.getSize()/2;
			avoidVec=new Vec2D(0,0);
			for(Border w : World.getBorders()) {
				float dist = b.pos.minus(new Vec2D(w.x, w.y)).getLength();
				
				if(dist<avoidDist && dist < minDist && Vec2D.getAngleBetween(b.v, w.borderVec)>90) {
					minDist=dist;	
					avoidVec=w.borderVec;
					//System.out.println("mindist:"+dist);
					//System.out.println("Vector:"+avoidVec.toString());
				//	System.out.println("Velocity:"+b.v.toString());
					//System.out.println("degree:"+Vec2D.getAngleBetween(b.v, w.borderVec));
				}
			}
			
			if(avoidVec.getLength()>0)
			{
				if(avoidVec.x == 0) {
					avoidVec=avoidVec.multiply(Math.abs(b.v.y)*2.0f);
					avoidVec.x=b.v.x;
				}
				else if(avoidVec.y == 0) {
					avoidVec=avoidVec.multiply(Math.abs(b.v.x)*2.0f);
					avoidVec.y=b.v.y;
				}
				else {
					avoidVec=b.v.multiply(-1f);				
				}	
				//avoidVec.drag(Config.MAX_LINACC[b.getType()]);
				b.a=avoidVec;
				//b.a.truncate(Config.MAX_LINACC[b.getType()]);
				//System.out.println("a "+b.a.toString());
			///System.out.println("v "+ b.v.toString());
			}
			
		}
		//System.out.println("xa2 "+boids.get(0).a.toString());
		
	}
}
