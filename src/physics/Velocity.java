package physics;

/**
 * @author ChenJF
 * @version 1.0
 * contact: jchen37@ncsu.edu
 */
public class Velocity extends Vec2D {
	
	public float vx;
	public float vy;

	public Velocity(float x, float y) {
		super(x, y);
		vx = x;
		vy = y;
	}

	public Velocity(int x, int y) {
		super(x, y);
		vx = x;
		vy = y;
	}
	
}