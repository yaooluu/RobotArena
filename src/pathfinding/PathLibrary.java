package pathfinding;

import java.util.List;

import environment.World;
import main.Main;
import physics.*;

public class PathLibrary {

	public static Vec2D getNextTarget(Vec2D pos, Vec2D targetPos) {
		Graph graph = Main.getGraph();
		int start = World.quantize(pos);
		int end = World.quantize(targetPos);
		
		if(start == end) return targetPos;

		List<Integer> path = PathFinding.AStar(graph, start, end);
		return graph.getNodePos(path.get(0));
	}

}
