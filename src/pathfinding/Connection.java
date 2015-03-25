package pathfinding;

public class Connection {
	private double cost;
	private int fromNode;
	private int toNode;
	
	public Connection(int fromNode, int toNode, double cost) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = cost;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getFromNode() {
		return fromNode;
	}
	public void setFromNode(int fromNode) {
		this.fromNode = fromNode;
	}
	public int getToNode() {
		return toNode;
	}
	public void setToNode(int toNode) {
		this.toNode = toNode;
	}
}
