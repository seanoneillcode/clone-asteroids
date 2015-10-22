package com.halycon.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Renderable {

	public void render(Graphics g);
	public void init() throws SlickException;
}
