package com.halycon.entity;

import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.halycon.EntityState;

public class Heart extends Entity {

	private static Audio pickUpSound;
	private static final int RADIUS = 10;
	private Trail trail;
	private static final int TRAIL_LENGTH = 20;
	private static final int TRAIL_SNAP = 10;
	
	public Heart() {
		setOffset(new Vector3f(RADIUS * 0.5f, RADIUS* 0.5f,0));
		setSize(new Vector3f(RADIUS,RADIUS,0));
		setRadius(RADIUS);
		trail = new Trail();
		trail.setOffset(new Vector3f(-(RADIUS / 2), -(RADIUS / 2), 0), new Vector3f(RADIUS, RADIUS, 0));
		
		try {
			pickUpSound = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/sounds/heart.ogg"));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws SlickException {
		try {
			setImage(new Image("res/elements/heart0.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		trail.init(new Color(1.0f, 0.1f, 0.1f, 0.5f), TRAIL_LENGTH, TRAIL_SNAP);
	}
	
	public void render(Graphics g) {
		
		trail.render(g, getImage());
		super.render(g);
	}
	
	public void destroy() {
		state = EntityState.DEAD;
		pickUpSound.playAsSoundEffect(1.0f, 0.6f, false);
	}
	
	@Override
	public void update(int delta) {
		trail.update(delta, getPosition(), getRotation());
		super.update(delta);
	}
}
