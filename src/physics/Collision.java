package physics;

import java.util.*;

import main.Config;
import environment.Boid;
import environment.Wall;
import environment.World;

/**
 * @author Jianfeng Chen, Yao Lu
 * @contact jchen37@ncsu.edu, ylu31@ncsu.edu
 * @version 1.0, 1.1, 1.2, 1.3
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
		float threshold = 2;
		
		for(Boid b : boids) {
			
			Wall nearestWall = null;
			float minD = Integer.MAX_VALUE;
			for(Wall w : World.getWalls()) {	
				float dist = b.pos.minus(new Vec2D(w.x, w.y)).getLength();
				
				if(dist < minD && dist < b.getSize()/2.0 + threshold) {							
					minD = dist;
					nearestWall = w;
				}
			}			
			
			if(nearestWall != null) {			
				float factor = 1.0f;				
				b.a.x = 0;
				b.a.y = 0;
				b.v.truncate(1.0f);
				b.v.plusEqual(nearestWall.collisionVec.multiply(factor));
				b.pos.plusEqual(b.v);
				
				/*
				Vec2D vec = new Vec2D(0,0);			
				float x = Math.abs(nearestWall.collisionVec.x);
				float y = Math.abs(nearestWall.collisionVec.y);
				if(x < 0.1 && y > 0.9) {
					vec = nearestWall.collisionVec.multiply(Math.abs(b.v.y) * factor);
				}
				else if(y < 0.1 && x > 0.9) {
					vec = nearestWall.collisionVec.multiply(Math.abs(b.v.x) * factor);
				}
				else {
					//nearestWall.collisionVec.truncate(.1f);
					//vec = nearestWall.collisionVec.multiply(b.v.getLength() * factor);
					//vec = b.pos.multiply(1);
					//b.v = b.v.multiply(1);
					//vec = nearestWall.collisionVec;
					b.v.truncate(1.1f);				
					b.a = new Vec2D(0, 0);
					b.v.plusEqual(nearestWall.collisionVec);
					b.pos.plusEqual(b.v);
					return;
				}
				*/
				
				/*
				b.v.plusEqual(vec);
				b.v.truncate(Config.MAX_SPEED[b.getType()]);
				b.v = b.v.multiply(0.1f);
				*/
						
				//nearestWall.collisionVec.drag(b.getSize()/2 + 4);
				//b.pos = nearestWall.plus(nearestWall.collisionVec.multiply(1.01f));
				
				if(b.isUlt){b.isHit=true;b.isUlt=false;}							
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
		
		//behavior finish (attack)
		if(b1.curBehavior.equals("attack")) b1.curBehavior = "";
		if(b2.curBehavior.equals("attack")) b2.curBehavior = "";
		
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
		
		//seperate them immediately
		b1.pos = b2.pos.plus(b1.pos.minus(b2.pos).multiply(1.1f));
		
		b1.pos.plusEqual(b1.v.multiply((float) (1.0/Config.FRAME_RATE)));
		b2.pos.plusEqual(b2.v.multiply((float) (1.0/Config.FRAME_RATE)));
		if(b1.isUlt){b1.isHit=true;b1.isUlt=false;}
		if(b2.isUlt){b2.isHit=true;b2.isUlt=false;}
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