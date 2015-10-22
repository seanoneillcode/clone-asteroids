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

import com.halycon.AsteroidType;
import com.halycon.EntityState;


public class Asteroid extends Entity {
	
	AsteroidType type;

	private static Audio destroySound;
	private static final int TRAIL_LENGTH = 10;
	private static final int TRAIL_SNAP = 6;
	
	private Trail trail;
	
	public Asteroid(AsteroidType type) {
		super();
		this.type = type;
		trail = new Trail();
		trail.setOffset(new Vector3f(-(type.getSize() / 2), -(type.getSize() / 2), 0), new Vector3f(type.getSize(), type.getSize(), 0));
		setOffset(new Vector3f(type.getSize() / 2, type.getSize() / 2, 0));
		setSize(new Vector3f(type.getSize(), type.getSize(), 0));
		setRadius(type.getSize());
		try {
			destroySound = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/sounds/explode1.ogg"));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws SlickException {
		String fileName = "";
		switch(type) {
		case LARGE:
			fileName = "asteroid_lrg.png";
			break;
		case MEDIUM:
			fileName = "asteroid_med.png";
			break;
		case SMALL:
			fileName = "asteroid_sml.png";
			break;
		}
		setRadius(type.getSize());
		setImage(new Image("res/elements/" + fileName));
		trail.init(new Color(0.5f, 1.0f, 0.5f, 0.7f), TRAIL_LENGTH, TRAIL_SNAP);
	}
	
	public void render(Graphics g) {
		trail.render(g, getImage());
		super.render(g);
	}
	
	public void destroy() {
		state = EntityState.DEAD;
		destroySound.playAsSoundEffect(1.0f, 0.6f, false);
	}
	
	public AsteroidType getType() {
		return type;
	}
	
	@Override
	public void update(int delta) {

		super.move();
		trail.update(delta, getPosition(), getRotation());
	}
}
