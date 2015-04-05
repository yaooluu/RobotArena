package environment;

import physics.Vec2D;

public class Wall extends Vec2D {
	
	public Vec2D collisionVec = new Vec2D(0,0);
	
	public Wall(float x, float y, Vec2D vec) {
		super(x, y);
		this.collisionVec = vec;
	}

}
