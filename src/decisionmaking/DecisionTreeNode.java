package decisionmaking;

import main.Config;
import environment.Boid;

class RootNode {
	public static String traverse(Boid b) {
		if (b.getVisibleEnemy() != null)
			return EnemyRange.traverse(b);
		else
			return EnemyAudible.traverse(b);
	}
}

class EnemyRange {
	public static String traverse(Boid b) {
		float dis = b.getVisibleEnemy().pos.minus(b.pos).getLength();
		if (dis < 10)
			return NearStronger.traverse(b);
		if (dis < 100)
			return MiddleFuel.traverse(b);
		else
			return FarFuel.traverse(b);
	}
}

class EnemyAudible {
	public static String traverse(Boid b) {
		if (b.getAudibleEnemy() != null)
			return MovingToMe.traverse(b);
		else
			return LowFuel.traverse(b);
	}
}

class NearStronger {
	public static String traverse(Boid b) {
		if (b.getVisibleEnemy().getType() > b.getType())
			return "evade";
		else
			return "attack";
	}
}

class MiddleFuel {
	public static String traverse(Boid b) {
		if (b.getFuel() < Config.MAX_FUEL * 0.15)
			return "redbuff";
		else
			return Offensive1.traverse(b);
	}

}

class FarFuel {
	public static String traverse(Boid b) {
		if (b.getType() < Config.MAX_FUEL * 0.15)
			return "redbuff";
		else
			return Offensive2.traverse(b);
	}
}

class Offensive1 {
	public static String traverse(Boid b) {
		switch (b.status) {
		case 1:
			return MiddleStrong.traverse(b);
		case 0:
			return Rand1.traverse(b);
		default:
			return "e";
		}
	}
}

class MiddleStrong {
	public static String traverse(Boid b) {
		if (b.getVisibleEnemy().getType() > b.getType()) { // strong than me
			if (b.getType() == Config.BOID_TYPE.commander.value())
				return "attack";
			else
				return Math.random() < 0.5 ? "trace" : "evade";
		} else {
			return Math.random() < 0.5 ? "trace" : "attack";
		}
	}
}

class Rand1 {
	public static String traverse(Boid b) {
		return Math.random() < 0.5 ? "wander" : "evade";
	}
}

class Offensive2 {
	public static String traverse(Boid b) {
		if (b.status == 1)
			return FarStrong.traverse(b);
		else
			return Rand1.traverse(b);
	}
}

class FarStrong {
	public static String traverse(Boid b) {
		if (b.getVisibleEnemy().getType() > b.getType()) {// stronger than me
			if (b.getType() == Config.BOID_TYPE.commander.value()) // VIP
				return Math.random() < 0.5 ? "attack" : "lure";
			else
				return Rand2.traverse(b);
		} else
			return Math.random() < 0.5 ? "lure" : "attack";
	}
}

class Rand2 {
	public static String traverse(Boid b) {
		double t = Math.random();
		if (t < 0.8)
			return "wander";
		if (t < 0.9)
			return "lure";
		return "evade";
	}
}

class MovingToMe {
	public static String traverse(Boid b) {
		// determine whether the audiable enemy is moving to me
		boolean movingtome = b.pos.minus(b.getAudibleEnemy().pos).dotCross(b.v) > 0;
		if (movingtome)
			return Offensive3.traverse(b);
		else
			return LowFuel2.traverse(b);
	}
}

class LowFuel {
	public static String traverse(Boid b) {
		if (b.getFuel() < Config.MAX_FUEL * 0.15) // low fuel
			return "redbuff";
		else
			return Math.random() < 0.01 ? "ultimate" : AllyDetectable
					.traverse(b);
	}
}

class Offensive3 {
	public static String traverse(Boid b) {
		if (b.status == 1) {
			if (b.getAudibleEnemy().getType() > b.getType())// stronger than me
				return "evade";
			else
				return b.getFuel() < Config.MAX_FUEL * 0.15 ? "evade"
						: "attack";
		} else
			return "evade";
	}
}

class LowFuel2 {
	public static String traverse(Boid b) {
		if (b.getFuel() < Config.MAX_FUEL * 0.15)
			return "evade";
		else {
			if (b.getAudibleEnemy().getType() > b.getType())
				return StrongLowFuel.traverse(b);
			else
				return Math.random() < 0.5 ? "wander" : "evade";
		}
	}
}

class StrongLowFuel {
	public static String traverse(Boid b) {
		if (b.getAudibleEnemy().getType() > b.getType()) { // Stronger than me
			if (b.getAudibleEnemy().getType() == Config.BOID_TYPE.commander
					.value())// enemy is VIP
				return "attack";
			else
				return Math.random() < 0.5 ? "trace" : "evade";
		} else
			return Math.random() < 0.5 ? "wander" : "evade";
	}
}

class AllyDetectable {
	public static String traverse(Boid b) {
		//TODO
	}
}