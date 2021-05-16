package game.component;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.base.BaseShape;

public class Asteroid extends BaseShape {
	
	// Define the asteroid polygon shape
	private int[] astx = { -20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16 };
	private int[] asty = { 20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5 };
	
	private double rotVel;
	
	public Asteroid() {
		setShape(new Polygon(astx, asty, astx.length));
		setAlive(true);
		setRotationVelocity(0.0);
	}
	
	public double getRotationVelocity() {
		return rotVel;
	}
	
	public void setRotationVelocity(double rotVel) {
		this.rotVel = rotVel;
	}
	
	// Bounding rectangle
	public Rectangle getBounds() {
		return (new Rectangle((int)getX() - 20, (int)getY() - 20, 40, 40));
	}

}
