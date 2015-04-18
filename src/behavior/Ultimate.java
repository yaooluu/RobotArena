package behavior;

import main.Config;
import environment.Boid;

public class Ultimate {

	public static void ultimate(Boid b)
	{
		//behavior finish (ultimate)
		if(b.isHittingWall) {
			b.isUlt=false;
			if(b.curBehavior.equals("ultimate")) {
				b.curBehavior = "";			
				return;
			}
		}
		b.isUlt=true;
		if(b.accRotate<1080)
		{
			b.vr=3*Config.MAX_ANGACC[b.getType()];
			b.v=b.v.multiply(0f);
			b.accRotate+=b.vr;
			
					
		}
		else
		{
			b.vr=0;
			b.v.x=(float) Math.sin(Math.toRadians(b.r));
			b.v.y=-(float) Math.cos(Math.toRadians(b.r));
			b.v.drag(2.5f*Config.MAX_SPEED[b.getType()]);
			//b.pos.plusEqual(b.v.multiply((float) (1.0/Config.FRAME_RATE)));
			//finish ultimate
			b.curBehavior = "";		

		}
		
	}
	
}
