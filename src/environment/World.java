package environment;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import main.Config;
import main.Main;
import pathfinding.Graph;
import physics.Vec2D;
import processing.core.PApplet;
import processing.core.PImage;

public class World {
	private static int width = Config.SCREEN_WIDTH;
	private static int height = Config.SCREEN_HEIGHT;

	public static Graph createGraphFromImage(int pixels[]) {
		Graph graph = new Graph();

		int nodeIndex = 0;
		System.out.println("Initializeing pixels[]...");
		// detect the vertices of Dirichlet Domain
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				// scanning in pixels[j*width+i]
				if (pixels[j * width + i] == 0x00FF0000) {
					// RED pixel indicates Dirichlet Domain vertices
					System.out.println("Found Dirichlet vertex!");
					graph.setNodePos(nodeIndex, new Vec2D(i, j));
					nodeIndex++;
				}
			}

		// create the edges in Dirichlet Domain
		int V = graph.getNodeCount();
		for (int i = 0; i < V; i++)
			for (int j = i + 1; j < V; j++) {
				Vec2D u = graph.getNodePos(i);
				Vec2D v = graph.getNodePos(j);
				if (detectAccessible(u, v, pixels)) {
					int cost = (int) u.minus(v).getLength();
					graph.addConnection(i, j, cost);
					graph.addConnection(j, i, cost);
				}
			}
		return graph;
	}

	/**
	 * @param point
	 * @return the index of the quantized vertex
	 */
	public static int quantize(Vec2D point) {
		int result = -1;
		float dis = Float.MAX_VALUE;
		float temp;
		Iterator<Entry<Integer, Vec2D>> it = Main.getGraph().getNodePos()
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Vec2D> pair = (Map.Entry<Integer, Vec2D>) it.next();
			Vec2D v = (Vec2D) pair.getValue();
			temp = v.minus(point).getLength();
			if (temp < dis) {
				dis = temp;
				result = (int) pair.getKey();
			}
		}
		return result;
	}

	private static boolean detectAccessible(Vec2D u, Vec2D v, int[] pixels) {
		int length = (int) u.minus(v).getLength() - 1;
		for (int i = 0; i < length; i++) {
			Vec2D t = v.minus(u).getUnitVec().multiply(i);
			int x = (int) t.x;
			int y = (int) t.y;
			if (pixels[y * width + x] != 0xFFFFFF) // TODO modify access
													// indicator
				return false;
		}
		return true;
	}
}

/*
 * Tile Graph
 */
class World2 {

	private static float tileSize = Config.TILE_SIZE;
	private static int width = Config.SCREEN_WIDTH;
	private static int height = Config.SCREEN_HEIGHT;

	// create graph from image using tile based division scheme
	// input: empty graph
	public static void createGraphFromImage(Graph graph, PApplet parent) {
		PImage environment;
		environment = parent.loadImage("./src/environment/Environment.png");
		environment.loadPixels();
		int pixels[] = environment.pixels;
		int nodeIndex = 0;
		// number of tile on each row
		int numberOfRow = (int) Math.floor((height / tileSize));
		// number of tile on each column
		int numberOfColumn = (int) Math.floor((width / tileSize));
		// record map type
		int numberOfNodes = width * height;
		int[] node = new int[numberOfNodes];

		// quantization
		for (int y = 0; y < height; y += tileSize) {
			for (int x = 0; x < width; x += tileSize) {
				int pixel_index = (int) (Math.floor(x + tileSize / 2) + Math
						.floor((y + tileSize / 2)) * width);
				int pixel_value = pixels[pixel_index] & 0xFF;

				// if the middle tile value is 0, then this node is closed
				if (pixel_value == 0) {
					node[nodeIndex] = 0;
				}
				// else open node, add connection(need to consider type)
				else {
					node[nodeIndex] = 1;
				}
				nodeIndex++;
			}
		}
		// make connections
		for (nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
			if (node[nodeIndex] == 1) {
				// connect left node
				if ((nodeIndex - 1) % numberOfColumn != 0 && nodeIndex - 1 >= 0
						&& node[nodeIndex - 1] == 1) {
					graph.addConnection(nodeIndex, nodeIndex - 1, 1);
				}
				// connect right node
				if ((nodeIndex + 1) % numberOfColumn != 0
						&& (nodeIndex + 1) < numberOfNodes
						&& node[nodeIndex + 1] == 1) {
					graph.addConnection(nodeIndex, nodeIndex + 1, 1);

				}
				// connect upper node
				if ((nodeIndex - numberOfColumn) >= 0
						&& node[nodeIndex - numberOfColumn] == 1) {
					graph.addConnection(nodeIndex, nodeIndex - numberOfColumn,
							1);

				}
				// connect lower node
				if ((nodeIndex + numberOfColumn) < numberOfNodes
						&& node[nodeIndex + numberOfColumn] == 1) {
					graph.addConnection(nodeIndex, nodeIndex + numberOfColumn,
							1);
				}
			}
		}
	}

}
