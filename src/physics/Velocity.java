package physics;

/**
 * @author Jianfeng Chen
 * @contact jchen37@ncsu.edu
 * @version 1.0
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
	
	public Velocity(Vec2D a){
		super(a.x,a.y);
		vx = a.x;
		vy = a.y;
	}
}