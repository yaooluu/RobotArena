package strategy;

import java.util.List;

import main.Config;
import main.Main;
import pathfinding.Graph;
import pathfinding.PathFinding;
import physics.Vec2D;
import environment.Boid;
import environment.World;

public class CostEval {

	public static float pathCost(Boid act, Boid target) {
		Graph g = Main.getGraph();
		float result = 0;
		Vec2D s = act.pos;
		Vec2D t = target.pos;
		List<Integer> path = PathFinding.AStar(g, World.quantize(s),
				World.quantize(t));
		for (Boid test : Main.getBoids()) {
			if (test.getTeam() == act.getTeam()) // The same team.
				continue;
			float dis = Float.MAX_VALUE;
			for (int node : path) {
				Vec2D p = g.getNodePos(node);
				if (!World.detectAccessible(test, p))
					continue;
				float d_t = p.minus(test.pos).getLength();
				if (d_t < dis)
					dis = d_t;
			}
			result += Config.MAX_LINACC[test.getType()] / dis;
		}
		return result;
	}
	

	public static void debug() {
		System.out.println("CostEval debug...");
		Boid t1 = Main.getBoids().get(3);
		Boid t2 = Main.getBoids().get(4);
		System.out.println(pathCost(t1, t2));
	}
}
