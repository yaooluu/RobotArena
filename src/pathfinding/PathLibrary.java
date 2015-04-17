package pathfinding;

import environment.*;
import main.*;
import physics.*;

public class PathLibrary {

	public static Vec2D getNextTarget(Boid b, Vec2D targetPos) {
		
		Graph graph = Main.getGraph();
		
		//debug, draw key points on path
		if(Config.drawKeyPoints) {
			for(int i=0;i<b.curPath.size();i++) {
				Vec2D vec = graph.getNodePos(b.curPath.get(i));
				//Config.canvas.fill(255,0,0);
				Config.canvas.ellipse(vec.x, vec.y, 5, 5);
				Config.canvas.text(b.curPath.get(i), vec.x + 3, vec.y - 3);
			}
		}
		
		if(World.detectAccessible(b, targetPos) == true) {
			b.curPath.clear();
			return targetPos;
		}

		int start = World.quantize(b.pos);
		int end = World.quantize(targetPos);
		
		b.curPath = PathFinding.AStar(graph, start, end);
			
		//filter key points, find the farthest reachable one
		while(b.curPath.size() >= 2) {
			Vec2D vec = graph.getNodePos(b.curPath.get(1));
			if(World.detectAccessible(b, vec))
				b.curPath.remove(0);
			else break;
		}		

		return graph.getNodePos(b.curPath.get(0));
	}

	/*
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
				if(len < p1.minus(p2).getLength() + 10) {
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
	*/

}
