package com.halycon.entity;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.halycon.EntityState;
import com.halycon.screen.GamePlayState;

public class Bullet extends Entity {
	
	private static final int INITIAL_TIME_TO_LIVE = 800;
	
	private Image image;
	
	private Trail trail;
	
	private int timeToLive = INITIAL_TIME_TO_LIVE;
	
	public Bullet() {
		super();
		trail = new Trail();
		trail.setOffset(new Vector3f(-2, -2, 0), new Vector3f(4, 4, 0));
		setOffset(new Vector3f(2, 2, 0));
		setSize(new Vector3f(4, 4, 0));
	}
	
	public void init() throws SlickException {
		image = new Image("res/elements/bullet5.png");
		trail.init(new Color(1.0f, 0.2f, 0.6f, 0.7f), 20, 1);
		setImage(image);
	}
	
	public void getHit() {
		state = EntityState.DEAD;
	}
	
	public void shootBullet(Sprite sprite) {
		setPosition(sprite.getPosition());
		setRotation(sprite.getRotation());
		movement = getRotationAsVector(sprite.getRotation());
	}
	
	public void render(Graphics g) {
		trail.render(g, image);
		super.render(g);
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		timeToLive -= delta;
		if (timeToLive < 0) {
			state = EntityState.DEAD;
		}
		trail.update(delta, getPosition(), getRotation());
	}
}
