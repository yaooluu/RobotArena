package behavior;

import main.Config;
import environment.Boid;

public class GetBuff {
//current version
	public static void goBuff(Boid b1, String type)
	{		
			//Steering behavior
			Steering st=null;
			if(type.equals("red"))
			{
				if(b1.getRedBuff()!=null)
				{
					st=Behavior.seek(b1, b1.getRedBuff());	
					Behavior.changeAcc(b1, st);
					st=Behavior.arrive(b1, b1.getRedBuff());
				}
					
			}
			else if(type.equals("blue"))
			{
				if(b1.getBlueBuff()!=null)
				{
					st=Behavior.seek(b1, b1.getBlueBuff());	
					Behavior.changeAcc(b1, st);
					st=Behavior.arrive(b1, b1.getBlueBuff());
				}
					
			}
			b1.a=st.a;
			b1.a.truncate(Config.MAX_LINACC[b1.getType()]);
			if(st.a.getLength()<=0.01f)
			{
				b1.a=b1.a.multiply(0f);
				b1.v=b1.v.multiply(0f);
			}
					
	}
}
