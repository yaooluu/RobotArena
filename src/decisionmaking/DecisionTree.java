package decisionmaking;

import main.Config;
import environment.Boid;

public class DecisionTree {

	public static String makeDecision(Boid b) {
		return RootNode.traverse(b);
	}
	
	public static void main(String[] args){
		Boid b1 = new Boid(20, 30, 2, 2, Config.BOID_TYPE.scout, 1);
		String ss = DecisionTree.makeDecision(b1);
		System.out.println(ss);
	}
}
