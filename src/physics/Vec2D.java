package physics;

/**
 * @author Jianfeng Chen
 * @contact jchen37@ncsu.edu
 * @version 1.0
 */
public class Vec2D {
	public float x;
	public float y;

	public Vec2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2D(int x, int y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public void plusEqual(Vec2D increment) {
		this.x += increment.x;
		this.y += increment.y;
	}

	public Vec2D plus(Vec2D b) {
		return new Vec2D(this.x + b.x, this.y + b.y);
	}

	public void minusEqual(Vec2D substraction) {
		this.x -= substraction.x;
		this.y -= substraction.y;
	}

	public Vec2D minus(Vec2D b) {
		return new Vec2D(this.x - b.x, this.y - b.y);
	}

	public float getLength() {
		return (float) Math.sqrt((x * x + y * y));
	}

	public void normalize() {
		float l = this.getLength();
		this.x /= l;
		this.y /= l;
	}

	public Vec2D getUnitVec() {
		float l = this.getLength();
		return new Vec2D(x / l, y / l);
	}

	public Vec2D multiply(float s) {
		return new Vec2D(s * x, s * y);
	}

	public void truncate(float maxLength) {
		if (this.getLength() < maxLength)
			return;
		this.normalize();
		Vec2D e = this.multiply(maxLength);
		this.x = e.x;
		this.y = e.y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
}




