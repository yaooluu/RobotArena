package main;

import java.util.*;
import decisionmaking.DecisionTree;
import pathfinding.Graph;
import physics.Collision;
import physics.Vec2D;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import strategy.CostEval;
import strategy.SafetyEval;
import environment.*;
import behavior.*;
import ddf.minim.*;


@SuppressWarnings("serial")
public class Main extends PApplet {

	private static List<Boid> boids = null;
	public static List<Boid> getBoids() {return boids;}
	
	private static Graph graph = null;
	public static Graph getGraph() {return graph;}
	PImage environment,grassMask;
	
	//debug
	private Vec2D mouseVec = null;
	private boolean pause = false;
	private static int mod = 1;
	
	
	//player
	private Player player;
	public static boolean[] arrowKeys = new boolean[4];
	
	
	public void setup() {
		
		Minim minim;//audio context
		
		minim = new Minim(this);
		Config.bk_music = minim.loadFile("../src/environment/background.wav", 2048);
		Config.bk_music.loop();
	  
		Config.ult_music=minim.loadSample("../src/environment/ultimate.wav", 2048);
		Config.collision_music=minim.loadSample("../src/environment/collision.mp3", 2048);
		Config.die_music=minim.loadSample("../src/environment/die.mp3", 2048);  
		Config.win_music=minim.loadFile("../src/environment/win.wav", 2048);
		Config.buff_music=minim.loadSample("../src/environment/buff.wav", 2048);
	  
		Config.canvas = this;
		boids = new ArrayList<Boid>();	
		environment=loadImage("../src/environment/GE.png");			

		grassMask=loadImage("../src/environment/Grass.png");		

		graph = World.createGraphFromImage(this);
		
		size(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		frameRate(Config.FRAME_RATE);
		
		
		//init players from two teams
		//initPlayers();

		debugPalyers();
		
		//SafetyEval.debug();
		//CostEval.debug();
	}

	private void debugPalyers() {
		//if debug team follow, comment out initPlayers();
		
		boids.add(new Boid(100, 150, 90, 0, Config.BOID_TYPE.scout, 1));
		//boids.add(new Boid(100, 250, 180, 0, Config.BOID_TYPE.soldier, 2));
		//boids.add(new Boid(100, 350, 90, 0, Config.BOID_TYPE.tank, 3));
		
		boids.add(new Boid(700, 200, 270, 1, Config.BOID_TYPE.scout, 4));
		//boids.add(new Boid(700, 300, 270, 1, Config.BOID_TYPE.soldier, 5));
		//boids.add(new Boid(700, 400, 270, 1, Config.BOID_TYPE.tank, 6));
		player=new Player(boids.get(0));
	}

	public void draw() {	
		//pause game when pressed space bar
		if(pause == false) {
			background(255);
			smooth(8);	
			Config.bk_music.play();
			
			
			drawEnvironment();

			mainLogic();

			//debugLogic();	
			
			Collision.allCollision(boids);
			Behavior.borderAvoid(boids);
			
			World.applyFriction(boids);	
			World.detectFallOff(boids);
			
			World.updateShelterStatus(boids);		
			World.applyFuelConsumption(boids);
				
			//draw all robots
			for(Boid b : boids) b.draw();
			
			//draw grass layer
			drawGrass();
			victoryJudge();

		} else {
			Config.bk_music.pause();
			drawText("Game Paused", 30, 30, "Georgia", 20, new RGB(255,0,0));
		}
		
	}

	private void initPlayers() {
		Boid[] b = new Boid[11];
		
		float span = 80, f = 1.2f;
		float ang = 50, x = 90, y = 520;
		b[1] = new Boid(x+span*f, y-span*f, ang, 0, Config.BOID_TYPE.scout, 1);
		b[2] = new Boid(x, y-span, 0, 0, Config.BOID_TYPE.soldier, 2);
		b[3] = new Boid(x+span, y, 90, 0, Config.BOID_TYPE.hero, 3);
		b[4] = new Boid(x+span/2, y-span/2, ang, 0, Config.BOID_TYPE.tank, 4);
		b[5] = new Boid(x, y, ang, 0, Config.BOID_TYPE.commander, 5);
		
		ang = 270 - ang; x = 710; y=85;
		b[6] = new Boid(x-span*f, y+span*f, ang, 1, Config.BOID_TYPE.scout, 6);
		b[7] = new Boid(x, y+span, 180, 1, Config.BOID_TYPE.soldier, 7);
		b[8] = new Boid(x-span, y, 270, 1, Config.BOID_TYPE.hero, 8);
		b[9] = new Boid(x-span/2, y+span/2, ang, 1, Config.BOID_TYPE.tank, 9);
		b[10] = new Boid(x, y, ang, 1, Config.BOID_TYPE.commander, 10);
		
		for(int i=1;i<b.length;i++) boids.add(b[i]);
		
		player=new Player(boids.get(0));
	}
	
	//main workflow here.
	private void mainLogic() {	
		
		if(player.b.fuel > 0)
			player.move();		  
	 	//player.controlTeam(boids);

		for(Boid b : boids) {						
			if(b!=player.b) {	
				if(b.fuel > 0)
					DecisionTree.PerformDecision(b);
				
				if(b.fuel > 0 || b.v.getLength() > 3)
					Behavior.update2(b);
				else {
					b.curBehavior = "";
					b.v.x = 0;
					b.v.y = 0;
					b.a.x = 0;
					b.a.y = 0;
				}
			}		
			//b.addBreadcrumb();
		  //b.showBreadcrumbs();
		}
	}
	
	//all debug workflow
	private void debugLogic() {
		
		if(this.mousePressed)
		{
			mouseVec = new Vec2D(mouseX, mouseY);		
			for(Boid b : boids)
				b.curPath.clear();			
		}		
		if(mouseVec!=null) ellipse(mouseVec.x, mouseVec.y, 20, 20);
		
		//if(boids.get(1).fuel > 0)
			//boids.get(1).getBuff("blue");

			//boids.get(1).attack(boids.get(0));
		//boids.get(1).evade(boids.get(0));
		/*
		for(int i=0;i<boids.size();i++) {
			Boid b = boids.get(i);
			if(frameCount % mod != 0) continue;
			mod = (int)Math.random()*90 + 30;
			if(i==0)b.wander();
			if(mouseVec!=null)
				Attack.goAttack(b, mouseVec);
			
		}//*/
		
		//Trace.trace(boids.get(1), player.b);
		//boids.get(1).trace(player.b);
		//boids.get(0).attack(boids.get(1));
		//boids.get(1).getBuff("blue");

		//Ultimate.ultimate(boids.get(1));

		//Tackle.tackle(boids.get(1), boids.get(0));
		//boids.get(3).getBuff("red");
		//boids.get(1).wander();
		
		//debug draw wall vectors
		/*
		int i = 0;	
		for(Wall w : World.getWalls()) {
			i++;
			if(i % 5!= 0) continue;
					
			stroke(255,0,0);
			strokeWeight(1);
			int f = 10;
			line(w.x,w.y,	w.x + w.collisionVec.x * f,	w.y + w.collisionVec.y * f);	
		}

		i = 0;	
		for(Border w : World.getBorders()) {
			i++;
			if(i % 5!= 0) continue;
					
			stroke(0,0,255);
			strokeWeight(1);
			int f = 10;
			line(w.x,w.y,	w.x + w.borderVec.x * f,	w.y + w.borderVec.y * f);	
		}
		*/
		
		//debug, draw shelters point
		if(Config.drawShelterPoints) {
			int i = 0;
			for(Shelter s : World.getShelters()) {
				drawText("s"+i, s.x, s.y, "Georgia", 16, new RGB(255,0,0));
				i++;
			}
		}
		
		//debug, draw Dirichlet points
		if(Config.drawKeyPoints) {
			int i = 0;
			for(Vec2D v : World.getKeyPoints()) {
				noStroke();
				fill(128,0,128);
				ellipse(v.x, v.y, 5, 5);
				drawText("D"+i, v.x + 5, v.y + 5, "Georgia", 16, new RGB(128,0,128));
				i++;
			}
		}
	}

	private void drawEnvironment() {	
		tint(255,255);	
		image(environment, 0, 0);
		
		//draw buffs
		///*
		for(Buff buff : World.getBuffs()) {
			if(buff.countdown > 0) {
				if(frameCount % Config.FRAME_RATE == 0)
					buff.countdown--;
				String buffText = "Red";
				if(buff.getType() == 1) buffText = "Blue";
				buffText += " in " + buff.countdown + " sec";
				if(buff.countdown > 1) buffText += "s";
				drawText(buffText, buff.x - 50, buff.y, "Georgia", 15, buff.getRGB());
			}
			else {
				float x = buff.x, y = buff.y, size = 10;
				RGB c = buff.getRGB();
				fill(c.r, c.g, c.b);
				noStroke();
				
				beginShape();
				vertex(x, y-size);
				vertex(x+size, y);
				vertex(x, y+size);
				vertex(x-size, y);
				endShape(CLOSE);
				
				//judge if buff is taken
				for(Boid b : boids) {
					if(b.pos.minus(buff).getLength() < b.getSize()/2) {
						buff.countdown = Config.BUFF_COUNTDOWN;
						
						//apply buff benefit
						if(buff.getType() ==0)
							b.fuel = Config.BOID_FUEL[b.getType()];
						else {
							//System.out.println(b + " get blue buff.");
							b.bonusAcc = Config.MAX_LINACC[b.getType()];
							b.bonusSpeed = Config.MAX_SPEED[b.getType()];
							b.bonusTime =  Config.BUFF_DURATION;
						}
						break;
					}
				}
			}
		}//*/
		
		//update blue buff bonus time
		for(Boid b : boids) {
			if(b.bonusTime > 0) {
				if(frameCount % Config.FRAME_RATE == 0)
					b.bonusTime--;
			}else if(b.bonusAcc > 0){
				//System.out.println(b + " lose blue buff.");
				b.bonusAcc = 0;
				b.bonusSpeed = 0;
			}
		}
	}
	
	private void drawGrass() {
		tint(255,180);
		image(grassMask,0,0);
		
		/*
		int i = 0;
		for(Shelter s : World.getShelters()) {
			if(i == 0 || i == 11) {
				noStroke();
				fill(0,255,0,100);
				rect(s.x - 20, s.y - 75 , 45 , 145);
			}
			else if(i == 3) {
				noStroke();
				fill(0,255,0,100);
				rect(s.x - 25, s.y - 22 , 300 , 45);
			}
			else if(i == 4) {
				noStroke();
				fill(0,255,0,100);
				rect(s.x - 25, s.y - 19 , 290 , 45);
			}
			i++;
		}*/
	}
	
	private void drawText(String text, float x, float y, String font, float size, RGB rgb) {
		PFont f = createFont(font,size,true);
		textFont(f,size);                 
		fill(rgb.r, rgb.g, rgb.b);                        
		text(text, x, y);
	}

	public void mousePressed() {
		//System.out.println("Mouse clicked: ("+mouseX+", "+mouseY+")");
		mouseVec = new Vec2D(mouseX, mouseY);
		
	}
	
	public void keyPressed() {
		//System.out.println("Key pressed: "+key);
		if(key == ' ')
			pause = !pause;
		
		else if(key == 'v')
			Config.drawBoidVision = !Config.drawBoidVision;
		else if(key == 'b')
			Config.drawBoidAction = !Config.drawBoidAction;
		else if(key == 'i')
			Config.drawBoidId = !Config.drawBoidId;
		else if(key == 'f')
			Config.drawBoidFuel = !Config.drawBoidFuel;
		else if(key == 'r')
			Config.drawRayCasting = !Config.drawRayCasting;
		else if(key == 'k')
			Config.drawKeyPoints = !Config.drawKeyPoints;
		else if(key == 's')
			Config.drawShelterPoints = !Config.drawShelterPoints;
		
		else if(key == PApplet.CODED) {
			if(keyCode == UP)
				arrowKeys[0] = true;
			else if(keyCode == DOWN)
				arrowKeys[1] = true;
			else if(keyCode == LEFT)
				arrowKeys[2] = true;
			else if(keyCode == RIGHT)
				arrowKeys[3] = true;
		}
		
	}
	
	public void keyReleased() {
		if(key == PApplet.CODED) {
			if(keyCode == UP)
				arrowKeys[0] = false;
			else if(keyCode == DOWN)
				arrowKeys[1] = false;
			else if(keyCode == LEFT)
				arrowKeys[2] = false;
			else if(keyCode == RIGHT)
				arrowKeys[3] = false;
		}
	}
	
	
	private void drawWinTeam(int teamID)
	{
		
		Config.die_music.stop();
		Config.win_music.play();;
		String winText = "Winner: Red Team!";
		RGB showColor=new RGB(255,0,0);
		if(teamID==1) {winText = "Winner: Blue Team!";showColor.b=255;showColor.r=0;}
		drawText(winText, Config.SCREEN_WIDTH/2-200, Config.SCREEN_HEIGHT/2-80, "Georgia", 50, showColor);	
	}
	private void victoryJudge()
	{
		int winTeam;
		if(boids.size()==0)
		{
			drawText("No Winner!", Config.SCREEN_WIDTH/2-150, Config.SCREEN_HEIGHT/2-80, "Georgia", 50, new RGB(0,0,0));
			return;
		}
		else
		{
			winTeam=boids.get(0).getTeam();
			if(boids.size()==1)
			{
				drawWinTeam(winTeam);		
			}
			else
			{
				for(Boid b:boids)
				{
					if(winTeam!=b.getTeam())
					{
						return;
					}
				}
				drawWinTeam(winTeam);					
			}
		}		
	}
	/*
	private void testBoidVision(Boid b) {
		if(frameCount % 60 == 1) {
			System.out.print(b.getId()+": ");
			Boid ve = b.getVisibleEnemy();
			Boid ae = b.getAudibleEnemy();
			Boid da = b.getDetectableAlly();
			System.out.print((ve==null?ve:ve.getId()) + ", ");
			System.out.print((ae==null?ae:ae.getId()) + ", ");
			System.out.println(da==null?da:da.getId());
		}
	}
	*/
}
