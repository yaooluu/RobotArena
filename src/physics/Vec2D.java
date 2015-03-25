package physics;

public class Vec2D {
	public float x;
	public float y;

	public Vec2D(float x, float y) {
		this.x = x;
		this.y = y;
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
		return (float)Math.sqrt((x * x + y * y));
	}

	public void normalize() {
		float l = this.getLength();
		this.x /= l;
		this.y /= l;
	}
	
	public Vec2D getUnitVec(){
		float l = this.getLength();
		return new Vec2D(x/l,y/l);
	}

	public Vec2D scale(float s) {
		return new Vec2D(s * x,  s * y);
	}

	public void truncate(float maxLength) {
		this.normalize();
		Vec2D e = this.scale(maxLength);
		this.x = e.x;
		this.y = e.y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public static void main(String[] args){
		Vec2D a = new Vec2D(200,300);
		Vec2D b = new Vec2D(1,1);
		System.out.println(a.getUnitVec());
	}
}
