package environment;

import java.awt.Color;

import processing.core.PApplet;
import main.Config;

public class RGB {
	float r;
	float g;
	float b;
	public RGB(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public static RGB getTeamColor(int team) {
		Color c = Config.TEAM_COLORS[team];
		return new RGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public static boolean isBlack(int rgb) {
		if(Config.canvas.red(rgb) < 5 
				&& Config.canvas.green(rgb) < 5 
				&& Config.canvas.blue(rgb) < 5) {
			return true;
		}
		else return false;
	}
	
	public static boolean isRed(int rgb) {
		if(Config.canvas.red(rgb) > 250
				&& Config.canvas.green(rgb) < 5 
				&& Config.canvas.blue(rgb) < 5) {
			return true;
		}
		else return false;
	}
	
	public static boolean isWhite(int rgb) {
		if(Config.canvas.red(rgb) > 250 
				&& Config.canvas.green(rgb) > 250
				&& Config.canvas.blue(rgb) > 250) {
			return true;
		}
		else return false;
	}
}