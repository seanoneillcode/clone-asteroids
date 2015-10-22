package com.halycon.entity;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.halycon.Constants;

public class Sprite implements Renderable, Collidable {
	Vector3f position, movement, moveBy, offset, size;

	double rotation = 0.0f;
	double rotateBy = 0.0f;
	float friction = 0.001f;
	float speed = 0.01f;
	
	private static final float ROTATION_STEP = 0.3f;
	private static final float MAX_MOVEMENT = 1.0f;
	
	private boolean isGoingForward = false;
	
	private Image image;
	
	float radius = Constants.UNIT_SIZE;
	
	private boolean canCollide = true;
	
	public Sprite() {
		this.position = new Vector3f(0,0,0);
		this.movement = new Vector3f(0,0,0);
		this.rotation = 0.0d;
		this.moveBy = new Vector3f(0,0,0);
		this.size = new Vector3f(0,0,0);
		this.offset = new Vector3f(0,0,0);
	}
	
	public void init() throws SlickException {
		
	}
	
	public void render(Graphics g) {
		g.pushTransform();
		g.rotate(getPosition().x, getPosition().y, (float) rotation);
		image.draw(position.x - offset.x, position.y - offset.y, size.x, size.y);
		g.popTransform();
	}
	
	public void setOffset(Vector3f offset) {
		this.offset = new Vector3f(offset);
	}
	
	public void setSize(Vector3f size) {
		this.size = new Vector3f(size);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setRadius(float newRadius) {
		radius = newRadius;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public boolean isGoingForward() {
		return isGoingForward;
	}
	
	public void update(int delta) {
		rotation += rotateBy * delta;
		movement.x = movement.x + moveBy.x;
		movement.y = movement.y + moveBy.y;
		if (movement.x >= friction) {
			movement.x -= friction;			
		}
		if (movement.x <= friction) {
			movement.x += friction;
		}
		if (Math.abs(movement.x) - friction < 0) {
			movement.x = 0;
		}
		if (movement.y >= friction) {
			movement.y -= friction;			
		}
		if (movement.y <= friction) {
			movement.y += friction;
		}
		if (Math.abs(movement.y) - friction < 0) {
			movement.y = 0;
		}
		if (movement.x > MAX_MOVEMENT) {
			movement.x = MAX_MOVEMENT;
		}
		if (movement.x < -MAX_MOVEMENT) {
			movement.x = -MAX_MOVEMENT;
		}
		if (movement.y > MAX_MOVEMENT) {
			movement.y = MAX_MOVEMENT;
		}
		if (movement.y < -MAX_MOVEMENT) {
			movement.y = -MAX_MOVEMENT;
		}

		this.move();
		moveBy.x = 0;
		moveBy.y = 0;
		rotateBy = 0;
		isGoingForward = false;
	}
	
	public void goForward() {
		moveBy = getRotationAsVector(rotation);
		moveBy.x = moveBy.x * speed;
		moveBy.y = moveBy.y * speed;
		isGoingForward = true;
	}
	
	public void rotateLeft() {
		rotateBy -= ROTATION_STEP;
	}
	
	public void rotateRight() {
		rotateBy += ROTATION_STEP;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setMovement(Vector3f movement) {
		this.movement = movement;
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public Vector3f getMovement() {
		return new Vector3f(movement);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void move() {
		Vector3f.add(position, movement, position);
	}
	
	public Vector3f getRotationAsVector(double rotation) {
		double rot = -Math.toRadians(rotation + 45.0d);
		
		double x = (Math.cos(rot) + Math.sin(rot));
		double y = (Math.cos(rot) - Math.sin(rot));
		
		return new Vector3f((float)x, (float)y, 0);
	}
	
	public boolean canCollide() {
		return canCollide;
	}
	
	public boolean isColliding(Collidable collidable) {
		boolean returnValue = false;
		Vector3f p1 = getPosition();
		Vector3f p2 = collidable.getPosition();
		float distance = getRadius() + collidable.getRadius();
		if ((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y) < (distance * 3.14)) {
			returnValue = true;
		}
		return returnValue;
	}
}
