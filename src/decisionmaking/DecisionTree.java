package decisionmaking;

import behavior.Attack;
import behavior.Buff;
import behavior.Evade;
import behavior.Trace;
import behavior.Wander;
import main.Config;
import environment.Boid;

public class DecisionTree {

	public static String makeDecision(Boid b) {
		String action = RootNode.traverse(b);
		System.out.println(action);
		return action;
	}

	public static void performDecisionTree(Boid b) {
		Boid enemy = b.getVisibleEnemy() != null ? b.getVisibleEnemy() : b
				.getAudibleEnemy();
		switch (DecisionTree.makeDecision(b)) {
		case "attack":
			Attack.goAttack(b, enemy);
			break;

		case "evade":
			Evade.evade(b, enemy);
			break;

		case "redbuff":
			Buff.goBuff(b, "red");
			break;
			
		case "bluebuff":
			Buff.goBuff(b, "blue");
			break;
			
		case "trace":
			Trace.trace(b, enemy);
			break;
			
		case "wander":
			Wander.wander(b);
			break;
			
		case "lure":
			//TODO
			break;
			
		case "ultimate":
			//TODO
			break;

		case "guard":
			//TODO
			break;
			
		case "hide":
			//TODO
			break;
		}
	}

	public static void main(String[] args) {
		Boid b1 = new Boid(20, 30, 2, 2, Config.BOID_TYPE.scout, 1);
		String ss = DecisionTree.makeDecision(b1);
		System.out.println(ss);
	}
}
