package com.halycon.entity;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.halycon.Constants;
import com.halycon.EntityState;

public class Alien extends Entity {

	private static final float ALIEN_SIZE = Constants.UNIT_SIZE * 10;
	private static final float ALIEN_POSITION_OFFSET = Constants.UNIT_SIZE * 5;
	private static final float ALIEN_INITIAL_SPEED = 0.15f;
	
	Image image;
	Vector3f aim; 
	float speed = ALIEN_INITIAL_SPEED;
	
	public void init() throws SlickException {
		image = new Image("res/elements/saucer.png");
	}
	
	public void getHit() {
		state = EntityState.DEAD;
	}
	
	public void render(Graphics g) {
		image.draw(getPosition().x - ALIEN_POSITION_OFFSET, getPosition().y - ALIEN_POSITION_OFFSET, ALIEN_SIZE, ALIEN_SIZE);
	}
	
	public void shootBullet(Bullet bullet) {
		bullet.setPosition(getPosition());
		bullet.setMovement(new Vector3f(aim.x * speed, aim.y * speed, 0));
	}
	
	public void update(int delta, Vector3f ship) {
		super.move();
		aim = new Vector3f(ship.x - getPosition().x, ship.y - getPosition().y, 0);
		aim.normalise(aim);
		aim.x = aim.x * delta;
		aim.y = aim.y * delta;
	}
}
