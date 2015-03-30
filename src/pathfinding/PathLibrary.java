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
		//System.out.println("Quantize result: startNode: "+start+", endNode:"+end);
		if(start == end) return targetPos;
		//System.out.print("calculating path...");

		List<Integer> path = PathFinding.AStar(graph, start, end);
		System.out.println(path);
		return graph.getNodePos(path.get(0));
	}

}
