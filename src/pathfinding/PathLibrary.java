package pathfinding;

import java.util.List;

import environment.World;
import main.Main;
import physics.*;

public class PathLibrary {

	public static Vec2D getNextTarget(Vec2D pos, Vec2D targetPos) {
		//if(true) return null;
		Graph graph = Main.getGraph();
		int start = World.quantize(pos);
		int end = World.quantize(targetPos);
		//System.out.println("Quantize result: startNode: "+start+", endNode:"+end);
		if(start == end) return targetPos;
		
		//System.out.println("calculating path...");

		List<Integer> path = PathFinding.AStar(graph, start, end);
		int index = 0;
		if(pos.minus(graph.getNodePos(path.get(index))).getLength() < 5) {
			//System.out.println("seeking next key point...");
			index++;
		}
		System.out.println(path);
		
		if(index < path.size())
			return graph.getNodePos(path.get(index)+1);
		else return targetPos;
	}

}
