package behavior;

import environment.Boid;

public class Buff {
//current version
	public static void goBuff(Boid b1, String type)
	{		
			//Steering behavior
			Steering st=null;
			if(type.equals("red"))
			{
				if(b1.getRedBuff()!=null)
					st=Behavior.seek(b1, b1.getRedBuff());	
			}
			else if(type.equals("blue"))
			{
				if(b1.getBlueBuff()!=null)
					st=Behavior.seek(b1, b1.getBlueBuff());	
			}
			
			if(st!=null)
					Behavior.changeBoid(b1, st);

	}
}
