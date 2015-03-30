package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Config;
import physics.Vec2D;

public class Graph {

	
	private HashMap<Integer, Vec2D> nodePos = null;
	public HashMap<Integer, Vec2D> getNodePos() {
		return nodePos;
	}

	private HashMap<Integer, List<Connection>> connectionLists = null;
	private int maxId = -1;
	private int screenWidth = 0;

	public Graph() {
		nodePos = new HashMap<Integer, Vec2D>();
		connectionLists = new HashMap<Integer, List<Connection>>();
		screenWidth = Config.SCREEN_WIDTH;
	}

	public void setNodePos(int nodeIndex, Vec2D pos) {
		if (nodePos.containsKey(nodeIndex))
			nodePos.replace(nodeIndex, pos);
		else
			nodePos.put(nodeIndex, pos);
	}

	public Vec2D getNodePos(int nodeIndex) {
		return nodePos.get((Object) nodeIndex);
	}

	public int getNodeCount() {
		return nodePos.keySet().size();
	}

	public void addConnection(int from, int to, int cost) {
		Connection conn = new Connection(from, to, cost);

		if (connectionLists.containsKey(from) == false) {
			List<Connection> list = new ArrayList<Connection>();
			list.add(conn);
			connectionLists.put(from, list);
		} else {
			connectionLists.get(from).add(conn);
		}
	}

	// return list of outgoing connections from given node
	public List<Connection> getConnections(int fromNode) {
		List<Connection> list = new ArrayList<Connection>();
		if (connectionLists.containsKey(fromNode))
			list = connectionLists.get(fromNode);
		return list;
	}

	// get number of vertices in graph
	public int order() {
		return connectionLists.size();
	}

	// get number of edges in graph
	public int size() {
		int size = 0;
		for (Integer key : connectionLists.keySet()) {
			size += connectionLists.get(key).size();
		}
		return size;
	}


	public int[] getRowColFromNode(int node) {
		int[] pixel = new int[2];
		pixel[0] = node % screenWidth;
		pixel[1] = node / screenWidth + 1;
		if (node % screenWidth == 0) {
			pixel[0] = screenWidth;
			pixel[1]--;
		}
		return pixel;
	}

	// debug, print adjacency list of graph
	public void printAdjacencyList() {
		for (Integer key : connectionLists.keySet()) {
			System.out.print(key + ":");
			List<Connection> vals = connectionLists.get(key);
			for (int i = 0; i < vals.size(); i++) {
				System.out.print(vals.get(i).getToNode() + ",");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		g.setNodePos(0, new Vec2D(312, 21));
		g.setNodePos(1, new Vec2D(12, 111));
		g.setNodePos(1, new Vec2D(1, 11));
		System.out.println(g.getNodePos(5));
		System.out.println(g.getNodeCount());
	}
}
