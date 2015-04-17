package decisionmaking;

import main.Config;
import main.Main;
import environment.Boid;

public class DecisionTree {

	public static void PerformDecision(Boid b){
		
		//if has current behavior, maintain it
		String decision = b.curBehavior;
		
		//if don't have current behavior, generate one
		if(decision.equals("") || 
				(b.curEnemy != null && !Main.getBoids().contains(b.curEnemy))) {
			decision = DecisionTree.makeDecision(b,1);
			
			b.curEnemy = b.getVisibleEnemy();
			if(b.curEnemy == null)
				b.curEnemy = b.getAudibleEnemy();
		
			System.out.println(b + ": " + decision);
		}
		
		//debug
//		/System.out.print(".");
		//if(Config.canvas.frameCount % 60 == 1) 
		//System.out.println(b + " desicion: " + decision);
		
		switch (decision) {
		case "attack":
			if(b.curEnemy != null)
				b.attack(b.curEnemy);
			break;

		case "evade":
			if(b.curEnemy != null)
				b.evade(b.curEnemy);
			break;

		case "redbuff":
			b.getBuff("red");
			break;
			
		case "bluebuff":
			b.getBuff("blue");
			break;
			
		case "trace":
			if(b.curEnemy != null)
				b.trace(b.curEnemy);
			break;
			
		case "wander":
			b.wander();
			break;
			
		case "lure":
			//TODO
			break;
			
		case "ultimate":
			//TODO
			break;

		case "guard":
			//TODO
			break;
			
		case "hide":
			b.hide();
			break;
		}
	}
	
	private static String makeDecision(Boid b, int parent) {
		int index = parent;
		do {
			parent = index;
			index = traverse(parent, b);
		} while (index > 0);
		return getLeafAction(index);
	}

	private static String getLeafAction(int index) {
		String actiontable[] = { "evade", "attack", "redbuff", "bluebuff",
				"trace", "wander", "lure", "ultimate", "gurad", "hide" };
		return actiontable[(-index) - 1];
	}

	private static int traverse(int parent, Boid b) {
		Boid enemy = b.getVisibleEnemy();
		if (enemy == null)
			enemy = b.getAudibleEnemy();
		switch (parent) {
		case 1:
			return b.getVisibleEnemy() != null ? 2 : 3;
		case 2:
			float dis = enemy.pos.minus(b.pos).getLength();
			return dis < 10 ? 4 : (dis < 100 ? 5 : 6);
		case 3:
			return b.getAudibleEnemy() != null ? 7 : 8;
		case 4:
			return enemy.getType() > b.getType() ? -1 : -2;
		case 5:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -3 : 9;
		case 6:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -3 : 10;
		case 7:
			return b.pos.minus(enemy.pos).dotCross(b.v) > 0 ? 11 : 12;
		case 8:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -3 : 13;
		case 9:
			return b.status == 1 ? 14 : 15;
		case 10:
			return b.status == 1 ? 16 : 17;
		case 11:
			return b.status == 1 ? 18 : -1;
		case 12:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -1 : 19;
		case 13:
			return Math.random() < 0.01 ? -8 : 20;
		case 14:
			return enemy.getType() > b.getType() ? 21 : 22;
		case 15:
			return Math.random() < 0.5 ? -6 : -1;
		case 16:
			return enemy.getType() > b.getType() ? 23 : 24;
		case 17:
			return Math.random() < 0.5 ? -6 : -1;
		case 18:
			return enemy.getType() > b.getType() ? -1 : 25;
		case 19:
			return b.status == 1 ? 26 : 27;
		case 20:
			return b.getDetectableAlly() != null ? 28 : 29;
		case 21:
			return enemy.getType() == Config.BOID_TYPE.commander.value() ? -2
					: 30;
		case 22:
			return Math.random() < 0.5 ? -5 : -2;
		case 23:
			return enemy.getType() == Config.BOID_TYPE.commander.value() ? 31
					: 32;
		case 24:
			return Math.random() < 0.5 ? -7 : -2;
		case 25:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -1 : -2;
		case 26:
			return enemy.getType() > b.getType() ? 33 : 34;
		case 27:
			return Math.random() < 0.5 ? -6 : -1;
		case 28:
			return Math.random() < 0.5 ? -6 : 35;
		case 29:
			return b.getBuffRange() < 100 ? -4 : 36;
		case 30:
			return Math.random() < 0.5 ? -5 : -1;
		case 31:
			return Math.random() < 0.5 ? -2 : -7;
		case 32:
			return Math.random() < 0.8 ? -6 : (Math.random() < 0.5 ? -7 : -1);
		case 33:
			return enemy.getType() == Config.BOID_TYPE.commander.value() ? -2
					: 37;
		case 34:
			return Math.random() < 0.5 ? -5 : -2;
		case 35:
			return b.status == 1 ? 38 : -6;
		case 36:
			double t = Math.random();
			if (t < 0.1)
				return -3;
			if (t < 0.8)
				return -6;
			else
				return -10;
		case 37:
			return Math.random() < 0.5 ? -5 : -1;
		case 38:
			return b.getFuel() < Config.MAX_FUEL * 0.15 ? -9 : -6;
		}
		return 0;
	}

}