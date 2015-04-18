package behavior;

import physics.Vec2D;
import main.Config;
import environment.Boid;

public class Ultimate {

	public static void ultimate(Boid b)
	{	
		//behavior finish (ultimate)
		if(b.isHit) {
			b.isUlt=false;
			
			if(b.curBehavior.equals("ultimate")) {
				b.curBehavior = "";			
				return;	
			}
		}
		
		b.isUlt=true;
		Vec2D vibrate;
		if(b.accRotate<1080)
		{
			vibrate=new Vec2D((float) (Math.sin(Math.toRadians(b.r))),
					-(float)(Math.cos(Math.toRadians(b.r))));
			
			b.vr=3*Config.MAX_ANGACC[b.getType()];
			b.v=b.v.multiply(0f);
			b.accRotate+=b.vr;	
			
			b.pos.plusEqual(vibrate);	
			
			//decrease fuel
			float threshold = Config.LOW_FUEL_RATE * Config.BOID_FUEL[b.getType()];
			if(b.fuel > threshold) {
				b.fuel = Config.BOID_FUEL[b.getType()]
						- (b.accRotate/1080) * (1-Config.LOW_FUEL_RATE)
						* Config.BOID_FUEL[b.getType()];
			}
		}
		else if(!b.isHit)
		{
			b.vr=0;
			b.v.x=(float) Math.sin(Math.toRadians(b.r));
			b.v.y=-(float) Math.cos(Math.toRadians(b.r));
			b.v.drag(4f*Config.MAX_SPEED[b.getType()]);
			//b.pos.plusEqual(b.v.multiply((float) (1.0/Config.FRAME_RATE)));
			//finish ultimate
			b.curBehavior = "";		
		}
		
	}
	
}
