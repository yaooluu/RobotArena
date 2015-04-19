package environment;

import physics.Vec2D;

public class Wall extends Vec2D {
	
	public Vec2D collisionVec = null;
	
	public Wall(float x, float y, Vec2D vec) {
		super(x, y);
		this.collisionVec  = new Vec2D(vec);
	}

}
