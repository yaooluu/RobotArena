package physics;

import main.Config;
import environment.Boid;

/**
 * @author Jianfeng Chen
 * @contact jchen37@ncsu.edu
 * @version 1.0
 */

public class Collision {

	/**
	 * The velocity will be reset immediately as if collision happens Perfect
	 * Elastic collision Equation from
	 * http://www4.uwsp.edu/physastr/kmenning/Phys203/Lect17.html
	 * Velocity can exceed the maximum speed.
	 * @param b1
	 *            the first boid
	 * @param b2
	 *            the second boid
	 */
	public static void perform(Boid b1, Boid b2) {
		float m1 = b1.getMass();
		float m2 = b2.getMass();
		Velocity v1 = b1.v;
		Velocity v2 = b2.v;
		Vec2D v11 =  v1.multiply((m1 - m2) / (m1 + m2)).plus(
				v2.multiply(2 * m2 / (m1 + m2)));
		Vec2D v22 =  v1.multiply(2 * m1 / (m1 + m2)).plus(
				v2.multiply((m2 - m1) / (m1 + m2)));
		b1.v = new Velocity(v11);
		b2.v = new Velocity(v22);
	}
	
	public static void main(String[] args){
		Boid b1 = new Boid(20,30,2,2,Config.BOID_TYPE.scout);
		Boid b2 = new Boid(30,20,2,2,Config.BOID_TYPE.tank);
		b1.v = new Velocity(-3,0);
		b2.v = new Velocity(7,0);
		Collision.perform(b1, b2);
		System.out.println(b1.v);
		System.out.println(b2.v);
		
	}
}
