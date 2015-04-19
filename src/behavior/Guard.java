package behavior;

import java.util.List;
import environment.Boid;


public class Guard {
	//seek the boid which needs protect, predict the future attack position, seek that position
	public static void guard(Boid b1, List<Boid> boids)
	{	
		float minDist=200;
		float dist;
		Boid b2=null;
		for(Boid b:boids)
		{
			if(b!=b1)
			{
				dist=b1.pos.minus(b.pos).getLength();
				if(dist<minDist)
				{
					minDist=dist;
					b2=b;
				}
			}
		}
		
		
		if(b2!=null)
		{
		//behavior finish (guard)
			if(b1.pos.minus(b2.pos).getLength() < (b1.getSize()+b2.getSize())) {
				if(b1.curBehavior.equals("guard")) {
					b1.curBehavior = "";
					return;
				}
				b1.v=b1.v.multiply(0f);
				b1.a=b1.a.multiply(0f);
				return;
			}	
			//guard b2
			Attack.goAttack(b1, b2);
			
			
		}
		
		

	}
}
