package com.halycon.entity;

import org.lwjgl.util.vector.Vector3f;

public interface Collidable {

	public boolean canCollide();
	public boolean isColliding(Collidable collidable);
	public float getRadius();
	public Vector3f getPosition();
}
