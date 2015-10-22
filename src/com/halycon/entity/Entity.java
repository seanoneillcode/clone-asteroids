package com.halycon.entity;

import com.halycon.EntityState;

public abstract class Entity extends Sprite {
	
	protected EntityState state;
	
	public Entity() {
		state = EntityState.ALIVE;
	}
	
	public void update(int delta) {
		super.update(delta);
	}

	public EntityState getState() {
		return state;
	}
}
