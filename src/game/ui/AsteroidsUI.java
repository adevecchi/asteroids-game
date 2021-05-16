package game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.Random;

import javax.swing.JFrame;

import game.component.Asteroid;
import game.component.Bullet;
import game.component.Ship;

public class AsteroidsUI extends JFrame implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 640;
	
	private final int HEIGHT = 480;
	
	private final int ASTEROIDS = 20;
	
	private final int BULLETS = 10;

	private Thread gameLoop;
	
	private BufferedImage backBuffer;
	
	private Graphics2D g2d;
	
	private Asteroid[] asteroids = new Asteroid[ASTEROIDS];
	
	private Bullet[] bullets = new Bullet[BULLETS];
	
	private int currentBullet = 0;
	
	private Ship ship = new Ship();
	
	private AffineTransform identity = new AffineTransform();
	
	private Random rand = new Random();
	
	public AsteroidsUI() {
		backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		ship.setX(WIDTH / 2);
		ship.setY(HEIGHT / 2);
		
		for (int i = 0; i < BULLETS; i++) {
			bullets[i] = new Bullet();
		}
		
		for (int i = 0; i < ASTEROIDS; i++) {
			asteroids[i] = new Asteroid();
			asteroids[i].setRotationVelocity(rand.nextInt(3) + 1);
			asteroids[i].setX((double)rand.nextInt(600) + 20);
			asteroids[i].setY((double)rand.nextInt(440) + 20);
			asteroids[i].setMoveAngle(rand.nextInt(360));
			
			double angle = asteroids[i].getMoveAngle() - 90;
			
			asteroids[i].setVelX(calcAngleMoveX(angle));
			asteroids[i].setVelY(calcAngleMoveY(angle));
		}
		
		this.addKeyListener(this);
		
		this.setTitle("Asteroids Clone");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void paint(Graphics g) {
		draw(g);
		g.drawImage(backBuffer, 0, 0, this);
	}
	
	private void draw(Graphics g) {
		g2d.setTransform(identity);
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		
		drawShip();
		drawBullets();
		drawAsteroids();
	}
	
	private void drawShip() {
		g2d.setTransform(identity);
		g2d.translate(ship.getX(), ship.getY());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.setColor(Color.ORANGE);
		g2d.fill(ship.getShape());
	}
	
	private void drawBullets() {
		for (int i = 0; i < BULLETS; i++) {
			if (bullets[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.translate(bullets[i].getX(), bullets[i].getY());
				g2d.setColor(Color.MAGENTA);
				g2d.draw(bullets[i].getShape());
			}
		}
	}
	
	private void drawAsteroids() {
		for (int i = 0; i < ASTEROIDS; i++) {
			if (asteroids[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.translate(asteroids[i].getX(), asteroids[i].getY());
				g2d.rotate(Math.toRadians(asteroids[i].getMoveAngle()));
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(asteroids[i].getShape());
			}
		}
	}
	
	public void start() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		
		while (t == gameLoop) {
			try {
				gameUpdate();
				
				Thread.sleep(20);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void stop() {
		gameLoop = null;
	}
	
	private void gameUpdate() {
		updateShip();
		updateBullets();
		updateAsteroids();
		checkCollisions();
	}
	
	private void updateShip() {
		ship.incX(ship.getVelX());
		
		if (ship.getX() < -10)
			ship.setX(getSize().width + 10);
		else if (ship.getX() > getSize().width + 10)
			ship.setX(-10);
		
		ship.incY(ship.getVelY());
		
		if (ship.getY() < -10)
			ship.setY(getSize().height + 10);
		else if (ship.getY() > getSize().height + 10)
			ship.setY(-10);
	}
	
	private void updateBullets() {
		for (int i = 0; i < BULLETS; i++) {
			if (bullets[i].isAlive()) {
				bullets[i].incX(bullets[i].getVelX());
				
				if (bullets[i].getX() < 0 || bullets[i].getX() > getSize().width)
					bullets[i].setAlive(false);
				
				bullets[i].incY(bullets[i].getVelY());
				
				if (bullets[i].getY() < 0 || bullets[i].getY() > getSize().height)
					bullets[i].setAlive(false);
			}
		}
	}
	
	private void updateAsteroids() {
		for (int i = 0; i < ASTEROIDS; i++) {
			if (asteroids[i].isAlive()) {
				asteroids[i].incX(asteroids[i].getVelX());
				
				if (asteroids[i].getX() < -20)
					asteroids[i].setX(getSize().width + 20);
				else if (asteroids[i].getX() > getSize().width + 20)
					asteroids[i].setX(-20);
				
				asteroids[i].incY(asteroids[i].getVelY());
				
				if (asteroids[i].getY() < -20)
					asteroids[i].setY(getSize().height + 20);
				else if (asteroids[i].getY() > getSize().height + 20)
					asteroids[i].setY(-20);
				
				asteroids[i].incMoveAngle(asteroids[i].getRotationVelocity());
				
				if (asteroids[i].getMoveAngle() < 0)
					asteroids[i].setMoveAngle(360 - asteroids[i].getRotationVelocity());
				else if (asteroids[i].getMoveAngle() > 360)
					asteroids[i].setMoveAngle(asteroids[i].getRotationVelocity());
			}
		}
	}
	
	private void checkCollisions() {
		for (int i = 0; i < ASTEROIDS; i++) {
			if (asteroids[i].isAlive()) {
				for (int j = 0; j < BULLETS; j++) {
					if (bullets[j].isAlive()) {
						if (asteroids[i].getBounds().contains(bullets[j].getX(), bullets[j].getY())) {
							bullets[j].setAlive(false);
							asteroids[i].setAlive(false);
						}
					}
				}
				
				if (asteroids[i].getBounds().intersects(ship.getBounds())) {
					asteroids[i].setAlive(false);
					ship.setX(320);
					ship.setY(240);
					ship.setFaceAngle(0);
					ship.setVelX(0);
					ship.setVelY(0);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent k) {}
	
	public void keyTyped(KeyEvent k) {}
	
	public void keyPressed(KeyEvent k) {
		int keyCode = k.getKeyCode();
		
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			ship.incFaceAngle(-5);
			if (ship.getFaceAngle() < 0)
				ship.setFaceAngle(360 - 5);
			break;
		case KeyEvent.VK_RIGHT:
			ship.incFaceAngle(5);
			if (ship.getFaceAngle() > 360)
				ship.setFaceAngle(5);
			break;
		case KeyEvent.VK_UP:
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(calcAngleMoveX(ship.getMoveAngle()) * 0.1);
			ship.incVelY(calcAngleMoveY(ship.getMoveAngle()) * 0.1);
			break;
		case KeyEvent.VK_DOWN:
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(-(calcAngleMoveX(ship.getMoveAngle()) * 0.1));
			ship.incVelY(-(calcAngleMoveY(ship.getMoveAngle()) * 0.1));
			break;
		case KeyEvent.VK_SPACE:
			currentBullet++;
			if (currentBullet > BULLETS - 1)
				currentBullet = 0;
			bullets[currentBullet].setAlive(true);
			
			bullets[currentBullet].setX(ship.getX());
			bullets[currentBullet].setY(ship.getY());
			bullets[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);
			
			double angle = bullets[currentBullet].getMoveAngle();
			double svx = ship.getVelX();
			double svy = ship.getVelY();
			
			bullets[currentBullet].setVelX(svx + calcAngleMoveX(angle) * 2);
			bullets[currentBullet].setVelY(svy + calcAngleMoveY(angle) * 2);
			break;
		}
	}
	
	private double calcAngleMoveX(double angle) {
		return (double)(Math.cos(angle * Math.PI / 180));
	}
	
	private double calcAngleMoveY(double angle) {
		return (double)(Math.sin(angle * Math.PI / 180));
	}
	
}
