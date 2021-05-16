package game.component;

import java.awt.Rectangle;

import game.base.BaseShape;

public class Bullet extends BaseShape {
	
	public Bullet() {
		setShape(new Rectangle(0, 0, 1, 1));
		setAlive(false);
	}
	
	// Bounding rectangle
	public Rectangle getBounds() {
		return (new Rectangle((int)getX(), (int)getY(), 1, 1));
	}

}
