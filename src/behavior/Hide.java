package behavior;

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
		//behavior finish (hide)
		if(b1.pos.minus(b1.findHide()).getLength() < 5) {
			if(b1.curBehavior.equals("hide"))
				b1.curBehavior = "";
		}
		
		//Steering behavior
		Steering st=null;
		st=Behavior.seek(b1, b1.findHide());	
		Behavior.addAcc(b1, st);
			
	}
}
