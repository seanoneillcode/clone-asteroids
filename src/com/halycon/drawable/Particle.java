package com.halycon.drawable;

import java.util.LinkedList;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.halycon.Constants;

public class Particle {
	
	float friction = 0.01f;
	Vector3f position, movement;
	int ttl, time;
	float segment;
	float size = Constants.UNIT_SIZE;
	
	LinkedList<Vector3f> trail;
	private static int TRAIL_LENGTH = 15;
	float less = 0.0f;
	
	public Particle(Vector3f newPosition, Vector3f newMovement, int ttl) {
		position = new Vector3f(newPosition);
		movement = new Vector3f(newMovement);
		this.ttl = ttl;
		time = ttl;
		segment = (1.0f / ttl);
		trail = new LinkedList<Vector3f>();
		for (int i = 0; i < TRAIL_LENGTH; ++i) {
			trail.add(new Vector3f(0,0,0));
		}
		size = size + ((ttl / 200) * size);
	}
	
	public void render(Graphics g) {
		float alpha, red, green, blue;
		
		alpha = 0.7f * segment * time;
		red = 1.0f;
		blue = (segment * time) * 0.2f;
		green = (segment * time) * 0.8f;
		float rot = 60.0f * time * segment;
		int trailNum = 1;
		
		for (Vector3f vector : trail) {
			g.setColor(new Color(red, green, blue, (alpha / TRAIL_LENGTH) * trailNum));
			g.pushTransform();
			g.rotate(vector.x, vector.y, rot);
			
			g.fillRect(vector.x, vector.y, size , size);
			g.popTransform();
			trailNum++;
		}
		
		g.setColor(new Color(red, green, blue, alpha));
		g.fillRect(position.x, position.y, size, size);
	}
	
	public void update(int delta) {
		time -= delta;
		if (time < 0) {
			time = 0;
		}
		if (movement.x >= friction) {
			movement.x -= friction;			
		}
		if (movement.x <= -friction) {
			movement.x += friction;
		}
		if (movement.y >= friction) {
			movement.y -= friction;			
		}
		if (movement.y <= -friction) {
			movement.y += friction;
		}
		Vector3f.add(position, movement, position);
		
		trail.removeFirst();
		trail.addLast(new Vector3f(position));
		size = size - segment;
	}
}
