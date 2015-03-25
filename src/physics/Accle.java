package physics;

/**
 * @author Jianfeng Chen
 * @contact jchen37@ncsu.edu
 * @version 1.0
 */
public class Accle extends Vec2D {

	float ax;
	float ay;

	public Accle(float x, float y) {
		super(x, y);
		ax = x;
		ay = y;
	}

	public Accle(int x, int y) {
		super(x, y);
		ax = x;
		ay = y;
	}
}