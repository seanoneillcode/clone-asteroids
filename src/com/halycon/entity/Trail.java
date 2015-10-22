package com.halycon.entity;

import java.util.LinkedList;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Trail {

	private Vector3f positionOffset;
	private Vector3f sizeOffset;
	
	private LinkedList<Vector3f> trail;
	private LinkedList<Float> trailRot;
	private int trailTimer = 0;
	
	private Color color;
	
	private int length;
	private int snap;
	
	public Trail() {
		trail = new LinkedList<Vector3f>();
		trailRot = new LinkedList<Float>();
	}
	
	public void init(Color color, int length, int snap) {
		this.color = color;
		this.length = length;
		this.snap = snap;
	}
	
	public void render(Graphics g, Image image) {
		for (int i = 0; i < trail.size(); ++i) {
			float alpha = (1.0f / length) * i;
			float x = trail.get(i).x;
			float y = trail.get(i).y;
			g.pushTransform();
			g.rotate(trail.get(i).x, trail.get(i).y, trailRot.get(i));
			image.draw(x + positionOffset.x, y + positionOffset.y, sizeOffset.x, sizeOffset.y, new Color(alpha * color.r, alpha * color.g, color.b, alpha * color.a));
			g.popTransform();
		}
	}

	public void setOffset(Vector3f position, Vector3f size) {
		this.positionOffset = position;
		this.sizeOffset = size;
	}
	
	public void update(int delta, Vector3f position, double rotation) {
		trailTimer += delta;
		if (trailTimer > snap) {
			trailTimer = 0;
			if (trail.size() >= length) {
				trail.removeFirst();				
			}
			trail.add(new Vector3f(position));
			if (trailRot.size() >= length) {
				trailRot.removeFirst();
			}
			trailRot.add((float) rotation);
		}
	}
}
