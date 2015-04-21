package main;

import processing.core.PApplet;

public class MainWrapper {

	public static void main(String[] args) {
		//full screen
		//PApplet.main(new String[] { "--present", "main.Main" });
		
		//original size
		PApplet.main("main.Main");
	}

}
