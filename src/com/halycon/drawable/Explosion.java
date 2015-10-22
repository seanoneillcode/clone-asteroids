package com.halycon.drawable;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Graphics;

import com.halycon.EntityState;
import com.halycon.screen.GamePlayState;

public class Explosion {

	Vector3f position;
	
	ArrayList<Particle> particles;
	
	Random random;
	
	int numParticles;
	
	private static final int TIME_TO_LIVE = 500;
	private static final float BURST_SPEED = 2.0f;
	private static final int MIN_PARTICLES = 4;
	private static final int EXTRA_PARTICLES = 10;
	
	int time;	
	EntityState state;

	
	public Explosion(Vector3f newPosition) {
		GamePlayState.shakeScreen();
		time = TIME_TO_LIVE;
		state = EntityState.ALIVE;
		position = new Vector3f(newPosition);
		random = new Random();
 		numParticles = random.nextInt(MIN_PARTICLES) + EXTRA_PARTICLES;
 		Vector3f mov;
 		particles = new ArrayList<Particle>();
 		for (int index = 0; index < numParticles; ++index) {
 			mov = new Vector3f((0.5f - random.nextFloat()) * BURST_SPEED, (0.5f - random.nextFloat()) * BURST_SPEED, 0);
 			particles.add(new Particle(position, mov, random.nextInt(TIME_TO_LIVE)));
 		}
	}
	
	public void render(Graphics g) {
		for (Particle particle : particles) {
			particle.render(g);
		}
	}
	
	public EntityState getState() {
		return state;
	}
	
	public void update(int delta) {
		time -= delta;
		if (time <= 0) {
			state = EntityState.DEAD;
		}
		for (Particle particle : particles) {
			particle.update(delta);
		}
	}
}
