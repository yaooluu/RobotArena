package pathfinding;

import java.util.*;

public class PathFinding {

	/**
	 * Heuristic function for A*
	 * 
	 * @param graph
	 * @param node
	 * @param goal
	 * @return H(node, goal)
	 */
	private double Heuristic(Graph graph, int node, int goal) {
		return graph.getNodePos(node).minus(graph.getNodePos(goal)).getLength();
	}

	/**
	 * Dijkstra's algorithm, calculate the shortest path from start to end
	 * 
	 * @param graph: the Graph structure
	 * @param start: id of start node
	 * @param end  : id of end node
	 * @return a list of node id, representing the shortest path
	 */
	public static List<Integer> Dijkstra(Graph graph, int start, int end) {// System.out.println("Calculating: "+start+", "+end);
		List<Integer> path = new ArrayList<Integer>();
		int[] prev = new int[graph.maxId() + 1];

		double[] csf = new double[graph.maxId() + 1];
		for (int i = 0; i < csf.length; i++)
			csf[i] = Double.MAX_VALUE - 1;
		csf[start] = 0;

		Set<Integer> open = new HashSet<Integer>();
		Set<Integer> close = new HashSet<Integer>();

		open.add(start);
		while (open.size() > 0) {

			// debug
			// System.out.print("OL: ");System.out.println(open);
			// System.out.print("CL: ");System.out.println(close);
			// System.out.println();

			// find smallest in open list
			int current = findSmallest(open, csf);

			// find goal, then terminate
			if (current == end)
				break;

			List<Connection> connections = graph.getConnections(current);

			for (int i = 0; i < connections.size(); i++) {
				int endNode = connections.get(i).getToNode();
				double endNodeCost = csf[current]
						+ connections.get(i).getCost();

				if (endNodeCost < csf[endNode]) {
					csf[endNode] = endNodeCost;
					prev[endNode] = current;

					if (close.contains(endNode))
						close.remove(endNode);
				}

				if (!close.contains(endNode))
					open.add(endNode);
			}
			open.remove(current);
			close.add(current);
		}

		// construct shortest path
		int node = end;
		path.add(node);
		while (node != start) {
			node = prev[node];
			path.add(0, node);
		}
		return path;
	}

	/**
	 * A* algorithm, calculate the shortest path from start to end
	 * 
	 * @param graph: the Graph structure
	 * @param start: id of start node
	 * @param end  : id of end node
	 * @return a list of node id, representing the shortest path
	 */
	public List<Integer> AStar(Graph graph, int start, int end) {
		List<Integer> path = new ArrayList<Integer>();
		int[] prev = new int[graph.maxId() + 1];

		double[] csf = new double[graph.maxId() + 1];
		double[] etc = new double[graph.maxId() + 1];
		for (int i = 0; i < csf.length; i++)
			csf[i] = Double.MAX_VALUE - 1;
		csf[start] = 0;
		etc[start] = Heuristic(graph, start, end);

		Set<Integer> open = new HashSet<Integer>();
		Set<Integer> close = new HashSet<Integer>();

		open.add(start);
		while (open.size() > 0) {

			// debug
			// System.out.print("OL: ");System.out.println(open);
			// System.out.print("CL: ");System.out.println(close);
			// System.out.println();

			// find smallest in open list
			int current = findSmallest(open, etc);

			// find goal, then terminate
			if (current == end)
				break;

			List<Connection> connections = graph.getConnections(current);

			for (int i = 0; i < connections.size(); i++) {
				int endNode = connections.get(i).getToNode();
				double endNodeCost = csf[current]
						+ connections.get(i).getCost();

				if (endNodeCost < csf[endNode]) {
					csf[endNode] = endNodeCost;
					etc[endNode] = Heuristic(graph, current, end)
							+ csf[endNode];
					prev[endNode] = current;

					if (close.contains(endNode))
						close.remove(endNode);
				}

				if (!close.contains(endNode))
					open.add(endNode);
			}
			open.remove(current);
			close.add(current);
		}

		// construct shortest path
		int node = end;
		path.add(node);
		while (node != start) {
			node = prev[node];
			path.add(0, node);
		}
		return path;
	}

	private static int findSmallest(Set<Integer> list, double[] arr) {
		int index = -1;
		double min = Double.MAX_VALUE;

		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
			int i = it.next();
			if (arr[i] < min) {
				index = i;
				min = arr[i];
			}
		}
		return index;
	}
}
