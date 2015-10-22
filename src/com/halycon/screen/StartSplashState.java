package com.halycon.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class StartSplashState extends BasicGameState{
	int stateID = -1;

	Image image;
	String splashImageLocation = "res/screens/splash.png";
	
	StartSplashState( int stateID ) {
		this.stateID = stateID;
	}
	
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		this.image = new Image(this.splashImageLocation);
		this.image.setFilter(Image.FILTER_NEAREST);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		image.draw(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_UP)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_ENTER)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}

	}
}
