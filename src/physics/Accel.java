package physics;

/**
 * @author Jianfeng Chen
 * @contact jchen37@ncsu.edu
 * @version 1.0
 */
public class Accel extends Vec2D {

	float ax;
	float ay;

	public Accel(float x, float y) {
		super(x, y);
		ax = x;
		ay = y;
	}

	public Accel(int x, int y) {
		super(x, y);
		ax = x;
		ay = y;
	}

	public Accel(Vec2D a) {
		// TODO Auto-generated constructor stub
		super(a.x,a.y);
		ax = a.x;
		ay = a.y;
	}
}