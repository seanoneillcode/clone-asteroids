package com.halycon;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Score {

	Image ones, tens, hundreds, thousands;
	Image numbers;
	Image[] digits;
	int score;
	String numbersLocation = "res/elements/numbers.png";
	private static final int RADIX = 10;
	private static final int NUMBER_WIDTH = 7;
	private static final int NUMBER_HEIGHT = 10;
	private static final int NUMBER_OF_DIGITS = 4;
	Vector3f position;
	
	
	public Score() {
		position = new Vector3f(0,0,0);
		score = 0;
		digits = new Image[NUMBER_OF_DIGITS];
	}
	
	public void setPosition(Vector3f pos) {
		position = pos;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setNumber(int num) {
		score = num;
		updateCurrentNumberImage();
	}
	
	public int getNumber() {
		return score;
	}
	
	public void increaseNumberBy(int additional) {
		score += additional;
	}
	
	public void init() throws SlickException {
		numbers = new Image(numbersLocation);
		updateCurrentNumberImage();
	}
	
	private void updateCurrentNumberImage() {
		int xoffset = 0;
		int digit = score;
		for (int i = 0; i < NUMBER_OF_DIGITS; ++i) {
			xoffset = NUMBER_WIDTH * (digit % RADIX);
			digits[i] = numbers.getSubImage(xoffset, 0, NUMBER_WIDTH, NUMBER_HEIGHT);
			digits[i].setFilter(Image.FILTER_NEAREST);
			digit = digit / RADIX;
		}		
	}
	
	public void render() {
		for (int i = 0; i < NUMBER_OF_DIGITS; ++i) {
			digits[NUMBER_OF_DIGITS - i - 1].draw(position.getX() + (i * NUMBER_WIDTH), position.getY());
		}
	}
	
	public void update() {
		updateCurrentNumberImage();
	}
}
