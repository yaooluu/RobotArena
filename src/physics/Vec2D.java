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

	public Vec2D(Vec2D v) {
		this.x = v.x;
		this.y = v.y;
	}

	public boolean equals(Object b) {
		Vec2D b1 = (Vec2D) b;
		return (this.x == b1.x) && (this.y == b1.y);
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
		if(l==0)return;
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

	public float dotCross(Vec2D b) {
		return this.x * b.x + this.y * b.y;
	}

	public void truncate(float maxLength) {
		if (this.getLength() < maxLength)
			return;
		this.normalize();
		Vec2D e = this.multiply(maxLength);
		this.x = e.x;
		this.y = e.y;
	}
	
	public void drag(float maxLength) {
		this.normalize();
		Vec2D e = this.multiply(maxLength);
		this.x = e.x;
		this.y = e.y;
	}
	
	public static float getAngleBetween(Vec2D v1, Vec2D v2) {
		
		float cosVal = v1.dotCross(v2) / (v1.getLength()*v2.getLength());
		if(cosVal > 1.0) cosVal = 1.0f;
		//System.out.println("Getting angle between:" + v1 + " and "+ v2 +", with cosVal="+cosVal);
		return (float) (Math.acos(cosVal) * 180.0 / Math.PI);
	}

	public static float vecToR(Vec2D vec) {
		if(vec.x == 0) {
			if(vec.y > 0) return 180;
			else return 0;
		}else {		
			
			float sharp = (float) (180.0 / Math.PI * Math.atan(Math.abs(vec.y/vec.x)));
			float angle = 0;
			if(vec.x>0 && vec.y>0) angle = 90 + sharp;
			else if(vec.x<0 && vec.y>0) angle = 270 - sharp;
			else if(vec.x<0 && vec.y<0) angle = 270 + sharp;
			else angle = 90 - sharp;
			
			return angle;
		}
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
