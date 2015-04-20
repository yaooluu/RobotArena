package environment;

import java.util.*;
import java.util.Map.Entry;

import main.Config;
import main.Main;
import pathfinding.Graph;
import physics.Vec2D;
import processing.core.PApplet;
import processing.core.PImage;

public class World {
	
	//store walls
	private static List<Wall> walls = new ArrayList<Wall>();
	public static List<Wall> getWalls() {return walls;}
	
	//store borders
	private static List<Border> borders = new ArrayList<Border>();
	public static List<Border> getBorders() {return borders;}

	//store buffs
	private static List<Buff> buffs = new ArrayList<Buff>();
	public static List<Buff> getBuffs() {return buffs;}

	//store hides
	private static List<Shelter> shelters = new ArrayList<Shelter>();
	public static List<Shelter> getShelters() {return shelters;}
	
	//debug, store Dirichlet points
	private static List<Vec2D> keypoints = new ArrayList<Vec2D>();
	public static List<Vec2D> getKeyPoints() {return keypoints;}
	
	private static int[] pixels = null;
	public static int[] getPixels() {return pixels;}
	
	private static int width = Config.SCREEN_WIDTH;
	private static int height = Config.SCREEN_HEIGHT;

	public static Graph createGraphFromImage(PApplet parent) {
		Graph graph = new Graph();
		PImage environment;
		environment = parent.loadImage("../src/environment/Environment.png");
		environment.loadPixels();
		pixels = environment.pixels;

		int nodeIndex = 0;
		System.out.println("Initializeing pixels[]...");

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pix = pixels[j * width + i];
				
				// scan Dirichlet Domain vertices (green)
				if (RGB.isGreen(pix)) {
					graph.setNodePos(nodeIndex, new Vec2D(i, j));
					nodeIndex++;
					keypoints.add(new Vec2D(i,j));
				}
				
				//scan and store walls (black) and borders (grey)
				if (i>0 && i<width-1 && j>0 && j<height-1
						&& (RGB.isBlack(pix) || RGB.isGrey(pix))) {
					
					//only store margin of wall, and it's vector
					int top = pixels[j * width + i - width];
					int bottom = pixels[j * width + i + width];
					int left = pixels[j * width + i - 1];
					int right = pixels[j * width + i + 1];
					Vec2D vec = new Vec2D(0, 0);
					
					int count = 0;
					if(RGB.isWhite(top)) {vec.y = -1;count++;}
					if(RGB.isWhite(bottom)) {vec.y = 1;count++;}
					if(RGB.isWhite(left)) {vec.x = -1;count++;}
					if(RGB.isWhite(right)) {vec.x = 1;count++;}
					
					if(count>0) {
						if(RGB.isBlack(pix))
							walls.add(new Wall(i,j,vec));
						else 
							borders.add(new Border(i,j,vec));
					}
				}
				
				//scan and store buff locations
				if(RGB.isRed(pix) || RGB.isBlue(pix)) {
					int type = 0;
					if(RGB.isBlue(pix)) type = 1;
					buffs.add(new Buff(i,j,type));
				}
				
				//scan and store shelter locations
				if(RGB.isPurple(pix)) {
					shelters.add(new Shelter(i,j));
				}	
			}
		}

		// create the edges in Dirichlet Domain
		int V = graph.getNodeCount();
		for (int i = 0; i < V; i++)
			for (int j = i + 1; j < V; j++) {
				Vec2D u = graph.getNodePos(i);
				Vec2D v = graph.getNodePos(j);
				if (detectAccessible(u, v)) {
					int cost = (int) u.minus(v).getLength();
					graph.addConnection(i, j, cost);
					graph.addConnection(j, i, cost);
				}
			}
		System.out.println("Graph initialized with "+graph.order()+" nodes, " + graph.size()/2 + " edges.");
		System.out.println("Found " + buffs.size() + " buffs on map.");
		System.out.println("Found " + shelters.size() + " shelters on map.");
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
			Map.Entry<Integer, Vec2D> pair = (Map.Entry<Integer, Vec2D>) it
					.next();
			Vec2D v = (Vec2D) pair.getValue();
			temp = v.minus(point).getLength();
			if (temp < dis) {
				dis = temp;
				result = (int) pair.getKey();
			}
		}
		return result;
	}

	//use 1-ray casting to detect accessible
	public static boolean detectAccessible(Vec2D u, Vec2D v) {
		int sampleRate = 100;
		float dis = u.minus(v).getLength();
		dis /= sampleRate;
		for (int i = 0; i < sampleRate; i++) {
			
			Vec2D t = v.minus(u).getUnitVec().multiply(dis * i).plus(u);
			int x = (int) (t.x);
			int y = (int) (t.y);
			if ((x == u.x && y == u.y) || (x == v.x && y == v.y))
				continue;
			if (RGB.isBlack(pixels[y * width + x]) == true
					|| RGB.isGrey(pixels[y * width + x]) == true) { //white color
				return false;
			}
			
			//debug
			if(Config.drawRayCasting) {
				Config.canvas.fill(255,0,0);
				Config.canvas.ellipse(t.x, t.y, 1.5f, 1.5f);
			}
		}		
		return true;
	}
	
	//use 3-ray casting to detect accessible
	public static boolean detectAccessible(Boid b, Vec2D v) {	
		
		/*
		//System.out.println(v);
		Vec2D vec = b.pos.minus(v);
		vec.truncate(1);
		vec = vec.multiply(b.getSize() / 3);
		vec = v.plus(vec);
		Config.canvas.fill(0);
		Config.canvas.ellipse(vec.x, vec.y, 3, 3);
		//System.out.println(v);System.out.println();
		//*/

		boolean middleRay = detectAccessible(b.pos, v);
		
		float x = -1 * v.minus(b.pos).y;
		float y = v.minus(b.pos).x;
		
		Vec2D offset = new Vec2D(x, y);
		offset.truncate(1);
		offset = offset.multiply(b.getSize()/2 + 2);
		Vec2D lv = v.plus(offset);
		
		//avoid left ray border misjudge
		if(lv.x < 50) lv.x = 50;
		if(lv.x > 800 - 50) lv.x = 750;
		if(lv.y < 50) lv.y = 50;
		if(lv.y > 600 - 50) lv.y = 550;
		//*/
				
		boolean leftRay = detectAccessible(b.pos.plus(offset), lv);
		
		offset = offset.multiply(-1);	
		Vec2D rv = v.plus(offset);
		
		//avoid right ray border misjudge
		if(rv.x < 50) rv.x = 50;
		if(rv.x > 800 - 50) rv.x = 750;
		if(rv.y < 50) rv.y = 50;
		if(rv.y > 600 - 50) rv.y = 550;
		//*/	
		
		boolean rightRay = detectAccessible(b.pos.plus(offset), v.plus(offset));
			
		return (middleRay && leftRay && rightRay);
	}

	public static void detectFallOff(List<Boid> boids) {
		List<Boid> tmpList = new ArrayList<Boid>();
		for(Boid b : boids) {
			int pix = pixels[(int)b.pos.y * width + (int)b.pos.x];
			if(RGB.isGrey(pix) == true) {
				Config.die_music.trigger();
				System.out.println(b + " is out of stage!");
				tmpList.add(b);
				
				Main.getLosers().add(b);
			}		
		}
		boids.removeAll(tmpList);
	}

	public static void updateShelterStatus(List<Boid> boids) {
		//update shelter taken status
		for(Shelter s : shelters) {
			boolean taken = false;			
			for(Boid b : boids) {
				if(s.minus(b.pos).getLength() < 5) {
					taken = true;
					break;
				}
			}
			s.isTaken = taken;
		}
		
		//updata boid hiding status
		for(Boid b : boids) {
			int curShelter = -1;
			for(int i=0;i<shelters.size();i++) {
				if(shelters.get(i).minus(b.pos).getLength() < 5) {
					curShelter = i;
					break;
				}
			}
			b.curShelter = curShelter;
		}
	}

	public static void applyFriction(List<Boid> boids) {
		float f = 0.00005f; 	//fraction factor
		for(Boid b :boids) {
			if(b.v.getLength() > 0) {
				Vec2D friction = new Vec2D(b.v).multiply(f * b.getMass());
				//System.out.println("Friction (speed loss): " + friction + " per frame");
				b.v.minusEqual(friction);
			}
			else b.v = new Vec2D(0,0);
		}		
	}

	public static void applyFuelConsumption(List<Boid> boids) {	
		float f = 0.0005f;	//fuel consumption rate 
		for(Boid b :boids) {
			if(b.fuel > 0) {
				if(b.v.getLength() > 0) {
					float loss = f * b.v.getLength() * b.getMass() / 200;
					//System.out.println(b.v.getLength());
					//System.out.println("Fuel loss rate: " + loss + " per frame");
					b.fuel -= loss; 
					if(b.fuel < 0) b.fuel = 0;
					//System.out.println(b + " fuel: " +b.fuel);
				}
			}
			else 
				b.fuel = 0;
		}	
		
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
		//int numberOfRow = (int) Math.floor((height / tileSize));
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
