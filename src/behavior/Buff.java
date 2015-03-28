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
				//st=Behavior.seek(b1, Redbuffposition);	
			}
			else if(type.equals("blue"))
			{
				//st=Behavior.seek(b1, Bluebuffposition);	
			}
			
			Behavior.changeBoid(b1, st);

	}
}
