package behavior;

import java.util.List;
import environment.Boid;


public class Guard {
	//seek the boid which needs protect, predict the future attack position, seek that position
	public static void guard(Boid b1, List<Boid> boids)
	{
		Boid b2=null;
		
		/*
		float minDist=800;
		float dist;	
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
		}*/
		
		b2 = b1.getDetectableAlly();
				
		if(b2!=null)
		{
			//behavior finish (guard)
			if(b1.pos.minus(b2.pos).getLength() < (b1.getSize()+b2.getSize()+100)) {
				if(b1.curBehavior.equals("guard")) {
					//b1.v=b1.v.multiply(0.8f);
					b1.a=b1.a.multiply(0f);
					b1.curBehavior = "";
					return;
				}
			}	
			//guard b2
			Attack.goAttack(b1, b2);			
		}
	}
}
