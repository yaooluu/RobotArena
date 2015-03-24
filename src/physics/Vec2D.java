package physics;

public class Vec2D {
	public float x;
	public float z;

	public Vec2D(float x, float z) {
		this.x = x;
		this.z = z;
	}

	public void plusEqual(Vec2D increment) {
		this.x += increment.x;
		this.z += increment.z;
	}

	public Vec2D plus(Vec2D b) {
		return new Vec2D(this.x + b.x, this.z + b.z);
	}

	public void minusEqual(Vec2D substraction) {
		this.x -= substraction.x;
		this.z -= substraction.z;
	}

	public Vec2D minus(Vec2D b) {
		return new Vec2D(this.x - b.x, this.z - b.z);
	}

	public float getLength() {
		return (float)Math.sqrt((x * x + z * z));
	}

	public void normalize() {
		float l = this.getLength();
		this.x /= l;
		this.z /= l;
	}
	
	public Vec2D getUnitVec(){
		float l = this.getLength();
		return new Vec2D(x/l,z/l);
	}

	public Vec2D scale(float s) {
		return new Vec2D(s * x,  s * z);
	}

	public void truncate(float maxLength) {
		this.normalize();
		Vec2D e = this.scale(maxLength);
		this.x = e.x;
		this.z = e.z;
	}

	public String toString() {
		return "(" + x + "," + z + ")";
	}

	public static void main(String[] args){
		Vec2D a = new Vec2D(200,300);
		Vec2D b = new Vec2D(1,1);
		System.out.println(a.getUnitVec());
	}
}
