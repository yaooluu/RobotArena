package behavior;

import physics.Vec2D;
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
		//behavior finish (attack)
		//implemented in Collision.java
		
		//Steering behavior
		st=Behavior.seek(b1, b2.pos);
		Behavior.changeAcc(b1, st);
		
	}
	
	//test version
	public static void goAttack(Boid b1,Vec2D pos)
	{		
		//behavior finish (attack)
		//implemented in Collision.java
		
		//Steering behavior
		st=Behavior.seek(b1, pos);
		Behavior.changeAcc(b1, st);
		
	}
	
}
