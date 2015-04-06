package main;

import java.awt.Color;

import processing.core.PApplet;

public class Config {
	
	public static PApplet canvas = null; 

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static int FRAME_RATE = 60;
	public static int TILE_SIZE = 2;
	public static int MAX_FUEL = 100;
	
	//draw boid's vision and auditory on screen
	public static boolean showVision = true;
	
	//buff countdown time (seconds)
	public static int BUFF_COUNTDOWN = 15;
	
	//number of boids per team
	public static int[] TEAM_SIZE = {1, 1};
	
	public static double[] NUM_BOID = {0.1, 0.4, 0.1, 0.1, 0.1, 0.1, 0.1};
	
	public static enum BOID_TYPE {
		scout(0), soldier(1), tank(2), hero(3), commander(4);
		private int value;
		BOID_TYPE(int value) { this.value = value;}
		public int value() {return this.value;}
	};
	
	public static int[] BOID_SIZE = 		{15, 20, 35, 25, 25};
	public static int[] BOID_VISION = 		{120, 90, 90, 90, 90};
	public static int[] BOID_AUDITORY = 	{30, 15, 10, 20, 20};
	public static int[] BOID_FUEL = 		{100, 200, 300, 200, 200};
	public static int[] BOID_MASS =			{200, 300, 600, 250, 350};
	

	public static float[] MAX_LINACC = 		{200, 100, 300, 100, 300};
	public static float[] MAX_ANGACC = 		{5, 4, 2, 100, 100};
	public static float[] MAX_SPEED =  		{200, 150, 100, 200, 150};

	public static Color[] TEAM_COLORS = {Color.RED, Color.BLUE, Color.GREEN,
										Color.MAGENTA, Color.ORANGE};
	
}
