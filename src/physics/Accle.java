package physics;

/**
 * @author ChenJF
 * @version 1.0
 * contact: jchen37@ncsu.edu
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