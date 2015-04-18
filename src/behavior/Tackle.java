package behavior;

import main.Config;
import physics.Vec2D;
import environment.Boid;

public class Tackle {
	public static void tackle(Boid b1, Boid b2)
	{
		
		//finish tackle
		if(b2.pos.minus(b1.pos).getLength() < 10) {
			if(b1.curBehavior.equals("tackle")) {
				b1.curBehavior = "";
				return;
			}
			b1.v=b1.v.multiply(0f);
			b1.a=b1.a.multiply(0f);
			return;
		}	
		Vec2D target=new Vec2D(0,0);
		float time;
		time=b2.pos.minus(b1.pos).getLength()/b1.v.getLength();
		if(time>20f/Config.FRAME_RATE)time=20f/Config.FRAME_RATE;
		if(time<10f/Config.FRAME_RATE)time=10f/Config.FRAME_RATE;
		//predict b2's position and then try to tackle it
		float moveDistance=Config.MAX_SPEED[b2.getType()]*time;
		target.x=(float) (b2.pos.x+moveDistance*Math.sin(Math.toRadians(b2.r)));
		target.y=(float) (b2.pos.y-moveDistance*Math.cos(Math.toRadians(b2.r)));

		Attack.goAttack(b1, target);
		return;
	}
	
}
