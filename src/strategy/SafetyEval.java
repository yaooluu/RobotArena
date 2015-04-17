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
	static float threatenAreaFactor = 0.2f;

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
			float dis = b.pos.minus(t.pos).getLength();
			result += dis / Config.MAX_SPEED[t.getType()] * allyFactor;
		}
		return result;
	}

	private static float enemyDanger(Boid b) {
		float result = 0;
		for (Boid t : Main.getBoids()) {
			if (t.getTeam() == b.getTeam())
				continue;// not enemy
			if (!World.detectAccessible(b.pos, t.pos))
				continue;// block by something
			float dis = b.pos.minus(t.pos).getLength();
			result += dis / Config.MAX_SPEED[t.getType()] * enemyDanger;
		}
		return result;
	}

	private static float bushSafety(Boid b) {
		return b.findHide().minus(b.pos).getLength() * bushFactor;
	}

	// considering the linear distance ONLY
	private static float threatenAreaDanger(Boid b) {
		float dis = Float.MAX_VALUE;
		for (Border bor : World.getBorders()) {
			float d = bor.borderVec.minus(b.pos).getLength();
			if (d < dis)
				dis = d;
		}
		return dis * threatenAreaFactor;
	}

}
