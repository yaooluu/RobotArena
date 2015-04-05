package environment;

import physics.Vec2D;

public class Border extends Vec2D {
	
	public Vec2D borderVec = new Vec2D(0,0);
	
	public Border(float x, float y, Vec2D vec) {
		super(x, y);
		this.borderVec = vec;
	}

}
