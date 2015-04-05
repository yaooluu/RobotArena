package environment;

import physics.Vec2D;

public class Buff extends Vec2D {

	//0: red buff, 1: blue buff
	private int type = -1;
	public int getType() {return type;}
	
	public Buff(float x, float y, int type) {
		super(x, y);
		this.type = type;
	}

}
