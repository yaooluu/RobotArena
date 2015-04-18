package behavior;

import environment.Boid;

public class Lure {
	//try to seek the position where have a lot of enemies
	
	public static void lure(Boid b1)
	{	//b1 trace b2
		
		//behavior finish (lure)
		if(true) {
			if(b1.curBehavior.equals("lure")) {
				b1.curBehavior = "";
				return;
			}
			b1.v=b1.v.multiply(0f);
			b1.a=b1.a.multiply(0f);
			return;
		}	
		

	}
}
