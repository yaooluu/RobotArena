package behavior;

import physics.Vec2D;
import environment.Boid;

public class Trace {
//current version
	public static void trace(Boid b1,Boid b2)
	{	
		//behavior finish (trace)
		if(b1.pos.minus(b2.pos).getLength() < 70) {
			if(b1.curBehavior.equals("trace")) {
				b1.curBehavior = "";
				return;
			}
		}	
		
		//Steering behavior
		Steering st=null;
		
		Vec2D target = new Vec2D(0,0);
		float distance=(b1.getSize()+b2.getSize())/2;
		//keep distance
		distance*=5;
		
		target.x=(float) (b2.pos.x-distance*Math.sin(Math.toRadians(b2.r)));
		target.y=(float) (b2.pos.y-distance*Math.cos(Math.toRadians(b2.r)));
		
		st=Behavior.seek(b1, target);	
		Behavior.addAcc(b1, st);
		//System.out.println("test:"+ target.x+" "+target.y);
		st=Behavior.arrive(b1, target);
		if(st.a.getLength()>0)Behavior.changeAcc(b1, st);
	}
}
