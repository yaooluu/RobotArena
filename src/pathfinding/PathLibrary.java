package pathfinding;

import java.util.List;

import environment.Boid;
import environment.World;
import main.Main;
import physics.*;

public class PathLibrary {

	public static Vec2D getNextTarget(Vec2D pos, Vec2D targetPos) {
		//if(true) return null;
		if(World.detectAccessible(pos, targetPos) == true) return targetPos;
		
		Graph graph = Main.getGraph();
		int start = World.quantize(pos);
		int end = World.quantize(targetPos);
		//System.out.println("Quantize result: startNode: "+start+", endNode:"+end);
		if(start == end) return targetPos;
		
		//System.out.println("calculating path...");

		List<Integer> path = PathFinding.AStar(graph, start, end);
		//System.out.println("Current Path:" + path);
		
		int curSeek = path.get(0);
		for(int i=0;i<path.size()-1;i++) {
			for(int j=i+1;j<path.size();j++) {
				Vec2D p1 = graph.getNodePos(path.get(i));
				Vec2D p2 = graph.getNodePos(path.get(j));
				float len = pos.minus(p1).getLength() + pos.minus(p2).getLength();
				if(len < p1.minus(p2).getLength() + 1) {
					curSeek = path.get(j);
					break;
				}			
			}
			if(curSeek != path.get(0)) break;
		}
		
		//System.out.println("Current Seeking Point:" + curSeek);
		
		//if(curSeek == end) return targetPos;
		return graph.getNodePos(curSeek);
	}

}
