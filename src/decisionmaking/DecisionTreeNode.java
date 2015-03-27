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
		return "";
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
