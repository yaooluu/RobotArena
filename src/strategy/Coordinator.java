package strategy;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import main.Main;
import environment.Boid;

public class Coordinator {

	public static void selfDefenseStrategy(int team) {
		HashMap<Float, Boid> index = new HashMap<Float, Boid>();
		for (Boid b : Main.getBoids()) {
			if (b.getTeam() != team)
				continue;
			index.put(SafetyEval.getSafetyIndex(b), b);
		}
		// System.out.println("Safety Eval for team " + team + "...");
		System.out.println(index); // debug information
		float limit = Float.NEGATIVE_INFINITY;
		while (true) {
			Map.Entry<Float, Boid> danger = findDangerous(index, limit);
			if (danger == null || saveMe(danger.getValue()))
				break;
			limit = (float) danger.getKey();
		}

	}

	public static boolean saveMe(Boid b) {
		System.out.println("we're saving " + b);
		return false;
	}

	@SuppressWarnings("rawtypes")
	private static Map.Entry<Float, Boid> findDangerous(HashMap h,
			float lowerlimit) {
		@SuppressWarnings("unchecked")
		Set<Float> indices = h.keySet();
		float min = lowerlimit;
		for (float s : indices) {
			if (s > lowerlimit && s < min)
				min = s;
		}
		if (min == lowerlimit)
			return null;
		return new AbstractMap.SimpleEntry<Float, Boid>(min, (Boid) h.get(min));
	}
}
