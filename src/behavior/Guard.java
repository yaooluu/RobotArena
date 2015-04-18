package behavior;

import environment.Boid;


public class Guard {
	//seek the boid which needs protect, predict the future attack position, seek that position
	public static void guard(Boid b1,Boid b2)
	{	//b1 trace b2
		
		//behavior finish (guard)
		if(b1.pos.minus(b2.pos).getLength() < 10) {
			if(b1.curBehavior.equals("guard")) {
				b1.curBehavior = "";
				return;
			}
			b1.v=b1.v.multiply(0f);
			b1.a=b1.a.multiply(0f);
			return;
		}	
		

	}
}
