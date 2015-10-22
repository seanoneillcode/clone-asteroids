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

public class Ship extends Entity {

	private Image boostImage, normalImage;
	
	private Trail trail;
	
	private static final int SHOOT_COOLDOWN = 250;
	private static final int STARTING_LIVES = 3;
	private static final int DAMAGE_COOLDOWN = 2000;
	private static final int TRAIL_LENGTH = 20;
	private static final int TRAIL_SNAP = 10;
	
	private int shootCooldown;
	private int lives;
	private int damageCooldown;
	
	public static Audio shoot, hurt;
	
	private static final int WIDTH = 8;
	private static final int HEIGHT = 10;
	
	public Ship() {
		super();
		shootCooldown = 0;
		damageCooldown = 0;
		lives = STARTING_LIVES;
		trail = new Trail();
		trail.setOffset(new Vector3f(-(WIDTH / 2), -(HEIGHT / 2), 0), new Vector3f(WIDTH, HEIGHT, 0));
		try {
			shoot = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/sounds/bullet4.ogg"));
			hurt = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/sounds/hurt03.ogg"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOffset(new Vector3f(WIDTH / 2, HEIGHT / 2, 0));
		setSize(new Vector3f(WIDTH, HEIGHT, 0));
		setRadius(4.5f);
	}
	
	@Override
	public void init() throws SlickException {
		normalImage = new Image("res/elements/ship5.png");
		boostImage = new Image("res/elements/shipboost.png");
		setImage(normalImage);
		trail.init(new Color(1.0f, 0.5f, 0.5f, 0.5f), TRAIL_LENGTH, TRAIL_SNAP);
	}
	
	public void render(Graphics g) {
		if (isGoingForward()) {
			setImage(boostImage);
		} else {
			setImage(normalImage);
		}
		
		if (damageCooldown <= 0 || isFlicker()) {
			trail.render(g, getImage());
			super.render(g);
		}
	}
	
	private boolean isFlicker() {
		return ((int)(damageCooldown / 200) % 2) == 0;
	}
	
	public boolean isCollidable() {
		return damageCooldown <= 0;
	}
	
	public void takeALife() {
		if (damageCooldown <= 0) {
			lives -= 1;
			damageCooldown = DAMAGE_COOLDOWN;
			hurt.playAsSoundEffect(1.0f, 2.0f, false);
		}
	}
	
	public void addALife() {
		lives += 1;
	}
	
	public int getLives() {
		return lives;
	}
	
	public boolean canShoot() {
		return shootCooldown <= 0;
	}
	
	public Bullet shootBullet() throws SlickException {
		Bullet bullet = new Bullet();
		bullet.init();
		bullet.shootBullet(this);
		shootCooldown = SHOOT_COOLDOWN;
		shoot.playAsSoundEffect(1.0f, 0.6f, false);
		return bullet;
	}
	
	@Override
	public void update(int delta) {
		trail.update(delta, getPosition(), getRotation());
		super.update(delta);
				
		if (damageCooldown > 0) {
			damageCooldown -= delta;
		}
		if (shootCooldown > 0) {
			shootCooldown -= delta;
		}
	}
 }
