package decisionmaking;

import main.Config;
import environment.Boid;

class RootNode {
	public static String traverse(Boid b){
		if (b.getVisibleEnemy() != null)
			return EnemyRange.traverse(b);
		else
			return EnemyAudible.traverse(b);
	}
}

class EnemyRange{
	public static String traverse(Boid b){
		float dis = b.getVisibleEnemy().pos.minus(b.pos).getLength();
		if (dis < 10)
			return NearStronger.traverse(b);
		if (dis < 100)
			return MiddleFuel.traverse(b);
		else
			return FarFuel.traverse(b);
	}
}

class EnemyAudible{
	public static String traverse(Boid b){
		return "";
	}
}

class NearStronger{
	public static String traverse(Boid b){
		if (b.getVisibleEnemy().getType() > b.getType())
			return "evade";
		else
			return "attack";
	}
}

class MiddleFuel{
	public static String traverse(Boid b){
		if (b.getFuel() < Config.MAX_FUEL * 0.15)
			return "redbuff";
		else
			
	}
	
}

class FarFuel{
	public static String traverse(Boid b){
		return "";
	}
}
