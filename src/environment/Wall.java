package environment;

import physics.Vec2D;

public class Wall extends Pair {
	
	public Vec2D vec = new Vec2D(0,0);
	
	public Wall(int x, int y, Vec2D vec) {
		super(x, y);
		this.vec = vec;
	}

}
