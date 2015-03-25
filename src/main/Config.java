package main;

public class Config {

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static int FRAME_RATE = 60;
	public static int TILE_SIZE = 2;
	
	public static int BOID_SIZE = 20;
	public static int BOID_VISION_ANGLE = 600;
	public static int BOID_AUDIO_RADIUS = 600;
	
	public static enum BOID_TYPE {scout, soldier, tank, hero, commander};
	
	public static int[] MAX_LINACC = {100,100,100,100,100};
	public static int[]	MAX_ANGACC = {100,100,100,100,100};
	public static int[] MAX_SPEED = {200,150,100,200,150};
	
	public static int GROUP_SIZE = 10;
	public static double[] NUM_BOID = {0.1, 0.4, 0.1, 0.1, 0.1, 0.1, 0.1};
	

}
