package behavior;

import main.Config;
import environment.Boid;
import environment.World;

public class GetBuff {
//current version
	public static void goBuff(Boid b, String type)
	{		
			//Steering behavior
			Steering st=null;
			if(type.equals("red"))
			{
				if(b.getRedBuff().minus(b.pos).getLength() > 2)
				{
					st=Behavior.seek(b, b.getRedBuff());	
					Behavior.changeAcc(b, st);
					st=Behavior.arrive(b, b.getRedBuff());
				}
				else {
					//behavior finish (redbuff)
					if(b.curBehavior.equals("redbuff")) {
						
						if(World.getBuffs().get(0).countdown==15)Config.buff_music.trigger();
						//System.out.println("Red got or missed.");
						b.curBehavior = "";
					}
				}
			}
			else if(type.equals("blue"))
			{
				if(b.getBlueBuff().minus(b.pos).getLength() > 2)
				{
					st=Behavior.seek(b, b.getBlueBuff());	
					Behavior.changeAcc(b, st);
					st=Behavior.arrive(b, b.getBlueBuff());
				}
				else {
					//behavior finish (bluebuff)
					if(b.curBehavior.equals("bluebuff")) {
						if(World.getBuffs().get(1).countdown==15)Config.buff_music.trigger();
						//System.out.println("Blue got or missed.");
						b.curBehavior = "";
					}
				}
					
			}
			/*
			if(st == null) return;
			st.a.truncate(Config.MAX_LINACC[b.getType()]);
			if(st.a.getLength()<=0.01f)
			{
				b.a=b.a.multiply(0f);
				b.v=b.v.multiply(0f);
			}
			*/
	}
}
