package behavior;

import physics.Vec2D;
import environment.Boid;

public class Hide {
	//find the closest hide position
	private Vec2D findClosestHide()
	{
		Vec2D hidePos=new Vec2D(0,0);
		
		return hidePos;
	}
	public static void evade(Boid b1,Vec2D hidePos)
	{		
			//Steering behavior
			Steering st=null;
			st=Behavior.seek(b1, hidePos);	
			Behavior.addAcc(b1, st);
	}
}
