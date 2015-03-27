package decisionmaking;

import environment.Boid;

public class DecisionTree {

	public static String makeDecision(Boid b) {
		return RootNode.traverse(b);
	}
}
