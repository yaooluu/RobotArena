package physics;

import java.util.*;

import main.Config;
import environment.Boid;
import environment.Wall;
import environment.World;

/**
 * @author Jianfeng Chen, Yao Lu
 * @contact jchen37@ncsu.edu, ylu31@ncsu.edu
 * @version 1.0, 1.1, 1.2
 */
public class Collision {
	
	public static void allCollision(List<Boid> boids) {
		
		worldCollision(boids);
		boidCollision(boids);
	}
	
	//physical collision between boids
	private static void boidCollision(List<Boid> boids) {

		for(int i=0;i<boids.size();i++) {
			for(int j=0;j<boids.size();j++) {
				if(i == j) continue;
				Collision.perform(boids.get(i), boids.get(j));
			}
		}
	}
	
	//physical collision between boid and walls
	private static void worldCollision(List<Boid> boids) {
		float factor = 1.1f;
		for(Boid b : boids) {
			
			Wall minW = null;
			float minD = Integer.MAX_VALUE;
			for(Wall w : World.getWalls()) {	
				float dist = b.pos.minus(new Vec2D(w.x, w.y)).getLength();
				if(dist < minD && dist < b.getSize()/2.0 + 2) {							
					minD = dist;
					minW = w;
				}
			}			
			
			if(minW != null) {
				b.isHittingWall = true;
				Vec2D vec = new Vec2D(0,0);			
				float x = Math.abs(minW.collisionVec.x);
				float y = Math.abs(minW.collisionVec.y);
				if(x < 0.1 && y > 0.9) {
					vec = minW.collisionVec.multiply(Math.abs(b.v.y) * factor);
				}
				else if(y < 0.1 && x > 0.9) {
					vec = minW.collisionVec.multiply(Math.abs(b.v.x) * factor);
				}
				else {
					vec = minW.collisionVec.multiply(b.v.getLength() * factor);
				}
	
				b.v.plusEqual(vec);
				b.v.truncate(Config.MAX_SPEED[b.getType()]);
				b.a = new Vec2D(0, 0);
				//b.a.plusEqual(vec);
				
			}
		}
	}
	
	/**
	 * The velocity will be reset immediately as if collision happens Perfect
	 * Elastic collision Equation from
	 * http://www4.uwsp.edu/physastr/kmenning/Phys203/Lect17.html Velocity can
	 * exceed the maximum speed.
	 * 
	 * @param b1 the first boid
	 * @param b2 the second boid
	 */
	private static void perform(Boid b1, Boid b2) {
		// check boids' distance, see if collision happens
		int distance = (int) b1.pos.minus(b2.pos).getLength();
		if (distance > (b1.getSize() + b2.getSize()) / 2)
			return;
		
		float m1 = b1.getMass();
		float m2 = b2.getMass();
		Vec2D v1 = b1.v;
		Vec2D v2 = b2.v;
		Vec2D v11 = v1.multiply((m1 - m2) / (m1 + m2)).plus(
				v2.multiply(2 * m2 / (m1 + m2)));
		Vec2D v22 = v1.multiply(2 * m1 / (m1 + m2)).plus(
				v2.multiply((m2 - m1) / (m1 + m2)));
		b1.v = new Vec2D(v11);
		b2.v = new Vec2D(v22);
		
		b1.pos.plusEqual(b1.v.multiply((float) (1.0/Config.FRAME_RATE)));
		b2.pos.plusEqual(b2.v.multiply((float) (1.0/Config.FRAME_RATE)));
		
		//System.out.println("calculated: "+b1.v.getLength() +","+b2.v.getLength());
		//System.out.println("calculated: "+b1.v +", "+b2.v+"\n");
	}

	/*
	public static void main(String[] args) {
		Boid b1 = new Boid(20, 30, 2, 2, Config.BOID_TYPE.scout, 1);
		Boid b2 = new Boid(30, 20, 2, 2, Config.BOID_TYPE.scout, 2);
		b1.v = new Vec2D(10, 10);
		b2.v = new Vec2D(-10, 20);
		Collision.perform(b1, b2);
		System.out.println(b1.v);
		System.out.println(b2.v);
	}*/
}