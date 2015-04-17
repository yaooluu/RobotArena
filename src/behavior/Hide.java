package behavior;

import physics.Vec2D;
import environment.Boid;

public class Hide {
	/*
	//find the closest hide position
	@SuppressWarnings("unused")
	private Vec2D findClosestHide()
	{
		Vec2D hidePos=new Vec2D(0,0);
		
		return hidePos;
	}*/
	public static void hide(Boid b1)
	{		
		Vec2D shelterPos = b1.findHide();
		//behavior finish (hide)
		if(b1.pos.minus(shelterPos).getLength() < 5) {
			if(b1.curBehavior.equals("hide"))
				b1.curBehavior = "";
		}
		
		//Steering behavior
		Steering st=null;
		st=Behavior.seek(b1, shelterPos);	
		Behavior.addAcc(b1, st);
			
	}
}
