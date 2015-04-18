package strategy;

import main.Config;
import main.Main;
import environment.Boid;
import environment.Border;
import environment.World;

public class SafetyEval {
	/** Set up all safety evaluation parameters here! */
	// TODO adjust parameters
	static float allyFactor = 1.3f;
	static float enemyDanger = 1.2f;
	static float bushFactor = 1.3f;
	static float threatenAreaFactor = 2f;

	public static float getSafetyIndex(Boid b) {
		return allySafety(b) - enemyDanger(b) + bushSafety(b)
				- threatenAreaDanger(b);
	}

	private static float allySafety(Boid b) {
		float result = 0;
		for (Boid t : Main.getBoids()) {
			if (t.getTeam() != b.getTeam())
				continue;// not ally
			if (!World.detectAccessible(b.pos, t.pos))
				continue;// block by something
			float arriveTime = b.pos.minus(t.pos).getLength()
					/ Config.MAX_SPEED[t.getType()];
			result += t.getMass() / arriveTime * 0.01; // 0.01 is adjusting
		}
		return result * allyFactor;
	}

	private static float enemyDanger(Boid b) {
		float result = 0;
		for (Boid t : Main.getBoids()) {
			if (t.getTeam() == b.getTeam())
				continue;// not enemy
			if (!World.detectAccessible(b.pos, t.pos))
				continue;// block by something
			float arriveTime = b.pos.minus(t.pos).getLength()
					/ Config.MAX_SPEED[t.getType()];
			result += t.getMass() / arriveTime * 0.01;// 0.01 is adjusting
		}
		return result * enemyDanger;
	}

	private static float bushSafety(Boid b) {
		return Config.MAX_SPEED[b.getType()]
				/ b.findHide().minus(b.pos).getLength() * 5 * bushFactor;
	}

	// considering the linear distance ONLY
	private static float threatenAreaDanger(Boid b) {
		float dis = Float.MAX_VALUE;
		for (Border bor : World.getBorders()) {
			float d = (float) Math.sqrt((bor.x - b.pos.x) * (bor.x - b.pos.x)
					+ (bor.y - b.pos.y) * (bor.y - b.pos.y));
			if (d < dis)
				dis = d;
		}
		return Config.MAX_SPEED[b.getType()] / dis * 5 * threatenAreaFactor;
	}

	public static void debug() {
		System.out.println("SafetyEval Debug...");
		for (Boid x : Main.getBoids()) {
			// System.out.println(allySafety(x));
			// System.out.println(enemyDanger(x));
			// System.out.println(bushSafety(x));
			// System.out.println(threatenAreaDanger(x));
			System.out.println(x+"  "+getSafetyIndex(x));
		}
	}

}
