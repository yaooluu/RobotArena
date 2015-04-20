package main;

import java.awt.Color;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import processing.core.PApplet;

public class Config {
	
	//debug parameters
	public static boolean drawRayCasting = false;
	public static boolean drawKeyPoints = false;
	public static boolean drawShelterPoints = false;
	
	public static boolean drawBoidVision = false;
	public static boolean drawBoidId = false;
	public static boolean drawBoidFuel = true;
	public static boolean drawBoidAction = true;
	public static boolean isMute = false;
	
	public static PApplet canvas = null; 
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static int FRAME_RATE = 60;
	public static int TILE_SIZE = 2;
	
	//buff re-spawn time (seconds)
	public static int BUFF_COUNTDOWN = 15;
	
	//buff effect time (seconds)
	public static int BUFF_DURATION = 10;
	
	//Percentage of low fuel
	public static float LOW_FUEL_RATE = 0.20f;
	
	//number of robots per team
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
	public static int[] BOID_MASS =			{200, 300, 600, 400, 350};
	
	public static float[] MAX_LINACC = 		{300, 200, 150, 250, 200};
	public static float[] MAX_ANGACC = 		{5, 3, 1.5f, 3.5f, 3};
	public static float[] MAX_SPEED =  		{220, 150, 100, 200, 150};

	public static Color[] TEAM_COLORS = {Color.RED, Color.BLUE, Color.GREEN,
										Color.MAGENTA, Color.ORANGE};
	
	
	public static AudioPlayer bk_music,win_music;
	public static AudioSample collision_music,die_music,ult_music,buff_music;
}
