package behavior;

import environment.Boid;

public class Attack {
	
	private static Steering st;
	public Attack()
	{
		st=new Steering();
	}
	//current version
	public static void goAttack(Boid b1,Boid b2)
	{		
			//Steering behavior
			st=Behavior.seek(b1, b2.pos);	
			Behavior.addAcc(b1, st);
	}
	
}
