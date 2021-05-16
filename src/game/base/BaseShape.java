package game.base;

import java.awt.Shape;

public class BaseShape {
	
	private Shape shape;
	private boolean alive;
	private double x;
	private double y;
	private double velX;
	private double velY;
	private double moveAngle;
	private double faceAngle;
	
	
	public BaseShape() {
		setShape(null);
		setAlive(false);
		setX(0.0);
		setY(0.0);
		setVelX(0.0);
		setVelY(0.0);
		setMoveAngle(0.0);
		setFaceAngle(0.0);
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void incX(double x) {
		this.x += x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void incY(double y) {
		this.y += y;
	}
	
	public double getVelX() {
		return velX;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void incVelX(double velX) {
		this.velX += velX;
	}
	
	public double getVelY() {
		return velY;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public void incVelY(double velY) {
		this.velY += velY;
	}
	
	public double getMoveAngle() {
		return moveAngle;
	}
	
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}
	
	public void incMoveAngle(double moveAngle) {
		this.moveAngle += moveAngle;
	}
	
	public double getFaceAngle() {
		return faceAngle;
	}
	
	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}
	
	public void incFaceAngle(double faceAngle) {
		this.faceAngle += faceAngle;
	}

}
