package environment;

import java.awt.Color;
import main.Config;

public class RGB {
	public float r;
	public float g;
	public float b;
	public RGB(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	//threshold for color compare difference
	private static float threshold = 5;
	
	public static RGB getTeamColor(int team) {
		Color c = Config.TEAM_COLORS[team];
		return new RGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	//color for blank space
	public static boolean isWhite(int rgb) {
		if(Config.canvas.red(rgb) > 250 
				&& Config.canvas.green(rgb) > 250
				&& Config.canvas.blue(rgb) > 250) {
			return true;
		}
		else return false;
	}
	
	//color(100,100,100) for wall 
	public static boolean isBlack(int rgb) {
		if(Math.abs(Config.canvas.red(rgb) - 100) < threshold 
				&& Math.abs(Config.canvas.green(rgb) - 100) < threshold 
				&& Math.abs(Config.canvas.blue(rgb) - 100) < threshold) {
			return true;
		}
		else return false;
	}
	
	//color(200,200,200) for border/trap
	public static boolean isGrey(int rgb) {
		if(Math.abs(Config.canvas.red(rgb) - 200) < threshold 
				&& Math.abs(Config.canvas.green(rgb) - 200) < threshold 
				&& Math.abs(Config.canvas.blue(rgb) - 200) < threshold) {
			return true;
		}
		else return false;
	}
	
	//color(255,0,0) for red buff
	public static boolean isRed(int rgb) {
		if(Math.abs(Config.canvas.red(rgb) - 255) < threshold 
				&& Math.abs(Config.canvas.green(rgb) - 0) < threshold 
				&& Math.abs(Config.canvas.blue(rgb) - 0) < threshold) {
			return true;
		}
		else return false;
	}
	
	//color(0,0,255) for blue buff
	public static boolean isBlue(int rgb) {
		if(Math.abs(Config.canvas.red(rgb) - 0) < threshold 
				&& Math.abs(Config.canvas.green(rgb) - 0) < threshold 
				&& Math.abs(Config.canvas.blue(rgb) - 255) < threshold) {
			return true;
		}
		else return false;
	}
	
	//color(0,255,0) for Dirichlet point
	public static boolean isGreen(int rgb) {
		if(Math.abs(Config.canvas.red(rgb) - 0) < threshold 
				&& Math.abs(Config.canvas.green(rgb) - 255) < threshold 
				&& Math.abs(Config.canvas.blue(rgb) - 0) < threshold) {
			return true;
		}
		else return false;
	}
	

}