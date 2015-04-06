package environment;

import physics.Vec2D;

public class Buff extends Vec2D {

	//0: red buff, 1: blue buff
	private int type = -1;
	public int getType() {return type;}
	
	private RGB rgb = null;
	
	public int countdown = 0;
	
	public Buff(float x, float y, int type) {
		super(x, y);
		this.type = type;
	}

	public RGB getRGB() {
		if(rgb == null) {
			if(type == 0) rgb = new RGB(255,0,0);
			else rgb = new RGB(0,0,255);
		}
		return rgb;
	}

}
