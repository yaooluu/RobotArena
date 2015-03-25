package pathfinding;

import java.util.*;
import main.Config;

public class Graph {
	
	private HashMap<Integer, List<Connection>> connectionLists = null;
	private int maxId = -1;
	private int screenWidth = 0;
	
	public Graph() {	
		connectionLists = new HashMap<Integer, List<Connection>>();
		screenWidth = Config.SCREEN_WIDTH;
	}
	
	public void addConnection(int from, int to, int cost) {
		Connection conn = new Connection(from, to, cost);
		
		if(connectionLists.containsKey(from) == false) {
			List<Connection> list = new ArrayList<Connection>();
			list.add(conn);
			connectionLists.put(from, list);
		}else{
			connectionLists.get(from).add(conn);
		}
	}
	
	
	//return list of outgoing connections from given node
	public List<Connection> getConnections(int fromNode) {
		List<Connection> list = new ArrayList<Connection>();
		if(connectionLists.containsKey(fromNode))
			list = connectionLists.get(fromNode);
		return list;
	}
	
	//get number of vertices in graph
	public int order() {
		return connectionLists.size();
	}
	
	//get number of edges in graph
	public int size() {
		int size = 0;
		for(Integer key : connectionLists.keySet()) {
			size += connectionLists.get(key).size();
		}
		return size;
	}
	
	//get max node id
	public int maxId() {
		if(maxId == -1) {
			for(Integer key : connectionLists.keySet())
				if(key > maxId) maxId = key;
		}
		return maxId;
	}
	
	public int[] getRowColFromNode(int node) {
		int[] pixel = new int[2];
		pixel[0] = node % screenWidth;
		pixel[1] = node / screenWidth + 1;
		if(node % screenWidth == 0) {
			pixel[0] = screenWidth;
			pixel[1]--;
		}
		return pixel;
	}
	
	//debug, print adjacency list of graph
	public void printAdjacencyList() {
		for(Integer key:connectionLists.keySet()) {
			System.out.print(key + ":");
			List<Connection> vals = connectionLists.get(key);
			for(int i=0;i<vals.size();i++) {
				System.out.print(vals.get(i).getToNode() + ",");
			}
			System.out.println();
		}
	}

}
