package environment;

import java.awt.Color;
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
}